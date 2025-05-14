package graphicalUserInterface;

import dataAccess.ClientDAO;
import dataAccess.ProductDAO;
import model.Client;
import model.Product;

public class Test {
    public static void main(String[] args) {
        ClientDAO dao = new ClientDAO();
        ProductDAO productDAO = new ProductDAO();
//        Client existing = dao.findById(6);
//        existing.setAddress("cluj-napoca observatorului");
//        dao.update(existing);

        for (Client c : dao.findAll()) {
            System.out.println(c);
        }
        for (Product p : productDAO.findAll()) {
            System.out.println(p);

        }
    }
}
