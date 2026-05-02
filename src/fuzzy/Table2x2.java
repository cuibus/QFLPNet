package fuzzy;

public class Table2x2 extends Table{
    public FuzzyValue[][][] rules; // 2 matrices of 4x4 for outputs

    public Table2x2(String name, FuzzyValue[][][] rules) { this.name = name; this.rules = rules; this.type = "2x2"; }
    public Table2x2(FuzzyValue[][][] rules) { this("", rules); }

    public FuzzyToken[] execute(FuzzyToken[] input) {
        FuzzyToken[] output = new FuzzyToken[] { new FuzzyToken(), new FuzzyToken() };

        for (int out = 0; out < 2; out++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    double strength = input[0].membershipDegrees[i] * input[1].membershipDegrees[j];
                    FuzzyValue outputLabel = rules[out][i][j];
                    int outputIndex = outputLabel.ordinal();
                    output[out].membershipDegrees[outputIndex] += strength;
                }
            }
        }

        return output;
    }

    public static Table fromIntVector(String name, int[][][] rulesMatrix) {
        FuzzyValue[][][] rules = new FuzzyValue[2][4][4];
        for (int out = 0; out < 2; out++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    rules[out][i][j] = FuzzyValue.values()[rulesMatrix[out][i][j] - 1];
                }
            }
        }
        return new Table2x2(name, rules);
    }

    public static Table fromString(String name, String str) {
        String[] rows = str.split(";");
        FuzzyValue[][][] rules = new FuzzyValue[2][4][4];
        for (int out = 0; out < 2; out++) {
            for (int i = 0; i < 4; i++) {
                String[] cols = rows[i].split(",");
                for (int j = 0; j < 4; j++) {
                    rules[out][i][j] = FuzzyValue.valueOf(cols[j].split("\\|")[out].trim());
                }
            }
        }
        return new Table2x2(name, rules);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(name == "" ? "": name+": ");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int out = 0; out < 2; out++) {
                    sb.append(rules[out][i][j]);
                    if (out < 1) sb.append("|");
                }
                if (j < 3) sb.append(",");
            }
            if (i < 3) sb.append(";");
        }

        return sb.toString();
    }
}

