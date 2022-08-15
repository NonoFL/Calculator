package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CalculateTest {

    @Spy
    private Calculate in;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void splitTransCalcu() {

    }

    String[] strs = new String[]{
            "1+2-3",
            "1-2-3",
            "1*2/3",
            "1*5+(1-6)"
    };

    @Test
    public void spilt() {
//        List<List<String>> res = new ArrayList<>();
//        Collections.addAll(res,
//                Arrays.asList("1","2","3"),
//                Arrays.asList("1")
//                );
        String str = "1+2*2/2/2";
        List<String> res = Arrays.asList("1","+","2","*","2","/","2","/","2");
        Assertions.assertEquals(res, in.spilt(str));
    }

    @Test
    public void trans() {
        String str = "2*(9+6/3-5)+4";
        List<String> res = Arrays.asList("2","9","6","3","/","+","5","-","*","4","+");
        Assertions.assertEquals(res, in.trans(in.spilt(str)));


    }

    @Test
    public void calculate() {

    }
}