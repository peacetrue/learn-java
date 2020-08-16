package com.github.peacetrue.learn.designpattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : xiayx
 * @since : 2020-08-15 04:57
 **/
public class InterpreterClient4_Subtract_Multiple extends InterpreterClient3_Add_Multiple {

    public static class SubtractExpression implements Expression {
        private NumberExpression numberExpression = new NumberExpression();

        public Object interpret(String strExpr) {
            String[] parts = splitByLastChar(strExpr, '-');
            if (parts == null) return numberExpression.interpret(strExpr);
            //左边是子表达式，所以递归调用当前方法处理表达式；右边是数值，保持不变
            return (int) this.interpret(parts[0]) - (int) numberExpression.interpret(parts[1]);
        }
    }

    @Test
    void testSubtract() {
        Assertions.assertEquals(-109, new SubtractExpression().interpret("0-99-10"));
        Assertions.assertEquals(-48, new SubtractExpression().interpret("2-30-20"));
    }
}
