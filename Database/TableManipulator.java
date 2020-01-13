package db;

import java.util.*;

/**
 * Created by Schuyler on 3/9/2017.
 */
public class TableManipulator {

    public static String addColumn1(Table t1, Table t2, String columnName) {
        int index = indexify(columnName, t1);
        String type = t1.columns.get(index).type;
        t2.columns.add(new Column(columnName, type));
        t2.numCols += 1;
        for (int i = 0; i < t1.numRows; i += 1) {
            t2.rows.get(i).add(t1.rows.get(i).get(index));
        }
        return "";
    }

    public static String addColumn2(Table t1, Table t2, String c1,
                                    String literal, String operator, String c2) {
        int index = indexify(c1, t1);
        String typeC = t1.columns.get(index).type;
        String typeL = type(literal);
        if ((typeC.equals("string") || typeL.equals("string")) && !operator.equals("+")) {
            return "ERROR: Attempted invalid operation on string type";
        }
        if ((typeC.equals("string") && !typeL.equals("string"))
                || (typeL.equals("string") && !typeC.equals("string"))) {
            return "ERROR: Attempted invalid type arithmetic";
        }
        if (typeC.equals("float") || typeL.equals("float")) {
            typeC = "float";
        }
        t2.columns.add(new Column(c2, typeC));
        t2.numCols += 1;
        for (int i = 0; i < t1.numRows; i += 1) {
            String val = (String) t1.rows.get(i).get(index);
            t2.rows.get(i).add(evaluate(operator, val, literal, typeC));
        }
        return "";
    }

    public static String addColumn3(Table t1, Table t2, String c1,
                                    String c2, String operator, String c3) {
        int index1 = indexify(c1, t1);
        int index2 = indexify(c2, t1);
        String type1 = t1.columns.get(index1).type;
        String type2 = t1.columns.get(index2).type;
        if ((type1.equals("string") || type2.equals("string")) && !operator.equals("+")) {
            return "ERROR: Attempted invalid operation on string type";
        }
        if ((type1.equals("string") && !type2.equals("string"))
                || (type2.equals("string") && !type1.equals("string"))) {
            return "ERROR: Attempted invalid type arithmetic";
        }
        if (type1.equals("float") || type2.equals("float")) {
            type1 = "float";
        }
        t2.columns.add(new Column(c3, type1));
        t2.numCols += 1;
        for (int i = 0; i < t1.numRows; i += 1) {
            String val1 = (String) t1.rows.get(i).get(index1);
            String val2 = (String) t1.rows.get(i).get(index2);
            t2.rows.get(i).add(evaluate(operator, val1, val2, type1));
        }
        return "";
    }

    /**private static String typify(String columnName, Table t) {
     for (Column c : t.columns) {
     if (c.name.equals(columnName)) {
     return c.type;
     }
     }
     return "";
     }*/

    private static int indexify(String columnName, Table t) {
        boolean found = false;
        int i = 0;
        while (!found) {
            if (t.columns.get(i).name.equals(columnName)) {
                found = true;
            } else {
                i += 1;
            }
        }
        return i;
    }

    private static String type(String value) {
        char[] chars = value.toCharArray();
        if (chars[0] == '\'') {
            return "string";
        }
        for (char i : chars) {
            if (i == '.') {
                return "float";
            }
        }
        return "int";
    }

    private static String evaluate(String operator, String val1, String val2, String type) {
        if (val1.equals("NaN") || val2.equals("NaN")) {
            return "NaN";
        } else if (val1.equals("NOVALUE") && val2.equals("NOVALUE")) {
            return "NOVALUE";
        } else if (val1.equals("NOVALUE")) {
            val1 = zeroForType(type);
        } else if (val2.equals("NOVALUE")) {
            val2 = zeroForType(type);
        }
        if (type.equals("string")) {
            String v1 = val1.substring(1, val1.length() - 1);
            String v2 = val2.substring(1, val2.length() - 1);
            return "'" + v1 + v2 + "'";
        }
        Double v1 = Double.parseDouble(val1);
        Double v2 = Double.parseDouble(val2);
        Double result;
        if (operator.equals("+")) {
            result = v1 + v2;
        } else if (operator.equals("-")) {
            result = v1 - v2;
        } else if (operator.equals("*")) {
            result = v1 * v2;
        } else {
            String divided = String.valueOf(v1 / v2);
            if (divided.equals("Infinity")) {
                return "NaN";
            }
            result = v1 / v2;
        }
        if (type.equals("int")) {
            return String.valueOf(result.intValue());
        }
        return round(String.valueOf(result));
    }

    private static String zeroForType(String type) {
        if (type.equals("string")) {
            return "''";
        } else if (type.equals("int")) {
            return "0";
        } else {
            return "0.0";
        }
    }

    private static String round(String doubleValue) {
        Boolean pastZero = false;
        int newStart = 0;
        String finalValue = null;
        for (int i = 0; i < doubleValue.length(); i += 1) {
            if (doubleValue.charAt(i) != '0' && doubleValue.charAt(i) != '-') {
                pastZero = true;
            }
            if (!pastZero && doubleValue.charAt(i) == '0') {
                newStart = i + 1;
            }
            if (doubleValue.charAt(i) == '.') {
                try {
                    finalValue = doubleValue.substring(0, i + 4);
                } catch (IndexOutOfBoundsException e) {
                    finalValue = doubleValue;
                }
                if (doubleValue.charAt(0) == '-' && newStart != 0) {
                    finalValue = "-" + finalValue.substring(newStart);
                } else {
                    finalValue = finalValue.substring(newStart);
                }
                if (finalValue.charAt(finalValue.length() - 1) == '.') {
                    finalValue = finalValue + "000";
                } else if (finalValue.charAt(finalValue.length() - 2) == '.') {
                    finalValue = finalValue + "00";
                } else if (finalValue.charAt(finalValue.length() - 3) == '.') {
                    finalValue = finalValue + "0";
                }
                if (finalValue.charAt(0) == '.') {
                    finalValue = "0" + finalValue;
                }
                return finalValue;
            }
        }
        return "";
    }

    public static Table join(Table t1, Table t2, ArrayList<String> columnNames) {
        ArrayList<Integer> i1 = new ArrayList<>();
        ArrayList<Integer> i2 = new ArrayList<>();
        for (String n : columnNames) {
            i1.add(indexify(n, t1));
            i2.add(indexify(n, t2));
        }
        ArrayList<Column> copy1 = copyColumns(t1.columns);
        ArrayList<Column> copy2 = copyColumns(t2.columns);
        for (int index2 : i2) {
            copy2.remove(index2);
        }
        copy1.addAll(copyColumns(copy2));
        Table t3 = new Table(copy1);
        for (Row r1 : t1.rows) {
            ArrayList<String> v1 = new ArrayList<>();
            for (int index1 : i1) {
                v1.add((String) r1.get(index1));
            }
            for (Row r2 : t2.rows) {
                ArrayList<String> v2 = new ArrayList<>();
                for (int index2 : i2) {
                    v2.add((String) r2.get(index2));
                }
                if (v1.equals(v2)) {
                    Row filteredRow = new Row(r2);
                    for (int index2 : i2) {
                        filteredRow.remove(index2);
                    }
                    ArrayList<String> merge = new ArrayList<>();
                    merge.addAll(r1);
                    merge.addAll(filteredRow);
                    t3.addRow(merge);
                }
            }
        }
        for (int i = columnNames.size() - 1; i >= 0; i -= 1) {
            t3.moveColumn(columnNames.get(i));
        }
        return t3;
    }

    public static Table join(Table t1, Table t2) {
        ArrayList<Column> copy1 = copyColumns(t1.columns);
        ArrayList<Column> copy2 = copyColumns(t2.columns);
        copy1.addAll(copyColumns(copy2));
        Table t3 = new Table(copy1);
        for (Row r1 : t1.rows) {
            for (Row r2 : t2.rows) {
                ArrayList<String> merge = new ArrayList<>();
                merge.addAll(r1);
                merge.addAll(r2);
                t3.addRow(merge);
            }
        }
        return t3;
    }

    private static ArrayList<Column> copyColumns(ArrayList<Column> columns) {
        ArrayList<Column> result = new ArrayList<>();
        for (Column c : columns) {
            result.add(new Column(c.name, c.type));
        }
        return result;
    }

    /**public static void main(String[] args) {
        Column x = new Column("x", "string");
        Column y = new Column("y", "float");
        Column z = new Column("z", "int");
        Column[] columns1 = new Column[] {x, y, z};
        Table T1 = new Table(columns1);
        T1.addRow(new String[] {"'a'", "2.0", "20"});
        T1.addRow(new String[] {"'b'", "4.0", "40"});
        T1.addRow(new String[] {"'c'", "6.0", "60"});
        T1.addRow(new String[] {"'d'", "8.0", "80"});

        Column a = new Column("a", "string");
        Column[] columns2 = new Column[] {a, z, y};
        Table T2 = new Table(columns2);
        T2.addRow(new String[] {"'e'", "30", "2.0"});
        T2.addRow(new String[] {"'f'", "60", "6.0"});
        T2.addRow(new String[] {"'g'", "60", "6.0"});
        T2.addRow(new String[] {"'h'", "70", "7.0"});
        ArrayList<String> cols = new ArrayList<>();
        cols.add("y");
        cols.add("z");
        Table T3 = TableManipulator.join(T1, T2, cols);

        Database db = new Database();
        Database.tables.put("T1", T1);
        Database.tables.put("T2", T2);
        Table T4 = Selector.joiner(new String[] {"T1", "T2"});
    }*/
}
