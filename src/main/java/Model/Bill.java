package Model;
/**
 * Represents a Bill with an id, associated order id, and details.
 *
 * @param id the bill ID
 * @param order_id the related order ID
 * @param details details of the bill
 */
public record Bill(int id, int order_id, String details) {
    public int getId() {
        return id;
    }
    public int getOrder_id() {
        return order_id;
    }
    public String getDetails() {
        return details;
    }
}
