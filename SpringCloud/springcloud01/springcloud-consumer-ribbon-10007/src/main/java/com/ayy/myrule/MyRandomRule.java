package com.ayy.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/04/2021
 * @ Version 1.0
 */

public class MyRandomRule extends AbstractLoadBalancerRule {
    private int total = 0;
    private int current = 0;

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                return null;
            }

            if(total < 4){
                server = upList.get(current);
                total++;
            }else {
                total = 0;
                current++;
                if(current >= upList.size()){
                    current -= upList.size();
                }
                server = upList.get(current);
            }

            if (server == null) {
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            server = null;
            Thread.yield();
        }

        return server;

    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }
}
