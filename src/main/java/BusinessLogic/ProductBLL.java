package BusinessLogic;

import DataAccess.ProductDAO;
import Model.Product;

import java.util.List;
/**
 * Business Logic Layer for Product operations.
 * Validates data and delegates database actions to ProductDAO.
 */

public class ProductBLL {
    private final ProductDAO productDAO = new ProductDAO();
    /**
     * Inserts a new product after validating stock and price are non-negative.
     *
     * @param product the Product to insert.
     * @throws IllegalArgumentException if stock or price is negative.
     */

    public void insertProduct(Product product) {
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        productDAO.insert(product);
    }

    /**
     * Updates an existing product in the database.
     *
     * @param product the Product with updated values.
     */

    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete.
     */
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

    /**
     * Finds a product by its ID.
     *
     * @param id the product ID.
     * @return the Product if found, otherwise null.
     */

    public Product findProductById(int id) {
        return productDAO.findById(id);
    }

    /**
     * Retrieves all products.
     *
     * @return a List of all Products.
     */

    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Checks if the requested quantity of a product is available in stock.
     *
     * @param productId the product ID.
     * @param quantity the requested quantity.
     * @return true if stock is sufficient, false otherwise.
     */

    public boolean isStockAvailable(int productId, int quantity) {
        Product product = productDAO.findById(productId);
        return product != null && product.getStock() >= quantity;
    }

    /**
     * Decreases the stock of a product by a given quantity.
     *
     * @param productId the product ID.
     * @param quantity the amount to decrement.
     */
    public void decrementStock(int productId, int quantity) {
        Product product = productDAO.findById(productId);
        if (product != null) {
            product.setStock(product.getStock() - quantity);
            productDAO.update(product);
        }
    }

    /**
     * Resets the auto-increment counter for the products table.
     */
    public void resetProductAutoIncrement() {
        productDAO.resetAutoIncrement();
    }
}
