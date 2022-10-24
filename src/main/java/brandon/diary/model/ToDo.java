package brandon.diary.model;

import java.util.Objects;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ToDo implements Comparable<ToDo> {
    private SimpleStringProperty event;
    private SimpleIntegerProperty priority;
    private SimpleBooleanProperty completed;

    public ToDo() {
        event = new SimpleStringProperty();
        priority = new SimpleIntegerProperty();
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

    public int getPriority() {
        return priority.get();
    }

    public SimpleIntegerProperty priorityProperty() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority.set(priority);
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
            ", priority=" + priority +
            ", completed=" + completed +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ToDo toDo = (ToDo) o;
        return priority.equals(toDo.priority) && event.equals(toDo.event) && completed == toDo.completed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(completed, priority, event);
    }

    @Override
    public int compareTo(ToDo that) {
        if (this.completed.get() == (that.completed.get())) {
            if (this.priority.get() < that.priority.get()) {
                return -1;
            } else if (this.priority.get() == that.priority.get()) {
                return this.event.get().compareTo(that.event.get());
            } else {
                return 1;
            }
        } else {
            if (!this.completed.get()) {
                return -1;
            } else {
                return 1;
            }
        }
    }


}
