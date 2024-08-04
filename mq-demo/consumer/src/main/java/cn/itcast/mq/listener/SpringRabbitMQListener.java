package cn.itcast.mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SpringRabbitMQListener {

//    @RabbitListener(queues = "simple.queue")
//    public void listen(String message){
//        System.out.println("Received message： " + message);
//    }

    /**
     * 监听队列simple.queue
     *
     * @param message
     * @throws InterruptedException
     */
    @RabbitListener(queues = "simple.queue")
    public void workQueueListener1(String message) throws InterruptedException {
        System.out.println("workQueueListener1接受到消息： " + message);
        Thread.sleep(20);
    }

    /**
     * 同上一块监听队列simple.queue
     *
     * @param message
     * @throws InterruptedException
     */
    @RabbitListener(queues = "simple.queue")
    public void workQueueListener2(String message) throws InterruptedException {
        System.out.println("--------------------workQueueListener2接受到消息： " + message);
        Thread.sleep(100);
    }

    /**
     * 监听队列fanout.queue1
     *
     * @param message
     */
    @RabbitListener(queues = "fanout.queue1")
    public void fanoutQueueListener1(String message) {
        System.out.println("fanoutQueueListener11111： " + message);
    }

    /**
     * 监听队列fanout.queue2
     *
     * @param message
     */
    @RabbitListener(queues = "fanout.queue2")
    public void fanoutQueueListener2(String message) {
        System.out.println("fanoutQueueListener22222： " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("direct.queue1"),
            exchange = @Exchange(value = "itcast.direct", type = ExchangeTypes.DIRECT), // type = ExchangeTypes.DIRECT可以不写，默认类型就是direct
            key = {"A", "C"}
    ))
    public void directQueueListener1(String message) {
        System.out.println("directQueueListener11111: " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("direct.queue2"),
            exchange = @Exchange(value = "itcast.direct", type = ExchangeTypes.DIRECT), // type = ExchangeTypes.DIRECT可以不写，默认类型就是direct
            key = {"B", "C"}
    ))
    public void directQueueListener2(String message) {
        System.out.println("directQueueListener22222: " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "itcast.topic", type = ExchangeTypes.TOPIC),
            key = {"China.#"}
    ))
    public void topicQueueListener1(String message) {
        System.out.println("topicQueueListener11111: " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "itcast.topic", type = ExchangeTypes.TOPIC),
            key = {"#.news"}
    ))
    public void topicQueueListener2(String message) {
        System.out.println("topicQueueListener22222: " + message);
    }

    @RabbitListener(queues = "object.queue")
    public void receiveObjectListener(HashMap<Integer, String> map){
        map.forEach((key, value) -> {
            System.out.println("key = " + key + ", value = " + value);
        });
    }
}