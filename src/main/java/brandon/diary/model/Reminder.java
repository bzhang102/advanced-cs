package brandon.diary.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Reminder {
    private SimpleStringProperty event;
    private SimpleObjectProperty<LocalDate> date;
    private SimpleStringProperty start;
    private SimpleBooleanProperty completed;

    public Reminder() {
        event = new SimpleStringProperty();
        date = new SimpleObjectProperty<LocalDate>();
        start = new SimpleStringProperty();
        completed = new SimpleBooleanProperty();
    }

    public String getEvent() {
        return event.get();
    }
    public SimpleStringProperty eventProperty() {
        return event;
    }
    public void setEvent(String event) {
        this.event.set(event);
    }

    public LocalDate getDate() {
        return date.get();
    }
    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public String getStart() {
        return start.get();
    }
    public SimpleStringProperty startProperty() {
        return start;
    }
    public void setStart(String start) {
        this.start.set(start);
    }

    public boolean isCompleted() {
        return completed.get();
    }
    public SimpleBooleanProperty completedProperty() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "event=" + event +
                ", date=" + date +
                ", start=" + start +
                ", completed=" + completed + '}';
    }
}
