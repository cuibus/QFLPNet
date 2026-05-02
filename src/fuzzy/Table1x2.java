package fuzzy;
public class Table1x2 extends Table {
    public FuzzyValue[][] rules; // 2 arrays of 4 elements for A1, A2, A3, A4 outputs

    public Table1x2(String name, FuzzyValue[][] rules) { this.name = name; this.rules = rules; this.type = "1x2"; }
    public Table1x2(FuzzyValue[][] rules) { this("", rules); }

    public FuzzyToken[] execute(FuzzyToken[] input) {
        FuzzyToken[] output = new FuzzyToken[] { new FuzzyToken(), new FuzzyToken() };

        for (int out = 0; out < 2; out++) {
            for (int i = 0; i < 4; i++) {
                double strength = input[0].membershipDegrees[i];
                FuzzyValue outputLabel = rules[out][i];
                int outputIndex = outputLabel.ordinal();
                output[out].membershipDegrees[outputIndex] += strength;
            }
        }

        return output;
    }

    public static Table fromIntVector(String name, int[][] rulesVector) {
        FuzzyValue[][] rules = new FuzzyValue[2][4];
        for (int out = 0; out < 2; out++) {
            for (int i = 0; i < 4; i++) {
                rules[out][i] = FuzzyValue.values()[rulesVector[out][i] - 1];
            }
        }
        return new Table1x2(name, rules);
    }

    // Create table from string representation
    public static Table fromString(String name, String str) {
        String[] parts = str.split(",");
        FuzzyValue[][] rules = new FuzzyValue[2][4];
        for (int out = 0; out < 2; out++) {
            for (int i = 0; i < 4; i++) {
                rules[out][i] = FuzzyValue.valueOf(parts[i].split("\\|")[out].trim());
            }
        }
        return new Table1x2(name, rules);
    }

    // Convert table to string representation
    public String toString() {
        return (name == "" ? "": name+": ") +
                rules[0][0] + "|" + rules[1][0] + "," + rules[0][1] + "|" + rules[1][1] + "," +
                rules[0][2] + "|" + rules[1][2] + "," + rules[0][3] + "|" + rules[1][3];
    }
}