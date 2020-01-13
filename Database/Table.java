package db;

import java.util.*;

/**
 * Created by Schuyler on 3/4/2017.
 */
public class Table {

    ArrayList<Column> columns;
    ArrayList<Row> rows;
    int numRows;
    int numCols;

    public Table(ArrayList<Column> c) {
        columns = new ArrayList<>();
        columns.addAll(c);
        rows = new ArrayList<>();
        numRows = 0;
        numCols = columns.size();
    }

    public Table(Column[] c) {
        columns = new ArrayList<>();
        columns.addAll(Arrays.asList(c));
        rows = new ArrayList<>();
        numRows = 0;
        numCols = columns.size();
    }

    public Table(int size) {
        columns = new ArrayList<>();
        rows = new ArrayList<>();
        for (int i = 0; i < size; i += 1) {
            rows.add(new Row<String>());
        }
        numRows = rows.size();
        numCols = 0;
    }

    public String addRow(String[] newRow) {
        if (numCols != newRow.length) {
            return "ERROR: Invalid row length";
        }
        for (int i = 0; i < numCols; i += 1) {
            if (!columns.get(i).type.equals(type(newRow[i])) && !newRow[i].equals("NOVALUE")) {
                return "ERROR: Attempted invalid type insertion";
            }
            if (type(newRow[i]).equals("float")) {
                newRow[i] = round(newRow[i]);
            }
        }
        rows.add(new Row<String>(newRow));
        numRows += 1;
        return "";
    }

    public String addRow(ArrayList<String> newRow) {
        if (numCols != newRow.size()) {
            return "ERROR: Invalid row length";
        }
        for (int i = 0; i < numCols; i += 1) {
            if (!columns.get(i).type.equals(type(newRow.get(i)))
                    && !newRow.get(i).equals("NOVALUE")) {
                return "ERROR: Attempted invalid type insertion";
            }
            if (type(newRow.get(i)).equals("float")) {
                newRow.add(i, round(newRow.remove(i)));
            }
        }
        rows.add(new Row<String>(newRow));
        numRows += 1;
        return "";
    }

    public static String type(String value) {
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

    /**private static String type(String value) {
        char[] chars = value.toCharArray();
        Character[] d = new Character[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        ArrayList<Character> digits = new ArrayList<>(Arrays.asList(d));
        if (chars[0] == '\'' && chars[chars.length - 1] == '\'') {
            return "string";
        }
        if (chars[0] == '-') {
            chars[0] = '0';
        }
        for (char i : chars) {
            if (! digits.contains(i)) {
                return "invalid";
            } else if (i == '.') {
                return "float";
            }
        }
        return "int";
    }*/

    public String filter1(String column, String comparator, String literal) {
        int index = indexify(column);
        String typeC = columns.get(index).type;
        String typeL = type(literal);
        if ((typeC.equals("string") && !typeL.equals("string"))
                || (typeL.equals("string") && !typeC.equals("string")))  {
            return "ERROR: Attempted invalid type comparison";
        }
        ArrayList<Row> removers = new ArrayList<>();
        for (Row r : rows) {
            String value = (String) r.get(index);
            boolean fulfilled = compare(comparator, value, literal, typeC);
            if (!fulfilled) {
                removers.add(r);
            }
        }
        for (Row r : removers) {
            rows.remove(r);
            numRows -= 1;
        }
        return "";
    }

    public String filter2(String column1, String comparator, String column2) {
        int index1 = indexify(column1);
        int index2 = indexify(column2);
        String type1 = columns.get(index1).type;
        String type2 = columns.get(index2).type;
        if ((type1.equals("string") && !type2.equals("string"))
                || (type2.equals("string") && !type1.equals("string")))  {
            return "ERROR: Attempted invalid type comparison";
        }
        ArrayList<Row> removers = new ArrayList<>();
        for (Row r : rows) {
            String value1 = (String) r.get(index1);
            String value2 = (String) r.get(index2);
            boolean fulfilled = compare(comparator, value1, value2, type1);
            if (!fulfilled) {
                removers.add(r);
            }
        }
        for (Row r : removers) {
            rows.remove(r);
            numRows -= 1;
        }
        return "";
    }

    private int indexify(String columnName) {
        boolean found = false;
        int i = 0;
        while (!found) {
            if (columns.get(i).name.equals(columnName)) {
                found = true;
            } else {
                i += 1;
            }
        }
        return i;
    }

    private static boolean compare(String comparator, String val1, String val2, String type) {
        int compareValue;
        if (val1.equals("NOVALUE") || val2.equals("NOVALUE")) {
            return false;
        } else if (val1.equals("NaN") && val2.equals("NaN")) {
            compareValue = 0;
        } else if (val1.equals("NaN")) {
            compareValue = 1;
        } else if (val2.equals("NaN")) {
            compareValue = -1;
        } else if (!type.equals("string")) {
            Double v1 = Double.parseDouble(val1);
            Double v2 = Double.parseDouble(val2);
            compareValue = v1.compareTo(v2);
        } else {
            String v1 = val1.substring(1, val1.length() - 1);
            String v2 = val2.substring(1, val2.length() - 1);
            compareValue = v1.compareTo(v2);
        }
        return fulfillsConditional(comparator, compareValue);
    }

    private static boolean fulfillsConditional(String comparator, int compareValue) {
        if (comparator.equals("==")) {
            return compareValue == 0;
        } else if (comparator.equals("!=")) {
            return compareValue != 0;
        } else if (comparator.equals("<")) {
            return compareValue < 0;
        } else if (comparator.equals(">")) {
            return compareValue > 0;
        } else if (comparator.equals("<=")) {
            return compareValue <= 0;
        } else {
            return compareValue >= 0;
        }
    }

    public void moveColumn(String columnName) {
        if (columns.get(0).name.equals(columnName)) {
            return;
        }
        int index = indexify(columnName);
        columns.add(0, columns.remove(index));
        for (Row r : rows) {
            r.add(0, r.remove(index));
        }
    }

    public String toString() {
        String result = "";
        for (Column col: columns) {
            result += col.name + " " + col.type + ",";
        }
        result = result.substring(0, result.length() - 1);
        for (Row row: rows) {
            result += row.toString();
        }
        return result;
    }

    public static void main(String[] args) {
        Column x = new Column("x", "string");
        Column y = new Column("y", "float");
        Column z = new Column("z", "int");
        Column[] columns = new Column[] {x, y, z};
        Table T1 = new Table(columns);
        /**String valid = T1.addRow(new String[] {"'0'", "-0.0", "-0"});
        String invalid = T1.addRow(new String[] {"0", "00.0", "00"});
        String revalid = T1.addRow(new String[] {"'yes'", "888.345", "NOVALUE"});
        String nope = T1.addRow(new String[] {"'yes", "888.345"});
        String nada = T1.addRow(new String[] {"'yes'", "888345", "88"});
        String nilch = T1.addRow(new String[] {"'yes'", "888345.", "88."});*/
        T1.addRow(new String[] {"'a'", "2.0", "20"});
        T1.addRow(new String[] {"'b'", "4.0", "4"});
        T1.addRow(new String[] {"'c'", "67.0", "6"});
        boolean a = Selector.columnInTable("x", T1);
    }
}
