package dataAccess;

import connection.ConnectionFactory;
import model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class BillDAO {

    public void insert(Bill bill) {
        String query = "INSERT INTO Log (order_id, details) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bill.getOrderId());
            statement.setString(2, bill.details());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bill> findAll() {
        String query = "SELECT * FROM Log";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Convert ResultSet to a list of Bill objects using Streams
            return resultSetToList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<Bill> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Bill> bills = new ArrayList<>();

        while (resultSet.next()) {

            Bill bill = Stream.of(resultSet)
                    .map(rs -> {
                        try {
                            int id = rs.getInt("id");
                            int orderID = rs.getInt("orderID");
                            String details = rs.getString("details");
                            return new Bill(id, orderID, details);
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
