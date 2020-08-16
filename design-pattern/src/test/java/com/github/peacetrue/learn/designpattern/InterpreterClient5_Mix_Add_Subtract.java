package com.github.peacetrue.learn.designpattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : xiayx
 * @since : 2020-08-15 04:57
 **/
public class InterpreterClient5_Mix_Add_Subtract extends InterpreterClient4_Subtract_Multiple {

    public static class AddExpression implements Expression {
        private SubtractExpression subtractExpression = new SubtractExpression();

        public Object interpret(String strExpr) {
            String[] parts = splitByLastChar(strExpr, '+');
            if (parts == null) return subtractExpression.interpret(strExpr);
            //左边是子表达式，所以递归调用当前方法处理表达式；右边是数值或者含减号的表达式，保持不变
            return (int) this.interpret(parts[0]) + (int) subtractExpression.interpret(parts[1]);
        }
    }

    @Test
    void testMixAddSubtract() {
        Assertions.assertEquals(-100, new AddExpression().interpret("0-99-10+9"));
        Assertions.assertEquals(-40, new AddExpression().interpret("2-30-20+8"));
    }
}
