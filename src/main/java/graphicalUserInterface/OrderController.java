package graphicalUserInterface;

import businessLogic.OrdersBLL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Orders;
import util.TableViewBuilder;


public class OrderController {

    @FXML private TextField orderClientIdField;
    @FXML private TextField orderProductIdField;
    @FXML private TextField orderQuantityField;
    @FXML private TableView<Orders> orderTable;

    private final OrdersBLL ordersBLL = new OrdersBLL();

    @FXML
    public void initialize() {
        refreshOrderTable();
    }

    public void handleAddOrder(ActionEvent actionEvent) {
        try {
            int clientId = Integer.parseInt(orderClientIdField.getText());
            int productId = Integer.parseInt(orderProductIdField.getText());
            int quantity = Integer.parseInt(orderQuantityField.getText());

            Orders orders = new Orders(clientId, productId, quantity);

            ordersBLL.insertOrder(orders);
            refreshOrderTable();
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in input fields.");
        } catch (IllegalArgumentException e) {
            System.err.println("Order error: " + e.getMessage());
            MainController.showInfoPopup("Currently under-stocked.");
        }
    }

    private void refreshOrderTable() {
        TableViewBuilder.setupTable(orderTable, ordersBLL.findAllOrders(), Orders.class);
    }
}
