package com.techcamp2.customer.util;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void testAddMethod()
    {
        assertEquals(2,calculator.add(1,1));
        assertEquals(7,calculator.add(2,5));
        assertTrue('a'<'b',() ->"Assertion message can be lazily evaluated");
    }

    @Test
    void testMultiplyMethod()
    {
        assertEquals(4,calculator.multiply(2,2));
        assertEquals(6,calculator.multiply(3,2));
    }

    @Test
    void testDevideMethod()
    {
        assertEquals(4,calculator.divide(8,2));
        assertEquals(6,calculator.divide(12,2));
    }
}
