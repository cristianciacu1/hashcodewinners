package com.company;

import java.util.HashMap;

public class Person {

    String name;
    HashMap<String, Integer> skillList = new HashMap<>();

    public Person(String name, HashMap<String, Integer> skillList) {
        this.name = name;
        this.skillList = skillList;
    }
}
