package fuzzy;

public class Table2x1 extends Table{
    public FuzzyValue[][] rules; // 4x4 matrix for outputs

    public Table2x1(String name, FuzzyValue[][] rules) { this.name = name; this.rules = rules; this.type = "2x1"; }
    public Table2x1(FuzzyValue[][] rules) { this("", rules); }

    public FuzzyToken[] execute(FuzzyToken[] input) {
        FuzzyToken[] output = new FuzzyToken[] { new FuzzyToken() };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double strength = input[0].membershipDegrees[i] * input[1].membershipDegrees[j];
                FuzzyValue outputLabel = rules[i][j];
                int outputIndex = outputLabel.ordinal();
                output[0].membershipDegrees[outputIndex] += strength;
            }
        }

        return output;
    }

    public static Table fromIntVector(String name, int[][] rulesMatrix) {
        FuzzyValue[][] rules = new FuzzyValue[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rules[i][j] = FuzzyValue.values()[rulesMatrix[i][j] - 1];
            }
        }
        return new Table2x1(name, rules);
    }

    public static Table fromString(String name, String str) {
        String[] rows = str.split(";");
        FuzzyValue[][] rules = new FuzzyValue[4][4];
        for (int i = 0; i < 4; i++) {
            String[] cols = rows[i].split(",");
            for (int j = 0; j < 4; j++) {
                rules[i][j] = FuzzyValue.valueOf(cols[j].trim());
            }
        }
        return new Table2x1(name, rules);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(name == "" ? "": name+": ");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append(rules[i][j]);
                if (j < 3) sb.append(",");
            }
            if (i < 3) sb.append(";");
        }
        return sb.toString();
    }
}

