package main.database;

public interface Model<T> {
    T getPrimaryKey();
    void setPrimaryKey(T pk);
}
