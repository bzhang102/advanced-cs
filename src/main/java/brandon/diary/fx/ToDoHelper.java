package brandon.diary.fx;

import com.sun.javafx.collections.SortableList;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.ObservableList;

public class ToDoHelper {
    public static <T extends Comparable<? super T>> void prepare(ObservableList<T> var0) {
        if (var0 instanceof SortableList) {
            ((SortableList)var0).sort();
        } else {
            ArrayList var1 = new ArrayList(var0);
            Collections.sort(var1);
            var0.setAll(var1);
        }
    }
}
