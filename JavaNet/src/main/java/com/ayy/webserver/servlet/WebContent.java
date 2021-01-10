package com.ayy.webserver.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ ClassName WebContent
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/01/2021 20H
 * @ Version 1.0
 */
public class WebContent {
    private List<Entity> entities;
    private List<Mapping> mappings;
    private Map<String,String> entitiesMap = new HashMap<>(); // name->class
    private Map<String,String> mappingsMap = new HashMap<>(); // url-pattern->name

    public WebContent(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;
        for (Entity e : entities){
            entitiesMap.put(e.getName(),e.getCls());
        }
        for (Mapping m : mappings){
            for (String s : m.getPatterns()){
                mappingsMap.put(s,m.getName());
            }
        }
    }

    public String getClassName(String pattern){
        String name = mappingsMap.get(pattern);
        return entitiesMap.get(name);
    }
}
