package com.ayy.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/03/2021
 * @ Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "couple")
public class Couple {
    private String name;
    private Integer age;

    public Couple() {}

    public Couple(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Couple{" +
                "name='" + name + '\'' +
                ", age=" + age +
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
}
