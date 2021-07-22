package study.step2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static study.step2.Exception.NUMBER_SHORTAGE;

public class Expression {

    private final List<Integer> numbers;
    private final List<Operator> operators;

    public Expression(List<Integer> numbers, List<Operator> operators) {
        NUMBER_SHORTAGE.validation(isValidExpression(numbers, operators), NUMBER_SHORTAGE.getMessage());
        this.numbers = numbers;
        this.operators = operators;
    }

    private boolean isValidExpression(List<Integer> numbers, List<Operator> operators) {
        return numbers.size() == operators.size() + 1;
    }

    public void addNumber(int number) {
        numbers.add(number);
    }

    public void addOperator(Operator operator) {
        operators.add(operator);
    }

    public List<Integer> getNumbers() {
        return Collections.unmodifiableList(numbers);
    }

    public LinkedList<Operator> getOperator() {
        return new LinkedList<>(operators);
    }
}
