package businessLogic;

import dataAccess.ProductDAO;
import model.Product;

import java.util.List;

public class ProductBLL {
    private final ProductDAO productDAO = new ProductDAO();

    public void insertProduct(Product product) {
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        productDAO.insert(product);
    }

    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

    public Product findProductById(int id) {
        return productDAO.findById(id);
    }

    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    public boolean isStockAvailable(int productId, int quantity) {
        Product product = productDAO.findById(productId);
        return product != null && product.getStock() >= quantity;
    }

    public void decrementStock(int productId, int quantity) {
        Product product = productDAO.findById(productId);
        if (product != null) {
            product.setStock(product.getStock() - quantity);
            productDAO.update(product);
        }
    }

    public void resetProductAutoIncrement() {
        productDAO.resetAutoIncrement();
    }
}
