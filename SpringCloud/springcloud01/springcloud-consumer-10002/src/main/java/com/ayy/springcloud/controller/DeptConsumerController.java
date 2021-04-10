package com.ayy.springcloud.controller;

import com.ayy.springcloud.bean.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/04/2021
 * @ Version 1.0
 */
@RestController
public class DeptConsumerController {
    private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX = "http://localhost:10001";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/consumer/dept/get/{nDept}")
    public Dept get(@PathVariable("nDept") int nDept){
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/get/"+nDept, Dept.class);
    }

    @RequestMapping("/consumer/dept/add")
    public boolean add(Dept dept){
        Boolean res = restTemplate.postForObject(REST_URL_PREFIX+"/dept/add/",dept,Boolean.class);
        return null != res && res;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/consumer/dept/list")
    public List<Dept> list(){
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/list",List.class);
    }
}
