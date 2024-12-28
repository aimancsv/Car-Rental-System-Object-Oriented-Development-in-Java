package main.database;

import main.model.account.Admin;
import main.utils.Split;

import java.util.Optional;

/** Admin database
* */
public class AdminRepo extends BaseRepo<String, Admin> implements LoginRepo<Admin> {
    public AdminRepo(String fileName, Database database) {
        super(fileName, database);
    }

    /* This Function is to map from object to text*/
    @Override
    public String recordToString(Admin record) {
        return Split.defaultDbJoin(record.getUsername(), record.getPassword());
    }

    /** This Function is to convert from text to object*/
    @Override
    public Admin stringToRecord(String str) {
        String[] split = Split.defaultDbSplit(str);
        return new Admin(split[0], split[1]);
    }

    public long getTotalRecord() {
        return database.getTotalCount(this);
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        return getLines()
                .filter(admin -> admin.getUsername().equals(username))
                .findFirst();
    }
}
