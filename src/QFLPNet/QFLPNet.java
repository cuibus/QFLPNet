package QFLPNet;

import fuzzy.FuzzyToken;
import fuzzy.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fuzzy.Table1x1;
import fuzzy.Table1x2;
import fuzzy.Table2x1;
import fuzzy.Table2x2;

import org.json.JSONArray;
import org.json.JSONObject;

public class QFLPNet {
    public String net;
    // (a1, a2) -> (a1*a2)
    // (a1, a2, c3) -> (a1&a2)*c3, has 2 inputs (to a1,a2), has 1 output of c3
    //// (b3, a1, a2) -> b3*(a1&a2), has 1 input (to b3), has 2 outputs of a1, a2
    /// (d1, d2) -> d1*d2, has 2 inputs (to d1) returns 2 outputs of d2
    /// (d3, a1, a2) -> d3

    public Table[] transitions;

    public static QFLPNet readFromString(String QFLPNjson) {
        JSONObject root = new JSONObject(QFLPNjson);

        QFLPNet qflpnet = new QFLPNet();
        qflpnet.net = root.getString("layout");

        JSONArray transitionsNode = root.getJSONArray("transitions");
        qflpnet.transitions = new Table[transitionsNode.length()];

        for (int i = 0; i < transitionsNode.length(); i++) {
            JSONObject node = transitionsNode.getJSONObject(i);
            String name  = node.getString("name");
            String type  = node.getString("type");
            String rules = node.getString("rules");

            qflpnet.transitions[i] = switch (type) {
                case "1x1" -> Table1x1.fromString(name, rules);
                case "1x2" -> Table1x2.fromString(name, rules);
                case "2x1" -> Table2x1.fromString(name, rules);
                case "2x2" -> Table2x2.fromString(name, rules);
                default    -> throw new IllegalArgumentException("Uknown transition type: " + type);
            };
        }

        return qflpnet;
    }

    public FuzzyToken[] execute(List<FuzzyToken> inputs){
        return parseAndSimulate(new int[] {0}, new ArrayList<>(inputs)); // make a mutable copy of the list
    }

    private FuzzyToken[] parseAndSimulate(int[] pos, List<FuzzyToken> inputs){
        if (net.charAt(pos[0]) == '('){
            pos[0]++; // consuma '('
            String transition = readOneTokenFromString(net, pos);

            List<FuzzyToken> operands = new ArrayList<>();
            while (net.charAt(pos[0]) == ',') {
                pos[0]++; // consumă ','
                operands.addAll(List.of(parseAndSimulate(pos, inputs)));
            }
            pos[0]++; // consuma ')'
            return execute(transition, operands.toArray(new FuzzyToken[0]));
        } else { // frunza
            String leafToken = readOneTokenFromString(net, pos);
            return resolveLeaf(leafToken, inputs);
        }
    }

    private FuzzyToken[] resolveLeaf(String leafToken, List<FuzzyToken> inputs) {
        int transitionIndex = getTransitionIndex(leafToken);
        if (transitions[transitionIndex].getNrInputs() == 1) {
            return execute(leafToken, new FuzzyToken[] {inputs.removeFirst()} );
        } else {
            return execute(leafToken, new FuzzyToken[] { inputs.removeFirst(), inputs.removeFirst()} );
        }
    }

    private FuzzyToken[] execute(String operator, FuzzyToken[] operands) {
        int transitionIndex = getTransitionIndex(operator);
        System.out.println("executing " + operator + "" + transitionIndex + ", " +
                Arrays.stream(operands).map(FuzzyToken::toString).collect(Collectors.joining(", "))
        );

        return transitions[transitionIndex].execute(operands);
    }


    private static String readOneTokenFromString(String net, int[] pos) {
        int start = pos[0];
        while (pos[0] < net.length()
                && net.charAt(pos[0]) != ','
                && net.charAt(pos[0]) != '('
                && net.charAt(pos[0]) != ')') {
            pos[0]++;
        }
        return net.substring(start, pos[0]);
    }

    private static int getTransitionIndex(String transition){
        return Integer.parseInt(transition.substring(1));
    }
}
