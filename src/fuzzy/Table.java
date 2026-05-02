package fuzzy;

public abstract class Table {
    public String type; // can be "1x1", "1x2", "2x1", "2x2"
    public String name;
    public int getNrInputs() {
        switch (type) {
            case "1x1": return 1;
            case "1x2": return 1;
            case "2x1": return 2;
            case "2x2": return 2;
            default: return -1;
        }
    }
    public abstract FuzzyToken[] execute(FuzzyToken[] inputs);
}
