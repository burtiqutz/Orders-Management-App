package DataAccess;

import Connection.ConnectionFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 * Abstract generic DAO class that provides basic CRUD operations
 * (Create, Read, Update, Delete) for any type of entity.
 *
 * @param <T> the type of the model class
 */
public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;
    protected static final Connection connection = ConnectionFactory.getConnection();

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        return "SELECT * FROM " + type.getSimpleName() + " WHERE " + field + " =?";
    }

    /**
     * Retrieves all records from the database table corresponding to the generic type T.
     *
     * @return a List of all T objects from the database, or an empty list if none found.
     */

    public List<T> findAll() {
        String query = "SELECT * FROM " + type.getSimpleName();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves a record from the database where the ID matches the given value.
     *
     * @param id the ID of the entity to retrieve.
     * @return the object of type T if found, otherwise null.
     */
    public T findById(int id) {
        String query = createSelectQuery("id");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<T> results = createObjects(resultSet);
            return results.isEmpty() ? null : results.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        }
        return null;
    }

    /**
     * Maps a ResultSet to a list of objects of type T using reflection.
     *
     * @param resultSet the ResultSet from a SQL query.
     * @return a List of objects of type T.
     */
    private List<T> createObjects(ResultSet resultSet) {
        Constructor<?> ctor = Arrays.stream(type.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No no-arg constructor found"));

        List<T> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();

                Arrays.stream(type.getDeclaredFields()).forEach(field -> {
                    try {
                        String fieldName = field.getName();
                        PropertyDescriptor propDesc = new PropertyDescriptor(fieldName, type);
                        Method setter = propDesc.getWriteMethod();
                        Class<?> paramType = setter.getParameterTypes()[0];

                        Object value = switch (paramType.getSimpleName()) {
                            case "int" -> resultSet.getInt(fieldName);
                            case "double" -> resultSet.getDouble(fieldName);
                            case "float" -> resultSet.getFloat(fieldName);
                            case "long" -> resultSet.getLong(fieldName);
                            case "boolean" -> resultSet.getBoolean(fieldName);
                            case "String" -> resultSet.getString(fieldName);
                            default -> resultSet.getObject(fieldName);
                        };

                        setter.invoke(instance, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Inserts a new record into the database for the given object.
     * Automatically sets the generated ID if available.
     *
     * @param t the object to insert into the database.
     * @return the inserted object with updated ID field.
     */
    public T insert(T t) {
        Field[] fields = Arrays.stream(type.getDeclaredFields())
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .toArray(Field[]::new);

        String columns = Arrays.stream(fields).map(Field::getName).collect(Collectors.joining(", "));
        String placeholders = IntStream.range(0, fields.length).mapToObj(i -> "?").collect(Collectors.joining(", "));
        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", type.getSimpleName(), columns, placeholders);

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            IntStream.range(0, fields.length).forEach(i -> {
                fields[i].setAccessible(true);
                try {
                    statement.setObject(i + 1, fields[i].get(t));
                } catch (IllegalAccessException | SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long id = generatedKeys.getLong(1);
                        try {
                            Field idField = type.getDeclaredField("id");
                            idField.setAccessible(true);
                            if (idField.getType() == int.class || idField.getType() == Integer.class) {
                                idField.set(t, (int) id);
                            } else if (idField.getType() == long.class || idField.getType() == Long.class) {
                                idField.set(t, id);
                            }
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            LOGGER.log(Level.SEVERE, "Unable to set generated ID.", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        }

        return t;
    }

    /**
     * Updates the database record matching the ID of the given object with the new values.
     *
     * @param t the object with updated values and existing ID.
     */
    public void update(T t) {
        Field[] fields = type.getDeclaredFields();
        String setClause = Arrays.stream(fields)
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));

        String query = String.format("UPDATE %s SET %s WHERE id = ?", type.getSimpleName(), setClause);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            AtomicInteger paramIndex = new AtomicInteger(1);
            Object idValue = null;

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(t);
                if (!field.getName().equalsIgnoreCase("id")) {
                    statement.setObject(paramIndex.getAndIncrement(), value);
                } else {
                    idValue = value;
                }
            }

            statement.setObject(paramIndex.get(), idValue);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        }
    }

    /**
     * Deletes the record from the database with the given ID.
     *
     * @param id the ID of the record to delete.
     */
    public void delete(int id) {
        String query = String.format("DELETE FROM %s WHERE id = ?", type.getSimpleName());
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        }
    }

    public void resetAutoIncrement() {
        String query = "ALTER TABLE " + type.getSimpleName() + " AUTO_INCREMENT = 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:resetAutoIncrement " + e.getMessage());
        }
    }
}
