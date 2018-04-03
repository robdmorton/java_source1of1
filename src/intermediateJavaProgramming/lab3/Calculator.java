package intermediateJavaProgramming.lab3;

import java.util.StringTokenizer;

public class Calculator {

    public int calculate(String expression) {
        // tokenize the expression
        // this is where it gets interesting.
        StringTokenizer tok = new StringTokenizer(expression);
        // my tokens are the operands and operators
        // the sequence should be operand { operator operand }
        if (tok.countTokens() == 0) {
            return 0; // no valid values
        }
        int result = Integer.parseInt(tok.nextToken());
        while (tok.hasMoreTokens()) {
            String operator = tok.nextToken();
            int operand = Integer.parseInt(tok.nextToken());
            if (operator.equals("+")) {
                result = result + operand;
            }
            if (operator.equals("-")) {
              result = result - operand;
            }            
        }
        // accumulate the result, based on the operands and operators
        // return the result
        return result;
    }

}
