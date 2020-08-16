package com.github.peacetrue.learn.designpattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : xiayx
 * @since : 2020-08-15 04:57
 **/
public class InterpreterClient1_Literal {

    public interface Expression {
        Object interpret(String strExpr);
    }

    public static class NumberExpression implements Expression {
        public Object interpret(String strExpr) {
            return Integer.parseInt(strExpr.trim());
        }
    }

    @Test
    void testLiteral() {
        Assertions.assertEquals(0, new NumberExpression().interpret("0"));
        Assertions.assertEquals(5, new NumberExpression().interpret("5"));
        Assertions.assertEquals(99, new NumberExpression().interpret("99"));
    }
}
