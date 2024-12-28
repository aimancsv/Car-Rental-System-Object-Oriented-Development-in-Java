package main.database;

import main.model.account.Customer;
import main.model.account.Gender;
import main.utils.Split;

import java.util.Optional;

public class CustomerRepo extends BaseRepo<String, Customer> implements LoginRepo<Customer> {

    public CustomerRepo(String fileName, Database database) {
        super(fileName, database);
    }

    @Override
    public String recordToString(Customer record) {
        return Split.defaultDbJoin(record.getUsername(), record.getPassword(), record.getGender().name(),
                record.getAge().toString(), record.getPhone(), record.getEmail(), record.getAddress());
    }

    @Override
    public Customer stringToRecord(String str) {
        String[] strings = Split.defaultDbSplit(str);
        return new Customer(strings[0], strings[1], Gender.valueOf(strings[2]), Integer.parseInt(strings[3]),
                strings[4], strings[5], strings[6]);
    }

    /*filter customer by username*/
    @Override
    public Optional<Customer> findByUsername(String username) {
        return getLines().filter(customer -> customer.getUsername().equals(username)).findFirst();
    }
}
