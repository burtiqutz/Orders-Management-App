package businessLogic;

import dataAccess.ClientDAO;
import model.Client;

import java.util.List;

public class ClientBLL {
    private final ClientDAO clientDAO = new ClientDAO();

    public void insertClient(Client client) {
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be empty");
        }
        clientDAO.insert(client);
    }

    public void updateClient(Client client) {
        clientDAO.update(client);
    }

    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    public Client findClientById(int id) {
        return clientDAO.findById(id);
    }

    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    public void resetClientAutoIncrement() {
        clientDAO.resetAutoIncrement();
    }
}
