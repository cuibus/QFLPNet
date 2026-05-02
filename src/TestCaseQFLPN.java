public class TestCaseQFLPN {
    public double pp, pl, eb, cc_desired;
    private TestCaseQFLPN(double pp, double pl, double eb, double cc_desired){
        this.pp = pp;
        this.pl = pl;
        this.eb = eb;
        this.cc_desired = cc_desired;
        // peste 3 = discharge
        // sub 2 = charge
    }
    public static TestCaseQFLPN TestCase1 = new TestCaseQFLPN(1.1, 1.1, 1.1, 1);
    public static TestCaseQFLPN TestCase2 = new TestCaseQFLPN(3.5, 3.5, 3.5, 4);
    public static TestCaseQFLPN TestCase3 = new TestCaseQFLPN(2.5, 2.5, 2.5, 2.5);
    public static TestCaseQFLPN TestCase4 = new TestCaseQFLPN(1.1, 3.5, 2.0, 4);
    public static TestCaseQFLPN TestCase5 = new TestCaseQFLPN(2.5, 2.5, 1.0, 1);
    public static TestCaseQFLPN TestCase6 = new TestCaseQFLPN(3.1, 1.2, 2.0, 1);

    public static TestCaseQFLPN[] scenarios = {
            TestCase1,
            TestCase2,
            TestCase3,
            TestCase4,
            TestCase5,
            TestCase6
    };
}
