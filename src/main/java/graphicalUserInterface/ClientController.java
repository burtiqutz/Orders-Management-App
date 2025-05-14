package graphicalUserInterface;

import businessLogic.ClientBLL;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Client;
import util.TableViewBuilder;

public class ClientController {

    @FXML private TextField clientNameField;
    @FXML private TextField clientAddressField;
    @FXML private TextField clientEmailField;
    @FXML private TableView<Client> clientTable;

    private final ClientBLL clientBLL = new ClientBLL();

    @FXML
    public void initialize() {
        refreshClientTable();
    }

    @FXML
    private void handleAddClient() {
        String name = clientNameField.getText();
        String address = clientAddressField.getText();
        String email = clientEmailField.getText();

        Client client = new Client(name, address, email);
        clientBLL.insertClient(client);
        refreshClientTable();
    }

    @FXML
    private void handleDeleteClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            clientBLL.deleteClient(selectedClient.getId());
            refreshClientTable();
        }
    }

    @FXML
    private void handleUpdateClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            selectedClient.setName(clientNameField.getText());
            selectedClient.setAddress(clientAddressField.getText());
            selectedClient.setEmail(clientEmailField.getText());

            clientBLL.updateClient(selectedClient);
            refreshClientTable();
        }
    }


    private void refreshClientTable() {
        TableViewBuilder.setupTable(clientTable, clientBLL.findAllClients(), Client.class);
    }
}
