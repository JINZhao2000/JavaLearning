package com.ayy.webserver.server;

import java.util.HashSet;
import java.util.Set;

/**
 * @ ClassName Mapping
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/01/2021 22H
 * @ Version 1.0
 */
public class Mapping {
    private String name;
    private Set<String> patterns;

    public Mapping() {
        patterns = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(Set<String> patterns) {
        this.patterns = patterns;
    }

    public void addPattern(String pattern){
        this.patterns.add(pattern);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mapping{name: ").append(name);
        sb.append(",patterns: ");
        for (String s : patterns){
            sb.append(s);
            sb.append(",");
        }
        sb.replace(sb.length()-1,sb.length(),"");
        sb.append("}");
        return sb.toString();
    }
}
