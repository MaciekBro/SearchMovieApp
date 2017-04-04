package com.example.rent.searchmovieapp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calculator {

    public int sum(int i, int j) {

        return i+j;
    }

    public List<Integer> sort(List<Integer> unsortedList) {
        List<Integer> copy = new ArrayList<>(unsortedList);
        Collections.sort(copy);
        return copy;
    }
}
