package BusinessLogic;

import DataAccess.OrdersDAO;
import Model.Orders;
import Model.Bill;

import java.util.List;
/**
 * Business Logic Layer for Orders management.
 * Handles order validation, stock update, and bill creation.
 */

public class OrdersBLL {
    private final OrdersDAO ordersDAO = new OrdersDAO();
    private final ProductBLL productBLL = new ProductBLL();
    private final BillBLL billBLL = new BillBLL();

    /**
     * Inserts a new order after checking product stock availability.
     * Decrements product stock and creates a corresponding bill.
     *
     * @param orders the Orders object to insert.
     * @throws IllegalArgumentException if there is insufficient stock.
     */

    public void insertOrder(Orders orders) {
        if (!productBLL.isStockAvailable(orders.getProduct_id(), orders.getQuantity())) {
            throw new IllegalArgumentException("Not enough stock for product ID: " + orders.getProduct_id());
        }

        Orders insertedOrder = ordersDAO.insert(orders);
        orders.setId(insertedOrder.getId());

        productBLL.decrementStock(orders.getProduct_id(), orders.getQuantity());

        Bill bill = new Bill(0, orders.getId(), "Details for Order ID: " + orders.getId());
        billBLL.insertBill(bill);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete.
     */

    public void deleteOrder(int id) {
        ordersDAO.delete(id);
    }

    /**
     * Finds an order by its ID.
     *
     * @param id the order ID.
     * @return the Orders object if found, otherwise null.
     */

    public Orders findOrderById(int id) {
        return ordersDAO.findById(id);
    }

    /**
     * Retrieves all orders.
     *
     * @return a List of all Orders.
     */

    public List<Orders> findAllOrders() {
        return ordersDAO.findAll();
    }

    /**
     * Resets the auto-increment counter for the orders table.
     */

    public void resetOrderAutoIncrement() {
        ordersDAO.resetAutoIncrement();
    }
}
