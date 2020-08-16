package com.github.peacetrue.learn.designpattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : xiayx
 * @since : 2020-08-15 04:57
 **/
public class InterpreterClient6_Mix_All extends InterpreterClient5_Mix_Add_Subtract {

    public static class DivideExpression implements Expression {

        private NumberExpression numberExpression = new NumberExpression();

        public Object interpret(String strExpr) {
            String[] parts = splitByLastChar(strExpr, '/');
            if (parts == null) return numberExpression.interpret(strExpr);
            //左边是子表达式，所以递归调用当前方法处理表达式；右边是数值或者含减号的表达式，保持不变
            return (int) this.interpret(parts[0]) / (int) numberExpression.interpret(parts[1]);
        }
    }

    public static class MultiplyExpression implements Expression {
        private DivideExpression divideExpression = new DivideExpression();

        public Object interpret(String strExpr) {
            String[] parts = splitByLastChar(strExpr, '*');
            if (parts == null) return divideExpression.interpret(strExpr);
            //左边是子表达式，所以递归调用当前方法处理表达式；右边是数值或者含减号的表达式，保持不变
            return (int) this.interpret(parts[0]) * (int) divideExpression.interpret(parts[1]);
        }
    }

    public static class SubtractExpression implements Expression {
        private MultiplyExpression multiplyExpression = new MultiplyExpression();

        public Object interpret(String strExpr) {
            String[] parts = splitByLastChar(strExpr, '-');
            if (parts == null) return multiplyExpression.interpret(strExpr);
            //左边是子表达式，所以递归调用当前方法处理表达式；右边是数值，保持不变
            return (int) this.interpret(parts[0]) - (int) multiplyExpression.interpret(parts[1]);
        }
    }

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
    void testMixAll() {
        Assertions.assertEquals(10, new AddExpression().interpret("0-99-10+9+10*10+10/10+9"));
        Assertions.assertEquals(9, new AddExpression().interpret("2-30-20+8+4*10+20/20+8"));
    }
}
