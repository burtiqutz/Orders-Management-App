package GraphicalUserInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import Model.Product;
import javafx.scene.control.TextField;
import Util.TableViewBuilder;
import BusinessLogic.ProductBLL;

/**
 * Controller for managing products in the UI.
 * Handles adding, updating, deleting, and displaying products.
 */
public class ProductController {
    @FXML private TextField productNameField;
    @FXML private TextField productStockField;
    @FXML private TextField productPriceField;

    @FXML private TableView<Product> productTable;

    private final ProductBLL productBLL = new ProductBLL();

    /**
     * Initializes the controller by loading product data into the table.
     */
    @FXML
    public void initialize() {
        refreshProductTable();
    }

    /**
     * Refreshes the product table with the latest product data.
     */
    private void refreshProductTable() {
        TableViewBuilder.setupTable(productTable, productBLL.findAllProducts(), Product.class);
    }

    /**
     * Handles adding a new product from user input.
     * Validates input and inserts the product using the business logic layer.
     */
    @FXML
    private void handleAddProduct() {
        try {
            String name = productNameField.getText();
            int stock = Integer.parseInt(productStockField.getText());
            double price = Double.parseDouble(productPriceField.getText());

            Product product = new Product(name, stock, price);
            productBLL.insertProduct(product);
            refreshProductTable();
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format. Stock and price must be numeric.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Handles updating the selected product with new user input.
     * Validates input and updates the product using the business logic layer.
     */
    @FXML
    private void handleUpdateProduct() {
        Product product = productTable.getSelectionModel().getSelectedItem();
        if (product != null) {
            try {
                product.setName(productNameField.getText());
                product.setStock(Integer.parseInt(productStockField.getText()));
                product.setPrice(Double.parseDouble(productPriceField.getText()));

                productBLL.updateProduct(product);
                refreshProductTable();
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Stock and price must be numeric.");
            }
        }
    }

    /**
     * Handles deleting the selected product.
     */
    @FXML
    private void handleDeleteProduct() {
        Product product = productTable.getSelectionModel().getSelectedItem();
        if (product != null) {
            productBLL.deleteProduct(product.getId());
            refreshProductTable();
        }
    }

    /**
     * Handles the refresh button action to reload the product table.
     *
     * @param actionEvent the event triggered by user action.
     */
    public void handleRefreshProduct(ActionEvent actionEvent) {
        refreshProductTable();
    }
}
