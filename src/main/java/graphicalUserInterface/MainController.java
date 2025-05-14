package graphicalUserInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainController {
    @FXML private TabPane mainTabPane;

    @FXML
    public void initialize() {
        loadTab("Clients", "/clients-tab.fxml");
        loadTab("Products", "/products-tab.fxml");
        loadTab("Orders", "/orders-tab.fxml");
        loadTab("Log", "/log-tab.fxml");
    }

    private void loadTab(String title, String resourcePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            Pane content = loader.load();
            Tab tab = new Tab(title, content);
            mainTabPane.getTabs().add(tab);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showInfoPopup(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
