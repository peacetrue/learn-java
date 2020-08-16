package com.github.peacetrue.learn.designpattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

/**
 * @author : xiayx
 * @since : 2020-08-15 11:18
 **/
public class InterpreterClient8_Refactor extends InterpreterClient7_Bracket {

    @Nullable
    public static String[] splitByLastString(String string, String delimiter) {
        //根据最后一个匹配字符分割成两段，前面是子表达式，后面是数值
        int index = string.lastIndexOf(delimiter);
        if (index == -1) return null;
        return new String[]{string.substring(0, index), string.substring(index + 1)};
    }

    public abstract static class PartialExpression implements Expression {
        protected Expression expression;
    }


    public abstract static class CalculationalExpression extends PartialExpression {
        protected Bridge bridge = new BridgeImpl();

        @Override
        public Object interpret(String strExpr) {
            return bridge.interpret(this, strExpr);
        }

        protected abstract String getOperator();

        protected abstract Object calculate(Object leftValue, Object rightValue);
    }

    public interface Bridge {
        Object interpret(CalculationalExpression expression, String strExpr);
    }

    public static class BridgeImpl implements Bridge {
        @Override
        public Object interpret(CalculationalExpression calculationalExpression, String strExpr) {
            String[] parts = splitByLastString(strExpr, calculationalExpression.getOperator());
            if (parts == null) return calculationalExpression.expression.interpret(strExpr);
            //左边是子表达式，所以递归调用当前方法处理表达式；右边是数值，保持不变
            return calculationalExpression.calculate(
                    calculationalExpression.interpret(parts[0]),
                    calculationalExpression.expression.interpret(parts[1])
            );
        }
    }

    public static class DivideExpression extends CalculationalExpression {

        public DivideExpression() {
            this.expression = new NumberExpression();
        }

        @Override
        protected String getOperator() {
            return "/";
        }

        @Override
        protected Object calculate(Object leftValue, Object rightValue) {
            return (int) leftValue / (int) rightValue;
        }
    }

    public static class MultiplyExpression extends CalculationalExpression {

        public MultiplyExpression() {
            this.expression = new DivideExpression();
        }

        @Override
        protected String getOperator() {
            return "*";
        }

        @Override
        protected Object calculate(Object leftValue, Object rightValue) {
            return (int) leftValue * (int) rightValue;
        }
    }

    public static class SubtractExpression extends CalculationalExpression {

        public SubtractExpression() {
            this.expression = new MultiplyExpression();
        }

        @Override
        protected String getOperator() {
            return "-";
        }

        @Override
        protected Object calculate(Object leftValue, Object rightValue) {
            return (int) leftValue - (int) rightValue;
        }
    }

    public static class AddExpression extends CalculationalExpression {

        public AddExpression() {
            this.expression = new SubtractExpression();
        }

        @Override
        protected String getOperator() {
            return "+";
        }

        @Override
        protected Object calculate(Object leftValue, Object rightValue) {
            return (int) leftValue + (int) rightValue;
        }
    }

    public static class BracketExpression extends PartialExpression {

        public BracketExpression() {
            this.expression = new AddExpression();
        }

        @Override
        public Object interpret(String strExpr) {
            int leftBracket = strExpr.lastIndexOf("(");
            if (leftBracket == -1) return expression.interpret(strExpr);

            int rightBracket = strExpr.indexOf(")", leftBracket);
            if (rightBracket == -1) throw new IllegalArgumentException(strExpr + "缺少对应的右括号");

            String subStrExpr = strExpr.substring(leftBracket + 1, rightBracket);
            Object value = expression.interpret(subStrExpr);
            String newStrExpr = strExpr.substring(0, leftBracket) + value + strExpr.substring(rightBracket + 1);
            return this.interpret(newStrExpr);
        }
    }

    @Test
    void testRefactor() {
        BracketExpression bracketExpression = new BracketExpression();
        Assertions.assertEquals(9, bracketExpression.interpret("(2-30-20+8+4*10+20/20+8)"));
        Assertions.assertEquals(1, bracketExpression.interpret("(2-30-20+8+4*10+20/20+8)/(1+8)"));
        Assertions.assertEquals(2, bracketExpression.interpret("1+(2-30-20+8+4*10+20/20+8)/(1+8)"));
        Assertions.assertEquals(0, bracketExpression.interpret("1+(2-30-20+8+4*10+20/20+8)/(1+8)-2"));
    }

}
