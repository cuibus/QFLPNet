import QFLPNet.QFLPNet;
import fuzzy.FuzzyToken;

import java.util.List;

public class TestQFLPNMain {
    public static String qflpNetAsString = "{\"transitions\":[{\"name\":\"t0\",\"type\":\"1x1\",\"rules\":\"A1,A2,A3,A4\"},{\"name\":\"t1\",\"type\":\"1x2\",\"rules\":\"A1|A2,A2|A3,A3|A4,A4|A4\"},{\"name\":\"t2\",\"type\":\"2x1\",\"rules\":\"A1,A2,A3,A4;A1,A2,A3,A4;A1,A2,A3,A4;A1,A2,A3,A4\"},{\"name\":\"t3\",\"type\":\"2x2\",\"rules\":\"A1|A2,A2|A3,A3|A4,A4|A4;A1|A2,A2|A3,A3|A4,A4|A4;A1|A2,A2|A3,A3|A4,A4|A4;A1|A2,A2|A3,A3|A4,A4|A4\"}],\"layout\":\"(t3,(t2,t1),t0)\"}";
    public static void main(String[] args){
        QFLPNet qflpNet = QFLPNet.readFromString(qflpNetAsString);

        System.out.println("===Test read QFLPN net from string===");
        System.out.println("Layout: " + qflpNet.net);
        System.out.println("Transitions: ");
        for (int i=0;i<qflpNet.transitions.length; i++) {
            System.out.println("\t" + qflpNet.transitions[i].name + ": " + qflpNet.transitions[i].type + ", " +
                    qflpNet.transitions[i].toString());
        }

        System.out.println("===Test execute QFLPN net===");

        FuzzyToken input1 = FuzzyToken.fuzzify(2.5);
        FuzzyToken input2 = FuzzyToken.fuzzify(3.2);

        System.out.println("\nInput 1 (2.5): " + input1);
        System.out.println("Input 2 (3.2):" + input2);

        FuzzyToken[] output1 = qflpNet.execute(List.of(input1, input2));
        System.out.println("output1: " + output1[0] + ", output2: " + output1[1]);
        System.out.println("Defuzzified output1: " + output1[0].defuzzify() + ", defuzzified output2: " + output1[1].defuzzify());

    }
}
