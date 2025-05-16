package Model;
/**
 * Represents an order made by a client for a specific product with a quantity.
 */

public class Orders {
    private int id;
    private int client_id;
    private int product_id;
    private int quantity;
    /**
     * Default constructor.
     */
    public Orders() {}
    /**
     * Constructs an order with client ID, product ID, and quantity.
     *
     * @param client_id  the ID of the client making the order
     * @param product_id the ID of the product being ordered
     * @param quantity   the quantity of the product ordered
     */
    public Orders(int client_id, int product_id, int quantity) {
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }
    /**
     * Constructs an order with an order ID, client ID, product ID, and quantity.
     *
     * @param id the order ID
     * @param client_id  the ID of the client making the order
     * @param product_id the ID of the product being ordered
     * @param quantity   the quantity of the product ordered
     */
    public Orders(int id, int client_id, int product_id, int quantity) {
        this.id = id;
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClient_id() { return client_id; }
    public void setClient_id(int client_id) { this.client_id = client_id; }

    public int getProduct_id() { return product_id; }
    public void setProduct_id(int product_id) { this.product_id = product_id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
