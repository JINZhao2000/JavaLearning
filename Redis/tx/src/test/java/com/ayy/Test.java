package com.ayy;

import org.junit.Assert;
import redis.clients.jedis.Jedis;

/**
 * @author Zhao JIN
 */

public class Test {
    @org.junit.Test
    public void testLua() throws Exception{
        Jedis conecction = JedisConnectionUtil.getConecction();
        String script = conecction.scriptLoad(Stimulator.LUA_SCRIPT);
        Object result = conecction.evalsha(script, 2, "user1", "productId");
        Assert.assertEquals("1", String.valueOf(result));
    }
}
