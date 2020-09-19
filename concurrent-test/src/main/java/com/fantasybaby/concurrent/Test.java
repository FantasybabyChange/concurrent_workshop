package com.fantasybaby.concurrent;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Test> list = new ArrayList<>();
        while(true){
            Test test = new Test();
            list.add(test);
        }
    }
}
