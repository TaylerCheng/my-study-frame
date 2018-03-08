package com.cg.springstudy.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;

/**
 *
 * @author： Cheng Guangimport java.util.Map;

 * @date： 2017/12/12.
 */
public class RedisStringTest {

    /** Default queue server host */
    final static String DEFAULT_SERVER_HOST = "192.168.255.100";
    /** Default queue server port */
    final static int DEFAULT_SERVER_PORT = 6379;
    /** Default queue stored database index */
    final static int DEFAULT_DB_INDEX = 2;
    /** Default bucket size */
    final static int DEFAULT_BUCKET_SIZE = 20;
    /** Redis default config value */
    final static int DEFAULT_CONNECT_TIMEOUT = 5000;

    private Jedis jedis;

    @Before
    public void init() {
        /**
         * 一、最简单的方式
         */
        jedis = new Jedis(DEFAULT_SERVER_HOST);
        jedis.set("cheng", "26");

        /**
         * 二、连接池的方式
         */
//        JedisPoolConfig config = new JedisPoolConfig();
//        JedisPool pool = new JedisPool(config, DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT, DEFAULT_CONNECT_TIMEOUT, null,
//                1);
//        jedis = pool.getResource();
    }


    /**
     * 递增
     *
     * INCR 命令将字符串值解析成整型，将其加一，最后将结果保存为新的字符串值，类似的命令有INCRBY, DECR 和 DECRBY。实际上他们在内部就是同一个命令，只是看上去有点儿不同。
     * INCR是原子操作意味着什么呢？就是说即使多个客户端对同一个key发出INCR命令，也决不会导致竞争的情况。例如如下情况永远不可能发生：『客户端1和客户端2同时读出“10”，他们俩都对其加到11，然后将新值设置为11』。最终的值一定是12，read-increment-set操作完成时，其他客户端不会在同一时间执行任何命令。
     */
    @Test
    public void test001() {
        //原子递增
        jedis.incr("cheng");
        String age = jedis.get("cheng");
        System.out.println(age);
        //一定步长递增
        jedis.incrBy("cheng", 10);
        age = jedis.get("cheng");
        System.out.println(age);

    }

    /**
     * 对字符串，另一个的令人感兴趣的操作是GETSET命令，行如其名：他为key设置新值并且返回原值。这有什么用处呢？
     * 例如：你的系统每当有新用户访问时就用INCR命令操作一个Redis key。你希望每小时对这个信息收集一次。你就可以GETSET这个key并给其赋值0并读取原值。
     */
    @Test
    public void test002(){
        String oldValue = jedis.getSet("cheng", "0");
        System.out.println(oldValue);
        String age = jedis.get("cheng");
        System.out.println(age);
    }

    /**
     * 为减少等待时间，也可以一次存储或获取多个key对应的值，使用MSET和MGET命令:
     */
    @Test
    public void test003(){
        List<String> mget = jedis.mget("cheng", "foo");
        System.out.println(mget);
    }

    /**
     * 修改或查询键空间
     有些指令不是针对任何具体的类型定义的，而是用于和整个键空间交互的。因此，它们可被用于任何类型的键。
     使用EXISTS命令返回1或0标识给定key的值是否存在，使用DEL命令可以删除key对应的值，DEL命令返回1或0标识值是被删除(值存在)或者没被删除(key对应的值不存在)。
     */
    @Test
    public void test004(){
        if (jedis.exists("cheng")){
            Long cheng = jedis.del("cheng");
            System.out.println(cheng);
        }
        Long shao = jedis.del("shao");
        System.out.println(shao);
    }

    /**
     * TYPE命令可以返回key对应的值的存储类型：
     */
    @Test
    public void test005(){
        String type = jedis.type("cheng");
        System.out.println(type);
    }

    /**
     * Redis超时:数据在限定时间内存活
     * 1、在介绍复杂类型前我们先介绍一个与值类型无关的Redis特性:超时。你可以对key设置一个超时时间，当这个时间到达后会被删除。精度可以使用毫秒或秒。
     * 2、上面的例子使用了EXPIRE来设置超时时间(也可以再次调用这个命令来改变超时时间，使用PERSIST命令去除超时时间 )。我们也可以在创建值的时候设置超时时间:
     * 3、TTL命令用来查看key对应的值剩余存活时间。
     */
    @Test
    public void test006() throws InterruptedException {
        String key = "cheng";
        /**
         * 默认存活时间
         */
        System.out.println(jedis.ttl(key));
        /**
         *  设置过期时间
         */
        jedis.expire(key, 5);
        System.out.println(jedis.ttl(key));
        /**
         * 休眠一定时间后，查看过期时间
         */
        Thread.sleep(4 * 1000);
        System.out.println(jedis.ttl(key));
        /**
         * 重置过期时间
         */
        jedis.expire(key, 3);
        System.out.println(jedis.ttl(key));

        /**
         * 取消过期
         */
        jedis.persist(key);
        System.out.println(jedis.ttl(key));

        /**
         * 删除后
         */
        jedis.del(key);
        System.out.println(jedis.ttl(key));
    }

}
