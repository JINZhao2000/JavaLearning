package com.ayy;

import com.sun.istack.internal.NotNull;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author Zhao JIN
 */

public class Stimulator implements Callable<Boolean> {
    private final String userId;
    private final String productId;
    private final CountDownLatch cdl;

    public static final String LUA_SCRIPT =
            "local userId = KEYS[1];\r\n" +
                    "local productId = KEYS[2];\r\n" +
                    "local stockKey = \"stock:\"..productId;\r\n" +
                    "local userKey = \"stock:\"..productId..\":user\";\r\n" +
                    "local exists = redis.call(\"sismember\", userKey, userId);\r\n" +
                    "if tonumber(exists) == 1 then\r\n" +
                    "return 2;\r\n" +
                    "end\r\n" +
                    "local num = redis.call(\"get\", stockKey);\r\n" +
                    "if tonumber(num) <= 0 then\r\n" +
                    "return 0\r\n" +
                    "else\r\n" +
                    "redis.call(\"decr\", stockKey);\r\n" +
                    "redis.call(\"sadd\", userKey, userId);\r\n" +
                    "end\r\n " +
                    "return 1;";

    public Stimulator (@NotNull String userId, @NotNull String productId, @NotNull CountDownLatch cdl) {
        this.productId = productId;
        this.userId = userId;
        this.cdl = cdl;
    }

    // 2114
/*
    @Override
    public Boolean call() throws Exception {
        if (userId == null || productId == null) {
            cdl.countDown();
            return false;
        }
        Jedis jedis = JedisConnectionUtil.getConecction();
        String stockKey = "stock:"+productId;
        String userKey = "stock:"+productId+":user";
        String stock = jedis.get(stockKey);
        if (stock == null) {
            JedisConnectionUtil.close();
            cdl.countDown();
            return false;
        }
        if (jedis.sismember(userKey, userId)) {
            JedisConnectionUtil.close();
            cdl.countDown();
            return false;
        }
        synchronized (cdl) {
            stock = jedis.get(stockKey);
            if (Integer.parseInt(stock) <= 0) {
                JedisConnectionUtil.close();
                cdl.countDown();
                return false;
            }
            jedis.decr(stockKey);
            jedis.sadd(userKey, userId);
        }
        boolean result = jedis.sismember(userKey, userId);
        JedisConnectionUtil.close();
        cdl.countDown();
        return result;
    }
 */

    // 2188
/*
    @Override
    public Boolean call() throws Exception {
        if (userId == null || productId == null) {
            cdl.countDown();
            return false;
        }
        Jedis jedis = JedisConnectionUtil.getConecction();
        String stockKey = "stock:"+productId;
        String userKey = "stock:"+productId+":user";
        String stock = jedis.get(stockKey);
        if (stock == null) {
            JedisConnectionUtil.close();
            cdl.countDown();
            return false;
        }
        if (jedis.sismember(userKey, userId)) {
            JedisConnectionUtil.close();
            cdl.countDown();
            return false;
        }
        synchronized (cdl) {
            stock = jedis.get(stockKey);
            if (Integer.parseInt(stock) <= 0) {
                JedisConnectionUtil.close();
                cdl.countDown();
                return false;
            }
            Transaction tx = jedis.multi();
            tx.decr(stockKey);
            tx.sadd(userKey, userId);
            tx.exec();
        }
        boolean result = jedis.sismember(userKey, userId);
        JedisConnectionUtil.close();
        cdl.countDown();
        return result;
    }
*/

    // 2074
    @Override
    public Boolean call() throws Exception {
        Object res;
        try {
            if (userId == null || productId == null) {
                return false;
            }
            Jedis jedis = JedisConnectionUtil.getConecction();
            String stockKey = "stock:"+productId;
            String userKey = "stock:"+productId+":user";
            String stock = jedis.get(stockKey);
            if (stock == null) {
                JedisConnectionUtil.close();
                return false;
            }
            String sha1 = jedis.scriptLoad(Stimulator.LUA_SCRIPT);
            res = jedis.evalsha(sha1, 2, userId, productId);
            JedisConnectionUtil.close();
        } finally {
            cdl.countDown();
        }
        return "1".equals(String.valueOf(res));
    }
}
