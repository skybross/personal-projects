package db;

import java.util.*;

/**
 * Created by Schuyler on 3/10/2017.
 */
public class Selector {

    public static Table joiner(String[] tables) {
        Table t1 = Database.tables.get(tables[0]);
        if (tables.length == 1) {
            return t1;
        }
        for (int i = 1; i < tables.length; i += 1) {
            Table t2 = Database.tables.get(tables[i]);
            ArrayList<String> columns = new ArrayList<>();
            for (Column c1 : t1.columns) {
                if (columnInTable(c1.name, t2)) {
                    columns.add(c1.name);
                }
            }
            if (columns.size() == 0) {
                t1 = TableManipulator.join(t1, t2);
            } else {
                t1 = TableManipulator.join(t1, t2, columns);
            }
        }
        return t1;
    }

    public static String expressionExecutor(String[][] expressions, Table t1, Table t2) {
        if (t1 == null) {
            return "ERROR: No table was given in the expression executor";
        }
        for (String[] expression : expressions) {
            expression = spaceRemover(expression);
            if (expression.length == 1) {
                if (expression[0].equals("*")) {
                    return "";
                } else {
                    if (!columnInTable(expression[0], t1)) {
                        return "ERROR: Requested column1 not in table";
                    }
                    String trying = TableManipulator.addColumn1(t1, t2, expression[0]);
                    if (!trying.equals("")) {
                        return trying;
                    }
                }
            }
            else {
                if (expression.length == 3) {
                    String compoundExp = expression[0];
                    String splitpoint;
                    if (compoundExp.contains("+")) {
                        splitpoint = "+";
                    } else if (compoundExp.contains("-")) {
                        splitpoint = "-";
                    } else if (compoundExp.contains("*")) {
                        splitpoint = "*";
                    } else {
                        splitpoint = "/";
                    }
                    int index = compoundExp.indexOf(splitpoint);
                    String val0 = compoundExp.substring(0, index);
                    String val1 = compoundExp.substring(index, index + 1);
                    String val2 = compoundExp.substring(index + 1);
                    expression = new String[] {val0, val1, val2, expression[1], expression[2]};
                }
                String operand1 = expression[0];
                if (!columnInTable(operand1, t1)) {
                    return "ERROR: Requested column2 not in table";
                }
                String operand2 = expression[2];
                boolean executed = false;
                for (Column c1 : t1.columns) {
                    if (c1.name.equals(operand2)) {
                        String trying = TableManipulator.addColumn3(t1, t2, operand1,
                                operand2, expression[1], expression[4]);
                        if (!trying.equals("")) {
                            return trying;
                        }
                        executed = true;
                    }
                }
                if (!executed) {
                    String trying = TableManipulator.addColumn2(t1, t2, operand1,
                            operand2, expression[1], expression[4]);
                    if (!trying.equals("")) {
                        return trying;
                    }
                }
            }
        }
        return "";
    }

    private static String[] spaceRemover(String[] expression) {
        if (expression.length == 2) {
            return new String[] {expression[1]};
        }
        if (expression.length == 4) {
            return new String[] {expression[1], expression[2], expression[3]};
        }
        if (expression.length == 6) {
            return new String[] {expression[1], expression[2], expression[3], expression[4], expression[5]};
        }
        return expression;
    }

    public static String conditionalExecutor(String[][] statements, Table t) {
        for (String[] statement : statements) {
            String operand1 = statement[0];
            if (!columnInTable(operand1, t)) {
                return "ERROR: Requested column3 not in table";
            }
            String operand2 = statement[2];
            boolean executed = false;
            for (Column c : t.columns) {
                if (c.name.equals(operand2)) {
                    String trying = t.filter2(operand1, statement[1], operand2);
                    if (!trying.equals("")) {
                        return trying;
                    }
                    executed = true;
                }
            }
            if (!executed) {
                String trying = t.filter1(operand1, statement[1], operand2);
                if (!trying.equals("")) {
                    return trying;
                }
            }
        }
        return "";
    }

    public static boolean columnInTable(String column, Table t) {
        if (t.numCols == 0) {
            return false;
        }
        for (Column c : t.columns) {
            if (c.name.equals(column)) {
                return true;
            }
        }
        return false;
    }
}
