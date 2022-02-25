package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static List<String> skills = new ArrayList<>();
    static Map<String, Person> mostSkilled = new HashMap<>();
    static List<Person> personList = new ArrayList<>();
    static List<Project> projectList = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        smecheriaLuiIonut();
    }

    public static void readFile() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("text.txt"));

        int n = scanner.nextInt(); // no contributors
        int m = scanner.nextInt(); // no projects

        for(int i = 0; i < n; i++){
            String name = scanner.next();
            int skillsNo = scanner.nextInt();

            HashMap<String, Integer> map = new HashMap<>();

            for(int j=0; j<skillsNo; j++){
                String skill = scanner.next();
                int level = scanner.nextInt();
                if(!skills.contains(skill))
                    skills.add(skill);

                map.put(skill, level);

            }

            personList.add(new Person(name, map));

        }

        for(int i=0; i<m; i++){
            String name = scanner.next();
            int duration = scanner.nextInt();
            int score = scanner.nextInt();
            int bestBefore = scanner.nextInt();
            int roles = scanner.nextInt();
            LinkedHashMap<String, Integer> hashMap = new LinkedHashMap<>();
            boolean ok = true;
            for(int j = 0; j<roles; j++){
                String skillName = scanner.next();
                int level = scanner.nextInt();
                if(hashMap.containsKey(skillName))
                    ok = false;
                hashMap.put(skillName, level);
            }
            if(ok)
                projectList.add(new Project(name, duration, score, bestBefore, roles, hashMap));
        }

    }

    public static boolean checkCondition(Project project){

        HashMap<Person, String> peopleThatAreWorkingOnThisProject = new HashMap<>();
        HashMap<String, Boolean> checkedSkills = new HashMap<>();

        for(Map.Entry<String, Integer> entry : project.requiredSkills.entrySet()){

            String skillName = entry.getKey();
            int skillLevel = entry.getValue();
            checkedSkills.put(skillName, false);

            for(Person person : personList){
                if(person.skillList.containsKey(skillName) && person.skillList.get(skillName) >= skillLevel){
                    peopleThatAreWorkingOnThisProject.put(person, skillName);
                    checkedSkills.replace(skillName, true);
                }
            }

        }

        for(Map.Entry<String, Boolean> entry : checkedSkills.entrySet()){
            if(!entry.getValue())
                return false;
        }

        for(Map.Entry<Person, String> entry : peopleThatAreWorkingOnThisProject.entrySet()){
            Person person = entry.getKey();
            String skill = entry.getValue();
            if(Objects.equals(person.skillList.get(skill), project.requiredSkills.get(skill)))
                person.skillList.replace(skill, person.skillList.get(skill) + 1);
        }

        return true;
    }

    public static void ordering(){

        for(String skill : skills){

            int maxim = Integer.MIN_VALUE;
            Person persoana = null;
            for(Person person : personList){

                if(person.skillList.containsKey(skill)){
                    int valoare = person.skillList.get(skill);
                    if(valoare > maxim){
                        maxim = valoare;
                        persoana = person;
                    }
                }

            }

            mostSkilled.put(skill, persoana);

        }

    }

    public static void smecheriaLuiIonut(){

        ordering();

        int n = 0;

        HashMap<Project, List<Person>> doneProjects = new HashMap<>();

        for(Project project : projectList){

            boolean ok = true;

            doneProjects.put(project, new ArrayList<>());

            for(Map.Entry<String, Integer> entry : project.requiredSkills.entrySet()){

                String currentSkill = entry.getKey();
                int requiredLevel = entry.getValue();

                if(mostSkilled.get(currentSkill).skillList.get(currentSkill) >= requiredLevel){
                    if(!doneProjects.get(project).contains(mostSkilled.get(currentSkill))){
                        if(mostSkilled.get(currentSkill).skillList.get(currentSkill) == requiredLevel)
                            mostSkilled.get(currentSkill).skillList.replace(currentSkill, mostSkilled.get(currentSkill).skillList.get(currentSkill) + 1);
                        doneProjects.get(project).add(mostSkilled.get(currentSkill));
                    }
                }
                else{
                    ok = false;
                }

            }

            if(doneProjects.get(project).size() == project.requiredSkills.size()){
                n++;
                System.out.println(project.name);
                for(int i=0; i<project.requiredSkills.size(); i++){
                    System.out.print(doneProjects.get(project).get(i).name + " ");
                }
                System.out.println();
            }

        }

        System.out.println(n);

    }

}
