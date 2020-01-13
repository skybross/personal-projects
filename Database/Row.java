package db;

import java.util.*;

/**
 * Created by Schuyler on 3/4/2017.
 */
public class Row<E> extends ArrayList<E> {

    public Row() {

    }

    public Row(E[] values) {
        addAll(Arrays.asList(values));
    }

    public Row(ArrayList<E> values) {
        addAll(values);
    }

    public String toString() {
        String result = "\n";
        for (E item: this) {
            result += item.toString() + ",";
        }
        return result.substring(0, result.length() - 1);
    }
}
