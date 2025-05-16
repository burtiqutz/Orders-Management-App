package GraphicalUserInterface;

import BusinessLogic.OrdersBLL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Model.Orders;
import Util.TableViewBuilder;

/**
 * Controller for managing Orders UI.
 * Handles adding orders and refreshing the orders table.
 */
public class OrderController {

    @FXML private TextField orderClientIdField;
    @FXML private TextField orderProductIdField;
    @FXML private TextField orderQuantityField;
    @FXML private TableView<Orders> orderTable;

    private final OrdersBLL ordersBLL = new OrdersBLL();

    /**
     * Initializes the controller by loading the orders data into the table.
     */
    @FXML
    public void initialize() {
        refreshOrderTable();
    }

    /**
     * Handles adding a new order from user input.
     * Validates inputs and inserts the order using the business logic layer.
     * Shows an info popup if stock is insufficient.
     *
     * @param actionEvent the event triggered by user action.
     */
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

    /**
     * Refreshes the orders table with the latest order data.
     */
    private void refreshOrderTable() {
        TableViewBuilder.setupTable(orderTable, ordersBLL.findAllOrders(), Orders.class);
    }
}
