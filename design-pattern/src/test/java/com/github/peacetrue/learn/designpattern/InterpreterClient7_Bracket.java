package com.github.peacetrue.learn.designpattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : xiayx
 * @since : 2020-08-15 11:14
 **/
public class InterpreterClient7_Bracket extends InterpreterClient6_Mix_All {

    public static class BracketExpression implements Expression {

        private AddExpression addExpression = new AddExpression();

        @Override
        public Object interpret(String strExpr) {
            int leftBracket = strExpr.lastIndexOf("(");
            if (leftBracket == -1) return addExpression.interpret(strExpr);

            int rightBracket = strExpr.indexOf(")", leftBracket);
            if (rightBracket == -1) throw new IllegalArgumentException(strExpr + "缺少对应的右括号");

            String subStrExpr = strExpr.substring(leftBracket + 1, rightBracket);
            Object value = addExpression.interpret(subStrExpr);
            String newStrExpr = strExpr.substring(0, leftBracket) + value + strExpr.substring(rightBracket + 1);
            return this.interpret(newStrExpr);
        }
    }

    @Test
    void testBracket() {
        BracketExpression bracketExpression = new BracketExpression();
        Assertions.assertEquals(9, bracketExpression.interpret("(2-30-20+8+4*10+20/20+8)"));
        Assertions.assertEquals(1, bracketExpression.interpret("(2-30-20+8+4*10+20/20+8)/(1+8)"));
        Assertions.assertEquals(2, bracketExpression.interpret("1+(2-30-20+8+4*10+20/20+8)/(1+8)"));
        Assertions.assertEquals(0, bracketExpression.interpret("1+(2-30-20+8+4*10+20/20+8)/(1+8)-2"));
    }
}
