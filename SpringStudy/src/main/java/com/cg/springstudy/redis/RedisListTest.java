package com.cg.springstudy.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import javax.sound.midi.Soundbank;
import java.util.List;

/**
 *
 * @author： Cheng Guangimport java.util.Map;

 * @date： 2017/12/12.
 */
public class RedisListTest {

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
        jedis = new Jedis(DEFAULT_SERVER_HOST);
//                jedis.lpush("cclist","1","2");
//                jedis.rpush("cclist","3","4");
    }

    /**
     * LPUSH 命令可向list的左边（头部）添加一个新元素，而RPUSH命令可向list的右边（尾部）添加一个新元素。最后LRANGE 命令可从list中取出一定范围的元素:
      */
    @Test
    public void test001() {

        List<String> cclist = jedis.lrange("cclist", -3, -2);
        System.out.println(cclist);
    }

    /**
     * 还有一个重要的命令是pop,它从list中删除元素并同时返回删除的值。可以在左边或右边操作。
     */
    @Test
    public void test002(){
//        String rpop = jedis.rpop("cclist");
//        System.out.println(rpop);
//        List<String> cclist = jedis.lrange("cclist", 0, -1);
//        System.out.println(cclist);

        String lpop = jedis.lpop("cclist");
        System.out.println(lpop);
        List<String> cclist = jedis.lrange("cclist", 0, -1);
        System.out.println(cclist);
    }

    /**
     * 可以使用Redis来实现生产者和消费者模型，如使用LPUSH和RPOP来实现该功能。
     * 但会遇到这种情景：list是空，这时候消费者就需要轮询来获取数据，这样就会增加redis的访问压力、增加消费端的cpu时间，而很多访问都是无用的。
     * 为此redis提供了阻塞式访问 BRPOP 和 BLPOP 命令。 消费者可以在获取数据时指定如果数据不存在阻塞的时间，如果在时限内获得数据则立即返回，
     * 如果超时还没有数据则返回null, 0表示一直阻塞。
     * 同时redis还会为所有阻塞的消费者以先后顺序排队。
     * 如需了解详细信息请查看 RPOPLPUSH 和 BRPOPLPUSH。
     */
    @Test
    public void test003() {
        List<String> brpop = jedis.brpop(1, "cclist");
        System.out.println(brpop);
        //        List<String> cclist = jedis.lrange("cclist", 0, -1);
        //        System.out.println(cclist);
    }

    /**
     * 但是，我们不能对存在但类型错误的 key 做操作：   > set foo bar OK > lpush foo 1 2 3 (error) WRONGTYPE Operation against a key holding the wrong kind of value > type foo string
     规则 2 示例:
     */
    @Test
    public void test004() throws InterruptedException {
        jedis.lpush("cclist","1","2");
        Thread.sleep(5*1000);
//        String lpop = jedis.lpop("cclist");
//         lpop = jedis.lpop("cclist");
        jedis.set("cclist", "123");
    }

    @Test
    public void test005(){

    }

}
