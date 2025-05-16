package DataAccess;

import Connection.ConnectionFactory;
import Model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
/**
 * Data Access Object (DAO) class for handling Bill records in the Log table.
 * Provides methods to insert and retrieve bills.
 */
public class BillDAO {

    /**
     * Inserts a new Bill record into the Log table.
     *
     * @param bill the Bill object to be inserted.
     */

    public void insert(Bill bill) {
        String query = "INSERT INTO Log (order_id, details) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bill.getOrder_id());
            statement.setString(2, bill.details());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all Bill records from the Log table.
     *
     * @return a list of Bill objects, or an empty list if none found or on error.
     */

    public List<Bill> findAll() {
        String query = "SELECT * FROM Log";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            return resultSetToList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * Converts a ResultSet into a list of Bill objects.
     *
     * @param resultSet the result set from a database query.
     * @return a list of Bill objects parsed from the result set.
     * @throws SQLException if a database access error occurs.
     */

    private List<Bill> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Bill> bills = new ArrayList<>();

        while (resultSet.next()) {

            Bill bill = Stream.of(resultSet)
                    .map(rs -> {
                        try {
                            int id = rs.getInt("id");
                            int order_id = rs.getInt("order_id");
                            String details = rs.getString("details");
                            return new Bill(id, order_id, details);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);

            if (bill != null) bills.add(bill);
        }

        return bills;
    }

}
