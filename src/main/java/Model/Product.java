package Model;
/**
 * Represents a product with an ID, name, stock quantity, and price.
 */
public class Product {
    private int id;
    private String name;
    private int stock;
    private double price;
    /**
     * Default constructor.
     */
    public Product() {}
    /**
     * Constructs a product with a name, stock quantity, and price.
     *
     * @param name  the product name
     * @param stock the quantity in stock
     * @param price the price of the product
     */
    public Product( String name, int stock, double price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }
    /**
     * Constructs a product with an ID, name, stock quantity, and price.
     *
     * @param id    the product ID
     * @param name  the product name
     * @param stock the quantity in stock
     * @param price the price of the product
     */
    public Product(int id, String name, int stock, double price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", stock=" + stock + ", price=" + price + "]";
    }
}
