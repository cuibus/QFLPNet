package fuzzy;

public enum FuzzyValue {
    A1(1), A2(2), A3(3), A4(4);

    public int value;

    FuzzyValue(int value) {
        this.value = value;
    }
}