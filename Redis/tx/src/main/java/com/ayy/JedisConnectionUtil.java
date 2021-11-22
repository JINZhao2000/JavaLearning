package com.ayy;

import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * @author Zhao JIN
 */

public class JedisConnectionUtil {
    private static final ThreadLocal<Jedis> TH_JEDIS = new ThreadLocal<>();

    public static Jedis getConecction() throws IOException {
        Jedis jedis;
        if (TH_JEDIS.get() == null) {
            jedis = connect();
            TH_JEDIS.set(jedis);
        }
        jedis = TH_JEDIS.get();
        if ("PONG".equals(jedis.ping())) {
            return jedis;
        }
        jedis = connect();
        TH_JEDIS.set(jedis);
        return jedis;
    }

    private static Jedis connect() throws IOException {
        Jedis jedis = new Jedis("192.168.68.10", 6379);
        if ("PONG".equals(jedis.ping())) {
            return jedis;
        }
        throw new IOException("Connection Failed");
    }

    public static void close() {
        if (TH_JEDIS.get() == null) {
            return;
        }
        TH_JEDIS.get().close();
        TH_JEDIS.remove();
    }
}
