package main.database;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class BaseRepo<K, T extends Model<K>> implements Repo<K, T> {
    private final String fileName;
    protected final Database database;
    private final LinkedHashMap<K, T> inMemoryMap = new LinkedHashMap<>();

    protected BaseRepo(String fileName, Database database) {
        this.fileName = fileName;
        this.database = database;
        database.register(this);
        loadToMemory();
    }

    /** This Function is to load text file
    * */
    private void loadToMemory() {
        database.getLines(this)
                .map(this::stringToRecord)
                .forEach(t -> inMemoryMap.put(t.getPrimaryKey(), t));
    }

    @Override
    public String getFileName() {
        return fileName;
    }


    /** This Function is to insert record data*/
    public void insert(T record) {
        inMemoryMap.put(record.getPrimaryKey(), record);
        database.appendAtEnd(this, recordToString(record));
    }

    /** This Function is to update the record data*/
    public void update(T record) {
        inMemoryMap.replace(record.getPrimaryKey(), record);
        database.rewriteFile(this);
    }

    /** This Function is to delete the record data*/
    public void remove(T record) {
        inMemoryMap.remove(record.getPrimaryKey());
        database.rewriteFile(this);
    }

    public Stream<T> getLines() {
        return inMemoryMap.values().stream();
    }

    public Optional<T> findByPrimaryKey(K key) {
        return Optional.ofNullable(getByPrimaryKey(key));
    }

    @Override
    public T getByPrimaryKey(K key) {
        return inMemoryMap.get(key);
    }
}
