package com.cg.springstudy.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author： Cheng Guangimport java.util.Map;

 * @date： 2017/12/12.
 */
public class RedisHashTest {

    /** Default queue server host */
    final static String DEFAULT_SERVER_HOST = "192.168.255.100";


    private Jedis jedis;

    @Before
    public void init() {
        jedis = new Jedis(DEFAULT_SERVER_HOST);
    }

    @Test
    public void test001() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "cheng");
        data.put("age", "26");
        jedis.hmset("people", data);

        //        List<String> hmget = jedis.hmget("people", "name");
        //        System.out.println(hmget);

        String name = jedis.hget("people", "name");
        System.out.println(name);
    }

    /**
     * Redis Set 是 String 的无序排列。
     * SADD 指令把新的元素添加到 set 中。对 set 也可做一些其他的操作，比如测试一个给定的元素是否存在，对不同 set 取交集，并集或差，等等。
     * 获取一个元素的命令是 SPOP，它很适合对特定问题建模。比如，要实现一个基于 web 的扑克游戏，你可能需要用 set 来表示一副牌
     */
    @Test
    public void test002(){
        jedis.sadd("cglist", "1", "2", "3", "4");
        for (int i = 0; i < 4; i++) {
            String spop = jedis.spop("cglist");
            System.out.println(spop);
        }


        //        Set<String> cglist = jedis.smembers("cglist");
//        System.out.println(cglist);
//        Boolean exists = jedis.sismember("cglist", "45");
//        System.out.println(exists);

    }

    @Test
    public void test(){
        jedis.sadd("cglist", "1", "2", "3", "4");
        jedis.sadd("cglist2", "4", "5", "6", "7");

        Long sunionstore = jedis.sunionstore("newlist", "cglist", "cglist2");
        System.out.println(sunionstore);
    }

}
