import fuzzy.*;

public class TestFuzzyMain {
    public static void main(String[] args) {
        FuzzyToken fuzzyToken = new FuzzyToken();
        System.out.println("=== Fuzzy Logic System Demo ===\n");

        // Example 1 - FUZZIFY&DEFFUZIFY - normal input
        System.out.println("Input: 2.3");
        fuzzyToken.fuzzify(2.3);
        System.out.print("Membership vector: ");
        System.out.println(fuzzyToken);
        System.out.println("Defuzzified: " + fuzzyToken.defuzzify());

        System.out.println();

        // Example 2 - FUZZIFY&DEFFUZIFY - Edge case: less than 1
        System.out.println("Input: 0.5 (< 1, should be 100% A1)");
        fuzzyToken.fuzzify(0.5);
        System.out.print("Membership vector: ");
        System.out.println(fuzzyToken);
        System.out.println("Defuzzified: " + fuzzyToken.defuzzify());

        System.out.println();

        // Example 3 - FUZZIFY&DEFFUZIFY - Edge case: greater than 4
        System.out.println("Input: 5.0 (> 4, should be 100% A4)");
        fuzzyToken.fuzzify(5.0);
        System.out.print("Membership vector: ");
        System.out.println(fuzzyToken);
        System.out.println("Defuzzified: " + fuzzyToken.defuzzify());


        // APPLY RULES - prepare inputs
        System.out.println();
        System.out.println("=== Fuzzy Rules Tables Demo ===");

        // Create input fuzzy tokens
        FuzzyToken input1 = new FuzzyToken();
        FuzzyToken input2 = new FuzzyToken();

        input1.fuzzify(2.5);
        input2.fuzzify(3.2);

        System.out.println("\nInput 1 (2.5):");
        System.out.println(input1);
        System.out.println("Input 2 (3.2):");
        System.out.println(input2);

        // Example 4 - APPLY RULES - 1x1 table

        System.out.println("=== Table 1x1 ===");
        // Table1x1 example from image: [A4, A3, A2, A1]
        FuzzyValue[] rules1x1 = {FuzzyValue.A4, FuzzyValue.A3, FuzzyValue.A2, FuzzyValue.A1};
        Table1x1 table1x1 = new Table1x1(rules1x1);
        System.out.println(table1x1.toString());

        FuzzyToken[] output1 = table1x1.execute(new FuzzyToken[] { input1 });
        System.out.println("\nTable1x1 output (rules: A4, A3, A2, A1):");
        System.out.println(output1[0]);
        System.out.println("Defuzzified: " + output1[0].defuzzify());

        // Example 5 - APPLY RULES - 2x1 table
        System.out.println("=== Table 2x1 ===");
        // Table2x1 example from image
        FuzzyValue[][] rules2x1 = {
                {FuzzyValue.A1, FuzzyValue.A1, FuzzyValue.A1, FuzzyValue.A1},  // A1 row
                {FuzzyValue.A1, FuzzyValue.A2, FuzzyValue.A2, FuzzyValue.A3},  // A2 row
                {FuzzyValue.A1, FuzzyValue.A2, FuzzyValue.A3, FuzzyValue.A3},  // A3 row
                {FuzzyValue.A4, FuzzyValue.A4, FuzzyValue.A4, FuzzyValue.A4}   // A4 row
        };
        Table2x1 table2x1 = new Table2x1(rules2x1);

        FuzzyToken[] output2 = table2x1.execute(new FuzzyToken[] { input1, input2 });
        System.out.println(table2x1.toString());
        System.out.println("\nTable2x1 output:");
        System.out.println(output2[0]);
        System.out.println("Defuzzified: " + output2[0].defuzzify());

        // Example 6 - APPLY RULES - 1x2 table
        System.out.println("=== Table 1x2 ===");
        FuzzyValue[][] rules1x2 = {
                {FuzzyValue.A1, FuzzyValue.A2, FuzzyValue.A3, FuzzyValue.A4},  // first output
                {FuzzyValue.A4, FuzzyValue.A3, FuzzyValue.A2, FuzzyValue.A1},  // second output
        };
        Table1x2 table1x2 = new Table1x2(rules1x2);

        FuzzyToken[] output3 = table1x2.execute(new FuzzyToken[] { input1 });
        System.out.println(table1x2.toString());
        System.out.println("\nTable1x2 output:");
        System.out.println("output1: " + output3[0] + ", output2: " + output3[1]);
        System.out.println("Defuzzified output1: " + output3[0].defuzzify() + ", defuzzified output2: " + output3[1].defuzzify());

        // Example 7 - APPLY RULES - 2x2 table
        System.out.println("=== Table 2x2 ===");
        FuzzyValue[][][] rules2x2 = { // two identical 1x2 tables
                {{FuzzyValue.A1, FuzzyValue.A1, FuzzyValue.A1, FuzzyValue.A1},  // A1 row
                 {FuzzyValue.A1, FuzzyValue.A2, FuzzyValue.A2, FuzzyValue.A3},  // A2 row
                 {FuzzyValue.A1, FuzzyValue.A2, FuzzyValue.A3, FuzzyValue.A3},  // A3 row
                 {FuzzyValue.A4, FuzzyValue.A4, FuzzyValue.A4, FuzzyValue.A4}},  // A4 row
                {{FuzzyValue.A1, FuzzyValue.A1, FuzzyValue.A1, FuzzyValue.A1},  // A1 row
                 {FuzzyValue.A1, FuzzyValue.A2, FuzzyValue.A2, FuzzyValue.A3},  // A2 row
                 {FuzzyValue.A1, FuzzyValue.A2, FuzzyValue.A3, FuzzyValue.A3},  // A3 row
                 {FuzzyValue.A4, FuzzyValue.A4, FuzzyValue.A4, FuzzyValue.A4}},  // A4 row
        };
        Table2x2 table2x2 = new Table2x2(rules2x2);

        FuzzyToken[] output4 = table2x2.execute(new FuzzyToken[] { input1, input2 });
        System.out.println(table2x2.toString());
        System.out.println("\nTable2x2 output:");
        System.out.println("output1: " + output4[0] + ", output2: " + output4[1]);
        System.out.println("Defuzzified output1: " + output4[0].defuzzify() + ", defuzzified output2: " + output4[1].defuzzify());

    }
}