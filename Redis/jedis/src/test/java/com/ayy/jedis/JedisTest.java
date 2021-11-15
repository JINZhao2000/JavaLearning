package com.ayy.jedis;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @author Zhao JIN
 */

public class JedisTest {
    private Jedis jedis;

    @Test
    public void zsetTest() {
        jedis.zadd("k1", 1, "v1");
        jedis.zadd("k1", 3, "v4");
        jedis.zadd("k1", 3, "v3");
        jedis.zadd("k1", 2, "v2");
        Set<String> values = jedis.zrange("k1", 0, -1);
        Assert.assertEquals(4, values.size());
    }

    @Test
    public void hashTest() {
        jedis.hset("k1", "hk1", "hv1");
        jedis.hset("k1", "hk2", "hv2");
        jedis.hset("k1", "hk3", "hv3");
        jedis.hset("k1", "hk1", "hv4");
        // jedis hmget, hmset
        String value = jedis.hget("k1", "hk1");
        Assert.assertEquals("hv4", value);
    }

    @Test
    public void setTest() {
        jedis.sadd("k1", "v1", "v2", "v3", "v2");
        Set<String> values = jedis.smembers("k1");
        Assert.assertEquals(3, values.size());
    }

    @Test
    public void listTest(){
        jedis.rpush("k1", "v1", "v2", "v3");
        List<String> values = jedis.lrange("k1", 0, -1);
        Assert.assertEquals(3, values.size());
        Assert.assertEquals("v1", values.get(0));
        Assert.assertEquals("v2", values.get(1));
        Assert.assertEquals("v3", values.get(2));
    }

    @Test
    public void keyTest() {
        jedis.set("k1", "v1");
        jedis.mset("k2", "v2", "k3", "v3");
        Set<String> keys = jedis.keys("*");
        Assert.assertEquals(3, keys.size());
        Assert.assertEquals(true, jedis.exists("k1"));
        Assert.assertEquals("v1", jedis.get("k1"));
        Assert.assertEquals("v2", jedis.get("k2"));
        Assert.assertEquals("v3", jedis.get("k3"));
        jedis.expire("k1", 10L);
        Assert.assertEquals(Long.valueOf(10), jedis.ttl("k1"));
    }

    @Test
    public void pingTest() {
        String ping = jedis.ping();
        Assert.assertEquals("PONG", ping);
    }

    @Before
    public void setUp() {
        jedis = new Jedis("192.168.68.10", 6379);
    }

    @After
    public void tearDown() {
        jedis.flushDB();
		jedis.close();
        jedis = null;
    }
}
