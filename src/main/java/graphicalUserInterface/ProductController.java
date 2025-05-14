package graphicalUserInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.Product;
import javafx.scene.control.TextField;
import util.TableViewBuilder;
import businessLogic.ProductBLL;

public class ProductController {
    @FXML private TextField productNameField;
    @FXML private TextField productStockField;
    @FXML private TextField productPriceField;

    @FXML private TableView<Product> productTable;

    private final ProductBLL productBLL = new ProductBLL();

    @FXML
    public void initialize() {
        refreshProductTable();
    }

    private void refreshProductTable() {
        TableViewBuilder.setupTable(productTable, productBLL.findAllProducts(), Product.class);
    }

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

    @FXML
    private void handleDeleteProduct() {
        Product product = productTable.getSelectionModel().getSelectedItem();
        if (product != null) {
            productBLL.deleteProduct(product.getId());
            refreshProductTable();
        }
    }
}
