package com.ayy.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/03/2021
 * @ Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private Integer age;
    private Boolean sex;
    private Date birth;
    private Map<String, Object> map;
    private List<Object> list;
    private Couple couple;

    public Person() {}

    public Person(String name, Integer age, Boolean sex, Date birth, Map<String, Object> map, List<Object> list, Couple couple) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.birth = birth;
        this.map = map;
        this.list = list;
        this.couple = couple;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", birth=" + birth +
                ", map=" + map +
                ", list=" + list +
                ", couple=" + couple +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Couple getCouple() {
        return couple;
    }

    public void setCouple(Couple couple) {
        this.couple = couple;
    }
}
