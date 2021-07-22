package study.step2;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static study.step2.Exception.*;

public class InterpreterTest {

    private final Interpreter interpreter = new SimpleInterpreter();

    @Test
    @DisplayName("입력값이 null 인 경우")
    public void nullInput() {
        // given
        String input = null;
        String message = NULL_INPUT.getMessage();

        // when
        ThrowingCallable throwingCallable = () -> interpreter.read(input);

        // then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @Test
    @DisplayName("입력값이 빈문자열인 경우")
    public void emptyInput() {
        // given
        String input = "";
        String message = BLANK_INPUT.getMessage();

        // when
        ThrowingCallable throwingCallable = () -> interpreter.read(input);

        // then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @ParameterizedTest(name = "숫자 자리에 숫자가 아닌 경우 | {arguments}")
    @CsvSource(value = {"+ 3 - 1:\"+\"", "1 - 2 + - 3:\"-\""}, delimiter = ':')
    public void notNumber(String input, String operator) {
        // given
        String message = "For input string: " + operator;

        // when
        ThrowingCallable throwingCallable = () -> interpreter.read(input);

        // then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(NumberFormatException.class)
                .hasMessage(message);
    }

    @ParameterizedTest(name = "연산자 자리에 적합한 연산 기호가 아닌 경우 | {arguments}")
    @CsvSource(value = {"7 ^ 3:^", "1 ! 2:!", "4 ( 0:(", "8 - 0 # 5 + 2:#"}, delimiter = ':')
    public void notOperator(String input, String operator) {
        // given
        String message = NOT_OPERATOR.getMessage(operator);

        // when
        ThrowingCallable throwingCallable = () -> interpreter.read(input);

        // then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @ParameterizedTest(name = "수식 마지막에 숫자가 한개 부족한 경우 경우 경우 | {arguments}")
    @ValueSource(strings = {"3 - 1 +", "1 - 2 + 3 *"})
    public void numberShortage(String input) {
        // given
        String message = NUMBER_SHORTAGE.getMessage();

        // when
        ThrowingCallable throwingCallable = () -> interpreter.read(input);

        // then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

}
