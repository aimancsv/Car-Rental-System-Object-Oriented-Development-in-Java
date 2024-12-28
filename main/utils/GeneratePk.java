package main.utils;

import main.database.Model;
import main.database.Repo;

public class GeneratePk {
    /** This Function is to generate primary key
     */
    public static <primaryKey extends Model<Integer>>void generateSerialIfNew(primaryKey model, Repo<Integer, primaryKey> repo) {
        if (model.getPrimaryKey() == null)
            model.setPrimaryKey((int) repo.getLines().count() + 1);
    }
}
