package com.ayy.springcloud.controller;

import com.ayy.springcloud.bean.Dept;
import com.ayy.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/04/2021
 * @ Version 1.0
 */
@RestController
public class DeptController {
    private DeptService deptService;
    private DiscoveryClient discoveryClient;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }
    @PostMapping("/dept/add")
    public boolean addDept(Dept dept){
        return deptService.addDept(dept);
    }

    @HystrixCommand(fallbackMethod = "hystrixGet")
    @GetMapping("/dept/get/{nDept}")
    public Dept get(@PathVariable("nDept") int nDept){
        Dept dept = deptService.queryDeptById(nDept);
        if(dept == null){
            throw new RuntimeException("nDept invalid");
        }
        return dept;
    }

    public Dept hystrixGet(@PathVariable("nDept") int nDept){
        return new Dept().setNDept(nDept).setNameDept("Not exist --By Hystrix").setDbSource("DB not exist --By Hystrix");
    }

    @GetMapping("/dept/list")
    public List<Dept> queryAll(){
        return deptService.queryAll();
    }

    @GetMapping("/dept/discovery")
    public DiscoveryClient discoveryClient(){
        List<String> services = discoveryClient.getServices();
        System.out.println("Services : "+services);

        List<ServiceInstance> instances = discoveryClient.getInstances("springcloud-provider-dept");
        for (ServiceInstance instance : instances) {
            System.out.println(
                      instance.getHost() + "\t"
                    + instance.getPort() + "\t"
                    + instance.getUri() + "\t"
                    + instance.getServiceId()
            );
        }
        return discoveryClient;
    }
}
