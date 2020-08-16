package com.github.peacetrue.learn.designpattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : xiayx
 * @since : 2020-08-15 04:57
 **/
public class InterpreterClient3_Add_Multiple extends InterpreterClient2_Add_Once {

    public static class AddExpression implements Expression {
        private NumberExpression numberExpression = new NumberExpression();

        public Object interpret(String strExpr) {
            String[] parts = splitByLastChar(strExpr, '+');
            if (parts == null) return numberExpression.interpret(strExpr);
            //左边是子表达式，所以递归调用当前方法处理表达式；右边是数值，保持不变
            return (int) this.interpret(parts[0]) + (int) numberExpression.interpret(parts[1]);
        }
    }

    @Test
    void testAddMultiple() {
        Assertions.assertEquals(109, new AddExpression().interpret("0+99+10"));
        Assertions.assertEquals(52, new AddExpression().interpret("2+30+20"));
    }
}
