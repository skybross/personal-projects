/**
 * Created by Schuyler on 4/29/2017.
 */

import java.util.ArrayList;
import java.io.PrintWriter;

public class SATGrader {

    // Add instance variables as needed.

    // Below includes answers to questions #1-10, 32-52
    private static final char[] DIAG4READING_PARTIAL = new char[] {'c', 'd', 'd', 'c', 'a', 'a', 'b', 'd',
            'd', 'a', 'd', 'd', 'a', 'd', 'b', 'd', 'd', 'd', 'a', 'b', 'c', 'b', 'a', 'd', 'a', 'd', 'c',
            'd', 'c', 'b', 'a'};
    // Below includes answers to questions #1-33.
    private static final char[] DIAG4WRITING_PARTIAL = new char[] {'b', 'b', 'b', 'a', 'd', 'b', 'd', 'b',
            'c', 'a', 'c', 'd', 'b', 'd', 'c', 'c', 'a', 'c', 'a', 'c', 'b', 'd', 'c', 'c', 'b', 'd',
            'c', 'a', 'd', 'b', 'c', 'b', 'a'};

    private static final char[] DIAG4MATH = new char[] {'a', 'a', 'a', 'b', 'c', 'b', 'd', 'a', 'd',
            'd', 'c', 'c', 'b', 'a', 'b', 'b', 'c', 'c', 'b', 'b', 'a', 'a', 'd', 'b', 'a', 'a', 'c',
            'c', 'd', 'b', 'a', 'd', 'c', 'a', 'c', 'c', 'b', 'b', 'c', 'b', 'c', 'd', 'd', 'b', 'd'};
    private static final int[] DIAG4MCTOPICS = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0,
            0, 0, 0, 3, 0, 3, 3, 3, 1, 1, 0, 1, 0, 0, 2, 0, 1, 0, 0, 3, 2, 1, 0, 3, 1, 3, 1};
    private static final String[] DIAG4MATHFREE = new String[] {"9", "3/5", "0.6", "5", "0", "25", "1160",
            "1/2", "0.5", "4.55", "150", "9/4", "2.25", "29", "30", "31", "32", "33", "34", "0.72", "134"};
    private static final int[] DIAG4FREETOPICS = new int[] {2, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 1, 1};

    // Below includes answers to questions #1-31.
    private static final char[] DIAG5READING_PARTIAL = new char[] {'d', 'c', 'c', 'a', 'c', 'a', 'd',
            'b', 'b', 'b', 'b', 'a', 'b', 'd', 'c', 'a', 'b', 'b', 'b', 'a', 'd', 'a', 'b', 'a', 'b',
            'c', 'd', 'b', 'd', 'b', 'd'};
    // Below includes answers to questions #1-33.
    private static final char[] DIAG5WRITING_PARTIAL = new char[] {'c', 'd', 'b', 'c', 'a', 'c', 'd',
            'd', 'b', 'c', 'c', 'd', 'd', 'a', 'b', 'c', 'c', 'a', 'd', 'd', 'a', 'b', 'b', 'b', 'a',
            'b', 'd', 'a', 'c', 'b', 'c', 'd', 'a'};

    private static final char[] DIAG5MATH = new char[] {'d', 'a', 'b', 'c', 'd', 'a', 'c', 'a', 'a', 'b', 'c',
            'd', 'b', 'c', 'd', 'd', 'c', 'a', 'b', 'c', 'b', 'a', 'c', 'b', 'a', 'a', 'd', 'd', 'a', 'a', 'd', 'd',
            'c', 'b', 'd', 'a', 'c', 'd', 'b', 'd', 'b', 'c', 'c', 'b', 'b'};
    private static final int[] DIAG5MCTOPICS = new int[] {0, 2, 1, 1, 1, 1, 0, 0, 1, 1, 2, 1, 0, 1, 0, 3, 0, 0,
            0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 2, 0, 0, 0, 0, 0, 0, 2, 3, 0, 2, 1};
    private static final String[] DIAG5MATHFREE = new String[] {"4", "6/5", "1.2", "21/4", "5.25", "2", "97",
            "1492", "29/3", "9.66", "9.67", "7", "9", "13", "80", "43", "43.5", "44", "87/2", "6"};
    private static final int[] DIAG5FREETOPICS = new int[] {0, 0, 0, 1, 2, 0, 0, 0, 1, 2, 2, 0, 0};

    // Below includes answers to questions #1-21, 32-41.
    private static final char[] DIAG8READING_PARTIAL = new char[] {'a', 'c', 'c', 'd', 'a', 'd', 'd', 'b', 'c',
            'b', 'b', 'd', 'd', 'a', 'd', 'b', 'c', 'b', 'c', 'a', 'c', 'b', 'c', 'b', 'c', 'b', 'c', 'd', 'c',
            'a', 'd'};
    // Below includes answers to questions #1-33.
    private static final char[] DIAG8WRITING_PARTIAL = new char[] {'d', 'b', 'c', 'b', 'd', 'c', 'b', 'c', 'a',
            'c', 'a', 'a', 'd', 'c', 'c', 'a', 'd', 'b', 'd', 'b', 'b', 'd', 'a', 'c', 'c', 'a', 'c', 'c', 'b',
            'b', 'b', 'd', 'd'};

    private static final int[] READINGCONVERTER = new int[] {10, 10, 10, 11, 12, 13, 14, 15, 16, 16, 17, 18,
            18, 19, 20, 20, 21, 21, 22, 22, 23, 23, 23, 24, 24, 25, 25, 26, 26, 27, 27, 28, 28, 28, 29, 29,
            30, 30, 31, 31, 32, 32, 33, 33, 34, 35, 35, 36, 37, 38, 39, 39, 40};
    private static final int[] WRITINGCONVERTER = new int[] {10, 10, 10, 10, 11, 12, 13, 14, 15, 16, 16, 17,
            18, 19, 19, 20, 21, 22, 23, 23, 24, 24, 25, 26, 26, 27, 27, 28, 29, 29, 30, 31, 31, 32, 32, 33,
            33, 34, 35, 36, 37, 37, 38, 39, 40};
    private static final int[] MATHCONVERTER = new int[] {200, 200, 210, 230, 250, 270, 280, 300, 320, 340,
            350, 360, 370, 390, 410, 420, 430, 450, 460, 470, 480, 490, 500, 510, 520, 530, 540, 550, 560, 570,
            580, 590, 600, 600, 610, 620, 630, 640, 650, 660, 670, 680, 690, 700, 710, 710, 720, 730, 730, 740,
            750, 750, 760, 770, 780, 790, 790, 800, 800};


    public static void main(String[] args) {
        if (args[0].equals("verbal")) {
            // Update depending on test version.
            if (args.length != 8 || args[1].length() != 31 || args[2].length() != 33) {
                System.out.println("Invalid arguments");
                System.out.println("Reading input length: " + Integer.toString(args[1].length()));
                System.out.println("Writing input length: " + Integer.toString(args[2].length()));
            } else {
                Integer[][] verbalResults = verbalCalculator(args);
                // Update or comment the following four lines depending on whether an email is being sent.
                System.out.println();
                System.out.println("Hi " + args[7] + ",");
                System.out.println();
                System.out.println("Below are your results from the verbal portion of Diagnostic Exam #2.");
                System.out.println();
                System.out.println("---------------------------------------------------------------------------" +
                        "----------------------------------");
                System.out.println();
                System.out.println("Verbal Scaled Score: " + Integer.toString(verbalResults[0][0])
                        + " (800 possible)");
                System.out.println();
                // Update depending on test version.
                System.out.println("Reading Section Raw Score: " + Integer.toString(verbalResults[0][1])
                        + " (" + Integer.toString(DIAG5READING_PARTIAL.length) + " possible)");
                System.out.print("You missed the following Reading questions: ");
                if (verbalResults[1].length == 0) {
                    System.out.println();
                }
                for (int i = 0; i < verbalResults[1].length; i += 1) {
                    if (i + 1 == verbalResults[1].length) {
                        System.out.println(Integer.toString(verbalResults[1][i]));
                    } else {
                        System.out.print(Integer.toString(verbalResults[1][i]) + ", ");
                    }
                }
                System.out.println();
                // Update depending on test version.
                System.out.println("Writing & Language (Grammar) Section Raw Score: " +
                        Integer.toString(verbalResults[0][2]) +
                        " (" + Integer.toString(DIAG5WRITING_PARTIAL.length) + " possible)");
                System.out.print("You missed the following Writing & Language questions: ");
                if (verbalResults[2].length == 0) {
                    System.out.println();
                }
                for (int i = 0; i < verbalResults[2].length; i += 1) {
                    if (i + 1 == verbalResults[2].length) {
                        System.out.println(Integer.toString(verbalResults[2][i]));
                    } else {
                        System.out.print(Integer.toString(verbalResults[2][i]) + ", ");
                    }
                }
                // Update or comment the following eighteen lines depending on whether an email is being sent.
                System.out.println();
                String nl = System.lineSeparator();
                System.out.println("Below are your results from the Essay Section " +
                        "(including a breakdown by scoring category)." +
                        nl + "Unfortunately, because class has ended, I can only give you numbers;" +
                        " you will not see any comments explaining your scores." +
                        nl + "If you would like more detailed feedback, please reply to this email asking for it." +
                        nl + "I will send you both a scanned copy of your original essay" +
                        " and my comments/suggestions.");
                System.out.println();
                System.out.println("Essay Overall Score: " + args[3] + " (24 possible)");
                System.out.println("Reading Score: " + args[4] + " (8 possible)");
                System.out.println("Analysis Score: " + args[5] + " (8 possible)");
                System.out.println("Writing Score: " + args[6] + " (8 possible)");
                System.out.println();
                // Update depending on test version.
                System.out.println("Remember, these results are from Practice Test #5 in your textbook.");
                System.out.println();
                System.out.println("---------------------------------------------------------------------" +
                        "----------------------------------------");
                System.out.println();
                System.out.println("Please let me know if you have any questions.");
                System.out.println();
                System.out.println("Best,");
                System.out.println();
                System.out.println("Schuyler");
            }
        } else if (args[0].equals("math")) {
            // Update depending on test version.
            if (args.length != 17 || args[1].length() != 45) {
                System.out.println("Invalid arguments");
                System.out.println("Math MC input length: " + Integer.toString(args[1].length()));
                System.out.println("Math FR input length: " + Integer.toString(args.length - 2));
            } else {
                Integer[][] mathResults = mathCalculator(args);
                String nl = System.lineSeparator();
                System.out.println();
                System.out.println(args[15] + " " + args[16]);
                System.out.println();
                // Special message for Su18 group. Delete for all future usage.
                System.out.println("This is Schuyler Ross, your instructor from the SAT Math class you took in ATDP " +
                        "in the summer of 2018.");
                System.out.println();
                System.out.println("It has taken me quite a while, but I have finally been able to grade your final " +
                        "diagnostic exam." +
                        nl + "My apologies for the extreme delay in getting you these results." +
                        nl + "I was unable to finish grading the exams by mid-August, and once the fall semester " +
                        "started up I became incredibly strapped for time." +
                        nl + "But, since many of you are Sophomores and Juniors who can still benefit from seeing " +
                        "your results, I decided it would be best to send out scores now." +
                        nl + "So, without further ado...");
                System.out.println();
                // End special message.
                System.out.println("Below are your results from the mathematics portion of Diagnostic Exam #2.");
                System.out.println();
                System.out.println("---------------------------------------------------------------------------" +
                        "----------------------------------");
                System.out.println();
                System.out.println("Math Scaled Score: " + Integer.toString(mathResults[0][0])
                        + " (800 possible)");
                System.out.println();
                System.out.print("You missed the following Non-Calculator Section questions: ");
                if (mathResults[1].length == 0) {
                    System.out.println();
                }
                for (int i = 0; i < mathResults[1].length; i += 1) {
                    if (i + 1 == mathResults[1].length) {
                        System.out.println(Integer.toString(mathResults[1][i]));
                    } else {
                        System.out.print(Integer.toString(mathResults[1][i]) + ", ");
                    }
                }
                System.out.println();
                System.out.print("You missed the following Calculator Section questions: ");
                if (mathResults[2].length == 0) {
                    System.out.println();
                }
                for (int i = 0; i < mathResults[2].length; i += 1) {
                    if (i + 1 == mathResults[2].length) {
                        System.out.println(Integer.toString(mathResults[2][i]));
                    } else {
                        System.out.print(Integer.toString(mathResults[2][i]) + ", ");
                    }
                }
                System.out.println();
                System.out.println("Below is your performance in each of the four major topic categories, " +
                        "across both portions of the test (calculator and non-calculator)." +
                        nl + "Keep in mind that these categories do NOT all contain the same number of questions." +
                        nl + "For example, a score of 80% in Geometry may mean you missed only one question, " +
                        "whereas a score of 80% in Basic Algebra likely " +
                        "means you missed at least five questions." +
                        nl + "Please see the document distributed during class (and posted in the Drive) for a " +
                        "list of all subtopics included in each category.");
                System.out.println();
                System.out.println("Basic Algebra Score: " + Integer.toString(mathResults[0][1]) + "%");
                System.out.println("Advanced Algebra Score: " + Integer.toString(mathResults[0][2]) + "%");
                System.out.println("Geometry Score: " + Integer.toString(mathResults[0][3]) + "%");
                System.out.println("Data Analysis Score: " + Integer.toString(mathResults[0][4]) + "%");
                System.out.println();
                System.out.println("Below is a list of all non-calculator questions, divided into the four major " +
                        "topic categories.");
                System.out.println();
                // Update following four lines depending on test version.
                // Should be made more generalizable; hardcoded now for sake of time.
                System.out.println("Basic Algebra: 1, 7, 8, 13, 15, 16, 17, 18");
                System.out.println("Advanced Algebra: 3, 4, 5, 6, 9, 10, 12, 14, 19");
                System.out.println("Geometry: 2, 11, 20");
                System.out.println("Data Analysis: N/A");
                System.out.println();
                System.out.println("Below is a list of all calculator questions, divided into the four major " +
                        "topic categories." +
                        nl + "You may find it useful to reference this list when interpreting your above scores.");
                System.out.println();
                // Update following four lines depending on test version.
                // Should be made more generalizable; hardcoded now for sake of time.
                System.out.println("Basic Algebra: 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 18, 20, 21, 22, 23, 24, 25, " +
                        "28, 31, 32, 33, 37, 38");
                System.out.println("Advanced Algebra: 7, 8, 30, 34");
                System.out.println("Geometry: 19, 26, 29, 35, 36");
                System.out.println("Data Analysis: 1, 14, 15, 16, 17, 27");
                System.out.println();
                // Update depending on test version.
                System.out.println("Remember, these results are from Practice Test #5 in your textbook.");
                // Update or comment the following eight lines depending on whether an email is being sent.
                System.out.println();
                System.out.println("---------------------------------------------------------------------" +
                        "----------------------------------------");
                System.out.println();
                System.out.println("Please let me know if you have any questions.");
                System.out.println();
                System.out.println("Best,");
                System.out.println();
                System.out.println("Schuyler");
            }
        } else {
            System.out.println("Arguments must begin with test category: math or verbal");
        }
    }

    private static Integer[][] verbalCalculator(String[] args) {
        int correct = 0;
        String reading = args[1];
        String writing = args[2];
        ArrayList<Integer> xReading = new ArrayList<>();
        ArrayList<Integer> xWriting = new ArrayList<>();
        // Update following two lines depending on test version.
        for (int i = 0; i < 31; i += 1) {
            if (reading.charAt(i) == DIAG5READING_PARTIAL[i]) {
                correct += 1;
            // Update following four lines depending on test version.
            } else {
                xReading.add(i + 1);
            }
        }
        int rRaw = correct;
        // May need to be revised depending on completeness of test version.
        int rScaled = READINGCONVERTER[Math.round((52 * correct) / (float) 31)];
        correct = 0;
        // Update following two lines depending on test version.
        for (int i = 0; i < 33; i += 1) {
            if (writing.charAt(i) == DIAG5WRITING_PARTIAL[i]) {
                correct += 1;
            } else {
                xWriting.add(i + 1);
            }
        }
        int wRaw = correct;
        // May need to be revised depending on completeness of test version.
        int wScaled = WRITINGCONVERTER[Math.round((44 * correct) / (float) 33)];
        return new Integer[][] {new Integer[] {((rScaled + wScaled) * 10), rRaw, wRaw},
                xReading.toArray(new Integer[0]), xWriting.toArray(new Integer[0])};
    }

    private static Integer[][] mathCalculator(String[] args) {
        int correct = 0;
        String mc = args[1];
        int[] topics = new int[] {0, 0, 0, 0};
        ArrayList<Integer> xNonCalc = new ArrayList<>();
        ArrayList<Integer> xCalc = new ArrayList<>();
        // Update following two lines depending on test version.
        for (int i = 0; i < 45; i += 1) {
            if (mc.charAt(i) == DIAG5MATH[i]) {
                correct += 1;
                // Update depending on test version.
                topics[DIAG5MCTOPICS[i]] += 1;
           // Update depending on test version.
            } else if (i < 15){
                xNonCalc.add(i + 1);
            } else {
                // Update depending on test version.
                xCalc.add(i - 14);
            }
        }
        // Update depending on test version.
        for (int i = 2, j = 0; i < 15; i += 1) {
            int[] results = mathFreeHandler(i, j, args);
            j = results[0];
            if (results[1] > 0) {
                correct += 1;
                // Update following five lines depending on test version.
                topics[DIAG5FREETOPICS[i - 2]] += 1;
            } else if (i < 7) {
                xNonCalc.add(i + 14);
            } else {
                xCalc.add(i + 24);
            }
        }
        for (int i = 0; i < 4; i += 1) {
            // Update depending on test version.
            topics[i] = Math.round(((float) topics[i] / (frequency(i, DIAG5MCTOPICS) + frequency(i, DIAG5FREETOPICS))) * 100);
        }
        // May need to be revised depending on completeness of test version.
        return new Integer[][] {{MATHCONVERTER[correct], topics[0], topics[1], topics[2], topics[3]},
                xNonCalc.toArray(new Integer[0]), xCalc.toArray(new Integer[0])};
    }

    private static int[] mathFreeHandler(int i, int j, String[] args) {
        int correct = 0;
        // Update following two lines depending on test version.
        if (i == 3 || i == 4) {
            int buffer = j + 2;
            while (j < buffer) {
                // Update depending on test version.
                if (args[i].equals(DIAG5MATHFREE[j])) {
                    correct += 1;
                }
                j += 1;
            }
        // Update following two lines depending on test version.
        } else if (i == 8) {
            int buffer = j + 3;
            while (j < buffer) {
                // Update depending on test version.
                if (args[i].equals(DIAG5MATHFREE[j])) {
                    correct += 1;
                }
                j += 1;
            }
        // Update following two lines depending on test version.
        } else if (i == 13) {
            int buffer = j + 4;
            while (j < buffer) {
                // Update depending on test version.
                if (args[i].equals(DIAG5MATHFREE[j])) {
                    correct += 1;
                }
                j += 1;
            }
        // Add additional conditional cases as necessary.
        } else {
            // Update depending on test version.
            if (args[i].equals(DIAG5MATHFREE[j])) {
                correct += 1;
            }
            j += 1;
        }
        return new int[] {j, correct};
    }

    private static int frequency(int target, int[] array) {
        int freq = 0;
        for (int num : array) {
            if (num == target) {
                freq += 1;
            }
        }
        return freq;
    }
}

/**
 Topic breakdowns for tests not currently used.
 TEST 4 NON-CALC
 System.out.println("Basic Algebra: 1, 2, 3, 4, 7, 8, 10, 11, 12, 19, 20");
 System.out.println("Advanced Algebra: 5, 6, 9, 13, 14, 15, 18");
 System.out.println("Geometry: 16, 17");
 System.out.println("Data Analysis: N/A");
 TEST 4 CALC
 System.out.println("Basic Algebra: 1, 2, 3, 4, 5, 6, 8, 14, 16, 17, 19, 21, 22, 26, 31, 32, 33, 34");
 System.out.println("Advanced Algebra: 12, 13, 15, 20, 25, 28, 30, 35, 37, 38");
 System.out.println("Geometry: 18, 24, 36");
 System.out.println("Data Analysis: 7, 9, 10, 11, 23, 27, 29");
 */
