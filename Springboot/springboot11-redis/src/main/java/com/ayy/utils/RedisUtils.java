package com.ayy.utils;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/04/2021
 * @ Version 1.0
 */
@Component
public class RedisUtils {
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean expire(String key, long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public long getExpire(String key){
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void del(String... keys){
        if(keys!=null && keys.length>0){
            if (keys.length==1){
                redisTemplate.delete(keys[0]);
            }else{
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(keys));
            }
        }
    }

    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean set(@NotNull String key, Object value){
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(@NotNull String key, Object value, long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else {
                set(key, value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public long incr(String key, long delta){
        if (delta<0){
            throw new IllegalArgumentException("Required Delta > 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public long decr(String key, long delta){
        if (delta<0){
            throw new IllegalArgumentException("Required Delta > 0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    public Object hget(String key, String item){
        return redisTemplate.opsForHash().get(key, item);
    }

    public Map<Object, Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public boolean hmset(String key, Map<String, Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean hmset(String key, Map<String, Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean hset(String key, String item, Object value){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean hset(String key, String item, Object value, long time){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    public double hincr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    public double hdecr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    public Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean sHasKey(String key, Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public long sSet(String key, Object... values){
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public long sSetAndTime(String key, long time, Object... valus){
        try {
            Long count = redisTemplate.opsForSet().add(key, valus);
            if(time>0){
                expire(key, time);
            }
            return count;
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public long setRemove(String key, Object... values){
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<Object> lGet(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long lGetListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public Object lGetIndex(String key, long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean lSet(String key, Object value){
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean lSet(String key, Object value, long time){
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean lSet(String key, List<Object> value){
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean lSet(String key, List<Object> value, long time){
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean lUpdateIndex(String key, long index, Object value){
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public long lRemove(String key, long count, Object value){
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
