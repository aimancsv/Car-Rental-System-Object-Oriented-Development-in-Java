package main.UI.tableUI;

public interface ObjectAdapter<T> {
    Object getColumnFromObject(T obj, int idx);
}
