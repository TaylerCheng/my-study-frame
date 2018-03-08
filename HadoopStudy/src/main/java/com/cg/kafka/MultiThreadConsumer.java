package com.cg.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * kafka多线程消费
 *
 * @author： Cheng Guang
 * @date： 2017/12/28.
 */
public class MultiThreadConsumer implements Runnable {


   private KafkaStream<String, String> stream;

    private MultiThreadConsumer(KafkaStream<String, String> stream) {
        this.stream = stream;
    }

    @Override
    public void run() {
        System.out.println("当前线程" + Thread.currentThread());
        ConsumerIterator<String, String> it = stream.iterator();
        while (it.hasNext()) {
            System.out.println(Thread.currentThread() + "消费: " + it.next().message());
        }
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        // zookeeper 配置
        props.put("zookeeper.connect", "master.hadoop:2181");
        // 消费者所在组
        props.put("group.id", "testgroup-1");
        // zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        // 序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        ConsumerConfig config = new ConsumerConfig(props);
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(MyKafkaProducer.TOPIC, new Integer(4));

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap =
                consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
        List<KafkaStream<String, String>> kafkaStreams = consumerMap.get(MyKafkaProducer.TOPIC);
        System.out.println("流数量：" + kafkaStreams.size());

        new Thread(new MultiThreadConsumer(kafkaStreams.get(0))).start();
        new Thread(new MultiThreadConsumer(kafkaStreams.get(1))).start();
        new Thread(new MultiThreadConsumer(kafkaStreams.get(2))).start();
        new Thread(new MultiThreadConsumer(kafkaStreams.get(3))).start();


    }

}
