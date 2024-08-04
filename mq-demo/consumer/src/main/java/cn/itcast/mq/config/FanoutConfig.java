package cn.itcast.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * 使用FanoutQueue实现订阅功能
 */
public class FanoutConfig {

    @Bean
    /**
     * 定义Exchange
     */
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("itcast.fanout");
    }

    @Bean
    /**
     * 定义队列1
     */
    public Queue getQueue1(){
        return new Queue("fanout.queue1");
    }

    @Bean
    /**
     * 绑定队列1
     */
    public Binding getBindingQueue1(FanoutExchange exchange, Queue getQueue1){
        return BindingBuilder.bind(getQueue1).to(exchange);
    }

    @Bean
    /**
     * 定义队列2
     */
    public Queue getQueue2(){
        return new Queue("fanout.queue2");
    }

    @Bean
    /**
     * 绑定队列2
     */
    public Binding getBindingQueue2(FanoutExchange exchange, Queue getQueue2){
        return BindingBuilder.bind(getQueue2).to(exchange);
    }
}
