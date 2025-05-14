package businessLogic;

import dataAccess.BillDAO;
import model.Bill;

import java.util.List;

public class BillBLL {

    private final BillDAO billDAO = new BillDAO();  // DAO to interact with the database for Bill operations

    public void insertBill(Bill bill) {
        billDAO.insert(bill);
    }

    public List<Bill> findAllBills() {
        return billDAO.findAll();
    }
}
