package com.example.rent.searchmovieapp;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setup(){        //bedzie to wołane przed każdym testem!!
        calculator = new Calculator();
    }

    @Test
    public void shouldAddTwoNumbers(){      //zawsze should na początku testu powinien byc! given, when,then
        //given

        int i = 1;
        int j = 2;

        //when
        int result = calculator.sum(i,j);

        //then
        Assert.assertTrue(result==3);
    }

    @Test
    public void shouldSortListOfNumbers(){
        //given
        List<Integer> unsortedList = Arrays.asList(5,2,8,1);
        //when
        List<Integer> result = calculator.sort(unsortedList);
        //then
        Assert.assertEquals(result, Arrays.asList(1,2,5,8));
    }



}
