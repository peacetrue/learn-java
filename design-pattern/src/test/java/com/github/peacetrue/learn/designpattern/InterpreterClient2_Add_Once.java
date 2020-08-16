package com.github.peacetrue.learn.designpattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

/**
 * @author : xiayx
 * @since : 2020-08-15 04:57
 **/
public class InterpreterClient2_Add_Once extends InterpreterClient1_Literal {

    @Nullable
    public static String[] splitByLastChar(String string, char delimiter) {
        //根据最后一个匹配字符分割成两段，前面是子表达式，后面是数值
        int index = string.lastIndexOf(delimiter);
        if (index == -1) return null;
        return new String[]{string.substring(0, index), string.substring(index + 1)};
    }

    public static class AddExpression implements Expression {
        private NumberExpression numberExpression = new NumberExpression();

        public Object interpret(String strExpr) {
            String[] parts = splitByLastChar(strExpr, '+');
            //不含 + 号，委派给 NumberExpression 处理
            if (parts == null) return numberExpression.interpret(strExpr);
            return (int) numberExpression.interpret(parts[0]) + (int) numberExpression.interpret(parts[1]);
        }
    }

    @Test
    void testAddOnce() {
        Assertions.assertEquals(99, new AddExpression().interpret("0+99"));
        Assertions.assertEquals(32, new AddExpression().interpret("2+30"));
    }
}
