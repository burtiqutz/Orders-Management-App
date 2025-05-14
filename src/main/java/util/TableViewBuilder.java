package util;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

public class TableViewBuilder {

    public static <T> void setupTable(TableView<T> tableView, List<T> data, Class<T> type) {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        Stream.of(type.getDeclaredFields())
                .forEach(field -> {
                    TableColumn<T, Object> column = new TableColumn<>(field.getName());
                    column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                    column.setPrefWidth(120);
                    tableView.getColumns().add(column);
                });

        tableView.getItems().addAll(data);
    }
}
