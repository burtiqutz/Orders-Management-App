package dataAccess;

import connection.ConnectionFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        return "SELECT * FROM " + type.getSimpleName() + " WHERE " + field + " =?";
    }

    public List<T> findAll() {
        String query = "SELECT * FROM " + type.getSimpleName();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public T findById(int id) {
        String query = createSelectQuery("id");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<T> results = createObjects(resultSet);
            return results.isEmpty() ? null : results.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        }
        return null;
    }

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




    public T insert(T t) {
        Field[] fields = Arrays.stream(type.getDeclaredFields())
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .toArray(Field[]::new);

        String columns = Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        String placeholders = IntStream.range(0, fields.length)
                .mapToObj(i -> "?")
                .collect(Collectors.joining(", "));

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", type.getSimpleName(), columns, placeholders);


        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            IntStream.range(0, fields.length).forEach(i -> {
                fields[i].setAccessible(true);
                try {
                    statement.setObject(i + 1, fields[i].get(t));
                } catch (IllegalAccessException | SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        }
        return t;
    }

    public void update(T t) {
        Field[] fields = type.getDeclaredFields();

        String setClause = Arrays.stream(fields)
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));

        String query = String.format("UPDATE %s SET %s WHERE id = ?", type.getSimpleName(), setClause);

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

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

    public void delete(int id) {
        String query = String.format("DELETE FROM %s WHERE id = ?", type.getSimpleName());

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        }
    }

    public void resetAutoIncrement() {
        String query = "ALTER TABLE " + type.getSimpleName() + " AUTO_INCREMENT = 1";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:resetAutoIncrement " + e.getMessage());
        }
    }

}
