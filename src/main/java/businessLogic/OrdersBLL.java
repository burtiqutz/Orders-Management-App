package businessLogic;

import dataAccess.OrdersDAO;
import model.Orders;
import model.Bill;

import java.util.List;

public class OrdersBLL {
    private final OrdersDAO ordersDAO = new OrdersDAO();
    private final ProductBLL productBLL = new ProductBLL();
    private final BillBLL billBLL = new BillBLL();

    public void insertOrder(Orders orders) {
        if (!productBLL.isStockAvailable(orders.getProduct_id(), orders.getQuantity())) {
            throw new IllegalArgumentException("Not enough stock for product ID: " + orders.getProduct_id());
        }

        ordersDAO.insert(orders);
        int orderId = ordersDAO.getLastInsertedOrderId();
        orders.setId(orderId);
        productBLL.decrementStock(orders.getProduct_id(), orders.getQuantity());

        Bill bill = new Bill(0, orders.getId(), "Details for Order ID: " + orders.getId());
        billBLL.insertBill(bill);
    }


    public void deleteOrder(int id) {
        ordersDAO.delete(id);
    }

    public Orders findOrderById(int id) {
        return ordersDAO.findById(id);
    }

    public List<Orders> findAllOrders() {
        return ordersDAO.findAll();
    }

    public void resetOrderAutoIncrement() {
        ordersDAO.resetAutoIncrement();
    }
}
