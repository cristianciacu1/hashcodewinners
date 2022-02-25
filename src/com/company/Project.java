package com.company;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Project {

    String name;
    int duration;
    int score;
    int deadLine;
    int roles;
    LinkedHashMap<String, Integer> requiredSkills = new LinkedHashMap<>();

    public Project(String name, int duration, int score, int deadLine, int roles, LinkedHashMap<String, Integer> requiredSkills) {
        this.name = name;
        this.duration = duration;
        this.score = score;
        this.deadLine = deadLine;
        this.roles = roles;
        this.requiredSkills = requiredSkills;
    }
}
