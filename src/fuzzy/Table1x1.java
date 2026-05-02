package fuzzy;
public class Table1x1 extends Table {
    public FuzzyValue[] rules; // 4 elements for A1, A2, A3, A4 outputs

    public Table1x1(String name, FuzzyValue[] rules) { this.name = name; this.rules = rules; this.type = "1x1"; }
    public Table1x1(FuzzyValue[] rules) { this("", rules); }

    public FuzzyToken[] execute(FuzzyToken[] input) {
        FuzzyToken[] output = new FuzzyToken[] { new FuzzyToken() };

        for (int i = 0; i < 4; i++) {
            double strength = input[0].membershipDegrees[i];
            FuzzyValue outputLabel = rules[i];
            int outputIndex = outputLabel.ordinal();
            output[0].membershipDegrees[outputIndex] += strength;
        }

        return output;
    }

    public static Table fromIntVector(String name, int[] rulesVector) {
        FuzzyValue[] rules = new FuzzyValue[4];
        for (int i = 0; i < 4; i++) {
            rules[i] = FuzzyValue.values()[rulesVector[i] - 1];
        }
        return new Table1x1(name, rules);
    }

    // Create table from string representation
    public static Table fromString(String name, String str) {
        String[] parts = str.split(",");
        FuzzyValue[] rules = new FuzzyValue[4];
        for (int i = 0; i < 4; i++) {
            rules[i] = FuzzyValue.valueOf(parts[i].trim());
        }
        return new Table1x1(name, rules);
    }

    // Convert table to string representation
    public String toString() {
        return (name == "" ? "": name+": ") + rules[0] + "," + rules[1] + "," + rules[2] + "," + rules[3];
    }
}