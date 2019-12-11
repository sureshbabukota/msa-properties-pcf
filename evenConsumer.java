package com.onlineshopping.microservices.salesorderservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfiguration {
                
                private static final String LISTENER_METHOD = "receiveMessage";

                @Value("${queue.name}")
                private String queueName;

                @Value("${topic.exchange}")
                private String exchange;

                @Bean
                public Queue queue() {
                  return new Queue(queueName);
                }

                @Bean
                public TopicExchange exchange() {
                  return new TopicExchange(exchange);
                }

                @Bean
                Binding binding(Queue queue, TopicExchange exchange) {
                  return BindingBuilder.bind(queue).to(exchange).with("customer.*");
                }
                
                 @Bean
                    public Jackson2JsonMessageConverter jsonMessageConverter(){
                        return new Jackson2JsonMessageConverter();
                    }

                @Bean
                SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,MessageListenerAdapter listenerAdapter) {
                  SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
                  container.setConnectionFactory(connectionFactory);
                  container.setQueueNames(queueName);
                  container.setMessageListener(listenerAdapter);
                  return container;

                }

                @Bean

                MessageListenerAdapter listenerAdapter(QueueConsumer consumer) {

                  return new MessageListenerAdapter(consumer, LISTENER_METHOD);

                }


}  
