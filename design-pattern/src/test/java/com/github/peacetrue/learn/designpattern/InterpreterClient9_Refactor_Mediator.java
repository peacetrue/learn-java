package com.github.peacetrue.learn.designpattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class InterpreterClient9_Refactor_Mediator extends InterpreterClient8_Refactor {

    public static class BridgeImpl implements Bridge {
        @Override
        public Object interpret(InterpreterClient8_Refactor.CalculationalExpression expression, String strExpr) {
            String regex = String.format("%s(?=[^%s]+$)", Pattern.quote(expression.getOperator()), expression.getOperator());
            String[] parts = strExpr.split(regex, 2);
            return expression.calculate(
                    expression.expression.interpret(parts[0]),
                    expression.expression.interpret(parts[1])
            );
        }
    }

    @Slf4j
    public static class MediatorExpression implements Expression {

        private final List<CalculationalExpression> expressions = Arrays.asList(
                new AddExpression(),
                new SubtractExpression(),
                new MultiplyExpression(),
                new DivideExpression()
        );

        {
            BridgeImpl delegate = new BridgeImpl();
            expressions.forEach(expression -> {
                expression.bridge = delegate;
                expression.expression = this;
            });
        }

        @Override
        public Object interpret(String strExpr) {
            for (CalculationalExpression expression : expressions) {
                if (strExpr.contains(expression.getOperator())) {
                    return expression.interpret(strExpr);
                }
            }
            return Integer.parseInt(strExpr.trim());
        }
    }

    @Test
    void testRefactorMediator() {
        BracketExpression calculator = new BracketExpression();
        calculator.expression = new MediatorExpression();
        Assertions.assertEquals(6, calculator.interpret("2*3"));
        Assertions.assertEquals(7, calculator.interpret("1+2*3"));
        Assertions.assertEquals(11, calculator.interpret("1+2*3+4"));
        Assertions.assertEquals(16, calculator.interpret("2+2*3+4*2"));
        Assertions.assertEquals(30, calculator.interpret("1+2*3*4+5"));
        Assertions.assertEquals(40, calculator.interpret("1+2*3*4+5+5*2"));
        Assertions.assertEquals(10, calculator.interpret("0-99-10+9+10*10+10/10+9"));
        Assertions.assertEquals(9, calculator.interpret("2-30-20+8+4*10+20/20+8"));
        Assertions.assertEquals(9, calculator.interpret("(2-30-20+8+4*10+20/20+8)"));
        Assertions.assertEquals(1, calculator.interpret("(2-30-20+8+4*10+20/20+8)/(1+8)"));
        Assertions.assertEquals(2, calculator.interpret("1+(2-30-20+8+4*10+20/20+8)/(1+8)"));
        Assertions.assertEquals(0, calculator.interpret("1+(2-30-20+8+4*10+20/20+8)/3/(1+2)-2"));
    }

}
