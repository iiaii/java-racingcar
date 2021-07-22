package study.step2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static study.step2.Exception.NOT_OPERATOR;

public enum Operator {
    ADD("+", Math::addExact),
    SUBTRACT("-", Math::subtractExact),
    MULTIPLY("*", Math::multiplyExact),
    DIVIDE("/", Math::floorDiv);

    private static final Map<String, Operator> operatorMap = Arrays.stream(Operator.values())
            .collect(Collectors.toMap(Operator::getSymbol, operator -> operator));
    private final String symbol;
    private final BinaryOperator<Integer> operator;

    Operator(String symbol, BinaryOperator<Integer> operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    public String getSymbol() {
        return symbol;
    }

    public int calculate(int a, int b) {
        return operator.apply(a, b);
    }

    public static Operator getOrThrow(String text) {
        Operator operator = operatorMap.get(text);
        NOT_OPERATOR.validation(operator, NOT_OPERATOR.getMessage(text));
        return operator;
    }
}
