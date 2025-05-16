package BusinessLogic;

import DataAccess.BillDAO;
import Model.Bill;

import java.util.List;

/**
 * Business Logic Layer for Bill operations.
 * Delegates data access to BillDAO.
 */

public class BillBLL {

    private final BillDAO billDAO = new BillDAO();

    /**
     * Business Logic Layer for Bill operations.
     * Delegates data access to BillDAO.
     */

    public void insertBill(Bill bill) {
        billDAO.insert(bill);
    }

    /**
     * Retrieves all Bill records.
     *
     * @return a list of all Bills.
     */

    public List<Bill> findAllBills() {
        return billDAO.findAll();
    }
}
