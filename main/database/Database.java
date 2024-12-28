package main.database;

import main.utils.FileCreater;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Database {
    private final String baseDirectory;

    public Database(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        init();
    }

    private void init() {
        FileCreater.createDirectoryIfNotExists(baseDirectory);
    }

    /**
    * This Function is to register folder path
    */
    public void register(Repo<?, ?> repo) {
        try {
            FileCreater.createFileIfNotExists(baseDirectory, repo.getFileName());
        } catch (Exception e) {
            System.out.printf("Create file %s in database is unsuccessful%n", repo.getFileName());
            throw new IllegalStateException();
        }
    }

    public long getTotalCount(Repo<?, ?> repo) {
        Path path = getPath(repo);
        long lines = 0;
        try {
            lines = Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
    /** This Function is to get file path
    */
    private Path getPath(Repo<?, ?> repo) {
        return FileCreater.joinPath(baseDirectory, repo.getFileName());
    }

    private String getPathString(Repo<?, ?> repo) {
        return FileCreater.joinPathString(baseDirectory, repo.getFileName());
    }

    public void appendAtEnd(Repo<?, ?> repo, String string) {
        try {
            Files.write(Paths.get(getPathString(repo)), string.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <K, T extends Model<K>> void rewriteFile(Repo<K, T> repo) {
        try {
            byte[] data = repo.getLines().map(repo::recordToString).collect(Collectors.joining()).getBytes();
            Files.write(Paths.get(getPathString(repo)), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stream<String> getLines(Repo<?, ?> repo) {
        try {
            return Files.lines(getPath(repo));
        } catch (IOException e) {
            return Stream.empty();
        }
    }
}
