package fuzzy;
public class FuzzyToken {
    public double[] membershipDegrees; // [A1, A2, A3, A4]

    public FuzzyToken() {
        membershipDegrees = new double[4];
    }

    public FuzzyToken(double crispInput) {
        this();
        fuzzify(crispInput);
    }

    public void fuzzify(double crispInput) {
        if (crispInput <= 1.0) {
            membershipDegrees[0] = 1.0;
            membershipDegrees[1] = 0.0;
            membershipDegrees[2] = 0.0;
            membershipDegrees[3] = 0.0;
            return;
        }
        if (crispInput >= 4.0) {
            membershipDegrees[0] = 0.0;
            membershipDegrees[1] = 0.0;
            membershipDegrees[2] = 0.0;
            membershipDegrees[3] = 1.0;
            return;
        }
        membershipDegrees[0] = calculateMembership(crispInput, FuzzyValue.A1.value);
        membershipDegrees[1] = calculateMembership(crispInput, FuzzyValue.A2.value);
        membershipDegrees[2] = calculateMembership(crispInput, FuzzyValue.A3.value);
        membershipDegrees[3] = calculateMembership(crispInput, FuzzyValue.A4.value);
    }

    private double calculateMembership(double input, int center) {
        double distance = Math.abs(input - center);
        if (distance >= 1.0) return 0.0;
        return 1.0 - distance;
    }

    public double defuzzify() {
        double numerator = FuzzyValue.A1.value * membershipDegrees[0] +
                FuzzyValue.A2.value * membershipDegrees[1] +
                FuzzyValue.A3.value * membershipDegrees[2] +
                FuzzyValue.A4.value * membershipDegrees[3];

        double denominator = membershipDegrees[0] + membershipDegrees[1] +
                membershipDegrees[2] + membershipDegrees[3];

        if (denominator == 0.0) return 0.0;
        return numerator / denominator;
    }

    public String toString(){
        return String.format("[%.2f, %.2f, %.2f, %.2f]",
        membershipDegrees[0], membershipDegrees[1],
                membershipDegrees[2], membershipDegrees[3]);
    }
    public void printVector() {
        System.out.printf("[%.2f, %.2f, %.2f, %.2f]",
                membershipDegrees[0], membershipDegrees[1],
                membershipDegrees[2], membershipDegrees[3]);
    }
}
