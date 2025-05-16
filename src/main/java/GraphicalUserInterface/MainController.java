package GraphicalUserInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Controller for the main application window.
 * Loads tabs dynamically and provides utility methods like showing info popups.
 */
public class MainController {
    @FXML private TabPane mainTabPane;

    /**
     * Initializes the main controller by loading all application tabs.
     */
    @FXML
    public void initialize() {
        loadTab("Clients", "/clients-tab.fxml");
        loadTab("Products", "/products-tab.fxml");
        loadTab("Orders", "/orders-tab.fxml");
        loadTab("Log", "/log-tab.fxml");
    }

    /**
     * Loads an FXML layout into a new tab and adds it to the main tab pane.
     *
     * @param title the title of the tab.
     * @param resourcePath the path to the FXML resource.
     */
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

    /**
     * Displays an informational popup dialog with the given message.
     *
     * @param message the message to display in the popup.
     */
    public static void showInfoPopup(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
