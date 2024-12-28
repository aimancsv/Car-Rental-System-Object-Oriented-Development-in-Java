package main.utils;

public class Split {
    /**
     * This Function is to join & split text file into class */

    public static String defaultDbJoin(String... strings) {
        return String.join("|| ", strings) + "\n";
    }

    public static String[] defaultDbSplit(String strings) {
        return strings.split("\\|\\| ");
    }
}
