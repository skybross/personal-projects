package db;

import java.util.TreeMap;

public class Database {

    static TreeMap<String, Table> tables;
    private Parser parser;

    public Database() {
        tables = new TreeMap<>();
        parser = new Parser(tables);
    }

    public String transact(String query) {
        return parser.parse(query);
    }
}
