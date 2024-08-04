package cn.itcast.mq.helloworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AmqpPublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimpleQueue(){
        rabbitTemplate.convertAndSend("simple.queue", "Hello, I am 5dsf4gws!!!");
    }

    @Test
    /**
     * 给WorkQueue发送消息
     */
    public void testWorkQueue() throws InterruptedException {
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend("simple.queue", "loop ： " + i);
            Thread.sleep(20);
        }
    }

    @Test
    /**
     * 给FanoutQueue的Exchange发送消息
     */
    public void testFanoutQueue() throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            // 发送消息到FanoutExchange，第二个参数routingKey无需设置
            rabbitTemplate.convertAndSend("itcast.fanout", "NOT FOUND", "loop ： " + i);
        }

        // 直接越过Exchange发送消息给指定的fanoutQueue队列，从控制台结果发现也是可以监听到的
        rabbitTemplate.convertAndSend("fanout.queue2", "不通过Exchange,直接发送消息给fanout.queue1");
        rabbitTemplate.convertAndSend("fanout.queue2", "不通过Exchange,直接发送消息给fanout.queue2");
    }

    @Test
    /**
     * 给DirectQueue的Exchange发送消息
     */
    public void testDirectQueue(){
        rabbitTemplate.convertAndSend("itcast.direct", "A", "发给A的消息");
        rabbitTemplate.convertAndSend("itcast.direct", "B", "发给B的消息");
        rabbitTemplate.convertAndSend("itcast.direct", "C", "发给C的消息");
        // key未指定，则不会发送到Queue
        rabbitTemplate.convertAndSend("itcast.direct", "", "发给null的消息");
    }

    @Test
    /**
     * 给TopicQueue的Exchange发送消息
     */
    public void testTopicQueue(){
        rabbitTemplate.convertAndSend("itcast.topic", "China.news", "China.news China.news China.news");
        rabbitTemplate.convertAndSend("itcast.topic", "China.weather", "China.weather China.weather China.weather");
        rabbitTemplate.convertAndSend("itcast.topic", "China.nature", "China.nature China.nature China.nature");
        rabbitTemplate.convertAndSend("itcast.topic", "American.news", "American.news American.news American.news");
        rabbitTemplate.convertAndSend("itcast.topic", "Russia.news", "Russia.news Russia.news Russia.news");
    }

    @Test
    public void testSendObjectToQueue(){
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "樊振东");
        map.put(1, "陈梦");
        map.put(2, "潘展乐");
        rabbitTemplate.convertAndSend("object.queue", map);
    }
}
