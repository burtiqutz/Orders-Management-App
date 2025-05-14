package model;

public record Bill(int id, int orderId, String details) {
    public int getId() {
        return id;
    }
    public int getOrderId() {
        return orderId;
    }
    public String getDetails() {
        return details;
    }
}
