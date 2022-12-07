package cn.itrip.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisUtil {
    //封装redis 写入方法
    public void setData(String key,String value){
        Jedis jedis = new Jedis("182.92.160.140");
        jedis.auth("root123*");
        //设置数据的过期时间
        jedis.setex(key,60*60*2,value);
    }

    //封装redis 读取数据方法
    public String getData(String key){
        Jedis jedis = new Jedis("182.92.160.140");
        jedis.auth("root123*");

        //设置数据的过期时间为2小时
        return jedis.get(key);
    }
}
