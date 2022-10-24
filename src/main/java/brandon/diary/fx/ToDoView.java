package brandon.diary.fx;

import brandon.diary.model.ToDo;
import brandon.diary.service.ToDoCsvFileService;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

public class ToDoView extends VBox {
    private PriorityQueue<ToDo> completedPriorityQueue = new PriorityQueue<>();
    private PriorityQueue<ToDo> unCompletedPriorityQueue = new PriorityQueue<>();
    boolean showCompleted = false;

    public ToDoView() throws IOException, ParseException {
        ToDoCsvFileService service = new ToDoCsvFileService();

        // get data from csv
        List<ToDo> allToDos = service.readCsvFile();
        for (ToDo toDo : allToDos) {
            if (toDo.isCompleted()) {
                completedPriorityQueue.offer(toDo);
            } else {
                unCompletedPriorityQueue.offer(toDo);
            }
        }

        System.out.println("completedPriorityQueue size = " + completedPriorityQueue.size());
        System.out.println("unCompletedPriorityQueue size = " + unCompletedPriorityQueue.size());

        ObservableList<ToDo> data = FXCollections.observableArrayList(unCompletedPriorityQueue);

        // create all the columns
        TableColumn eventCol = new TableColumn("Event");
        eventCol.setPrefWidth(400);
        eventCol.setCellValueFactory(new PropertyValueFactory<ToDo, String>("event"));
        eventCol.setCellFactory(TextFieldTableCell.forTableColumn());
        eventCol.setOnEditCommit(
            new EventHandler<CellEditEvent<ToDo, String>>() {
                @Override
                public void handle(CellEditEvent<ToDo, String> t) {
                    ((ToDo) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                    ).setEvent(t.getNewValue());
                }
            }
        );

        TableColumn priorityCol = new TableColumn("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<ToDo, Integer>("priority"));
        priorityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        priorityCol.setOnEditCommit(
            new EventHandler<CellEditEvent<ToDo, Integer>>() {
                @Override
                public void handle(CellEditEvent<ToDo, Integer> t) {
                    ((ToDo) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                    ).setPriority(t.getNewValue());

                    t.getTableView().refresh();
                }
            }
        );

        TableColumn completedCol = new TableColumn("Completed");
        completedCol.setCellValueFactory(new PropertyValueFactory<ToDo, Boolean>("completed"));
        completedCol.setCellFactory(CheckBoxTableCell.forTableColumn(completedCol));

        // create tableview and add data
        TableView<ToDo> table = new TableView<>();
        table.setEditable(true);
        table.setItems(data);
        ToDoHelper.prepare(table.getItems());

        // add columns to table
        table.getColumns().addAll(eventCol, priorityCol, completedCol);

        table.setRowFactory(tv -> new TableRow<ToDo>() {
            @Override
            public void updateItem(ToDo item, boolean empty) {
                super.updateItem(item, empty) ;
            }
        });

        // create filter
        final Label filterLabel = new Label("Search");
        //filterLabel.setFont(new Font("Arial", 12));
        final TextField filterField = new TextField();
        ObservableList items = table.getItems();
        filterField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table.setItems(items);
            }
            String value = newValue.toLowerCase();
            ObservableList<ToDo> subentries = FXCollections.observableArrayList();

            long count = table.getColumns().stream().count();
            for (int i = 0; i < table.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table.getItems().get(i));
                        break;
                    }
                }
            }
            table.setItems(subentries);
        });

        // Clear search
        final Button allButton = new Button("Clear Search");
        allButton.setOnAction(e -> {
            filterField.clear();
            table.setItems(data);
        });

        // add to-do
        final TextField addEvent = new TextField();
        addEvent.setPromptText("Event / Task");
        addEvent.setPrefWidth(eventCol.getPrefWidth());
        addEvent.setMaxWidth(eventCol.getPrefWidth());
        final TextField addPriority = new TextField();
        addPriority.setPromptText("Priority");
        addEvent.setPrefWidth(eventCol.getPrefWidth());
        addEvent.setMaxWidth(eventCol.getPrefWidth());

        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            int priority = 0;
            try {
                priority = Integer.parseInt(addPriority.getText());
            } catch (Exception ee) {
                Alert alert = new Alert(AlertType.ERROR, "Priority has to be a number");
                alert.showAndWait();
                return;
            }

            ToDo toDo = new ToDo();
            toDo.setEvent(addEvent.getText());
            toDo.setPriority(priority);
            toDo.setCompleted(false);

            unCompletedPriorityQueue.offer(toDo);

            data.clear();
            data.addAll(FXCollections.observableArrayList(unCompletedPriorityQueue));
            table.setItems(data);
            ToDoHelper.prepare(table.getItems());

            addEvent.clear();
            addPriority.clear();
        });

        // delete to-do
        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            ToDo selectedItem = table.getSelectionModel().getSelectedItem();
            table.getItems().remove(selectedItem);
            if (selectedItem.isCompleted()) {
                completedPriorityQueue.remove(selectedItem);
            } else {
                unCompletedPriorityQueue.remove(selectedItem);
            }
            data.remove(selectedItem);
        });

        // save to CSV
        final Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                service.writeCsvFile(unCompletedPriorityQueue, completedPriorityQueue);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        });

        // show completed
        final Button showCompletedButton = new Button("Show Completed");
        showCompletedButton.setOnAction(e -> {
            showCompleted = true;
            data.clear();
            data.addAll(FXCollections.observableArrayList(unCompletedPriorityQueue));
            data.addAll(FXCollections.observableArrayList(completedPriorityQueue));

            table.setItems(data);
            ToDoHelper.prepare(table.getItems());
        });

        // hide completed
        final Button hideCompletedButton = new Button("Hide Completed");
        hideCompletedButton.setOnAction(e -> {
            System.out.println("hide completed clicked");
            showCompleted = false;
            data.clear();
            data.addAll(FXCollections.observableArrayList(unCompletedPriorityQueue));

            table.setItems(data);
            ToDoHelper.prepare(table.getItems());
        });

        // refresh
        final Button updateCompleted = new Button("Update Completed");
        updateCompleted.setOnAction(e -> {
            if (showCompleted) {
                unCompletedPriorityQueue.clear();
                completedPriorityQueue.clear();

                for (ToDo toDo : data) {
                    if (toDo.isCompleted()) {
                        completedPriorityQueue.offer(toDo);
                    } else {
                        unCompletedPriorityQueue.offer(toDo);
                    }
                }

                data.clear();

                data.addAll(FXCollections.observableArrayList(unCompletedPriorityQueue));
                data.addAll(FXCollections.observableArrayList(completedPriorityQueue));

            } else {
                // because we are hiding completed, so it is only possible to change some uncompleted to completed, not vise versa
                Iterator<ToDo> dataIt = data.iterator();
                while (dataIt.hasNext()) {
                    ToDo toDo = dataIt.next();
                    if (toDo.isCompleted()) {
                        completedPriorityQueue.offer(toDo);
                        dataIt.remove();
                        unCompletedPriorityQueue.remove(toDo);
                    }
                }
            }

            table.setItems(data);
            ToDoHelper.prepare(table.getItems());
        });

        final HBox hb1 = new HBox();
        hb1.getChildren().addAll(filterLabel, filterField, allButton, showCompletedButton, hideCompletedButton, updateCompleted);
        hb1.setSpacing(20);

        // put everything on screen
        final HBox hb2 = new HBox();
        hb2.getChildren().addAll(addEvent, addPriority,
            addButton, deleteButton, saveButton);
        hb2.setSpacing(3);

        this.setPrefWidth(800);
        this.setSpacing(5);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.getChildren().addAll(hb1, table, hb2);
    }

}
