package brandon.dl.fx;
import java.time.LocalDate;

import brandon.dl.model.DriverLicense;
import brandon.dl.service.CsvFileService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

public class DatePickerCell<S, T> extends TableCell<DriverLicense, LocalDate> {
    private DatePicker datePicker;
    private ObservableList<DriverLicense> data;
    private String column;

    public DatePickerCell(ObservableList<DriverLicense> data, String column) {

        super();

        this.data = data;
        this.column = column;

        if (datePicker == null) {
            createDatePicker();
        }
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                datePicker.requestFocus();
            }
        });
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            if (isEditing()) {
                setContentDisplay(ContentDisplay.TEXT_ONLY);

            } else {
                setDatepikerDate(CsvFileService.dateTimeFormatter.format(item));
                setText(CsvFileService.dateTimeFormatter.format(item));
                setGraphic(this.datePicker);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }

    private void setDatepikerDate(String dateAsStr) {

        LocalDate ld = null;
        int jour, mois, annee;

        jour = mois = annee = 0;
        try {
            mois = Integer.parseInt(dateAsStr.substring(0, 2));
            jour = Integer.parseInt(dateAsStr.substring(3, 5));
            annee = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
        } catch (NumberFormatException e) {
            System.out.println("setDatepikerDate / unexpected error " + e);
        }

        ld = LocalDate.of(annee, mois, jour);
        datePicker.setValue(ld);
    }

    private void createDatePicker() {
        this.datePicker = new DatePicker();
        datePicker.setPromptText("jj/mm/aaaa");
        datePicker.setEditable(true);

        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = datePicker.getValue();
                int index = getIndex();

                setText(CsvFileService.dateTimeFormatter.format(date));
                commitEdit(date);


                if (null != getData()) {
                    if ("dob".equalsIgnoreCase(column)) {
                        getData().get(index).setDob(date);
                    } else if ("expiration".equalsIgnoreCase(column)) {
                        getData().get(index).setExpiration(date);
                    }
                }
            }
        });

        setAlignment(Pos.CENTER);
    }

    @Override
    public void commitEdit(LocalDate localDate) {
        super.commitEdit(localDate);

        this.getTableView().refresh();
    }

    @Override
    public void startEdit() {
        super.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    public ObservableList<DriverLicense> getData() {
        return data;
    }

    public void setBirthdayData(ObservableList<DriverLicense> data) {
        this.data = data;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

}