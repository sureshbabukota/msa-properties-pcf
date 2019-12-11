package com.onlineshopping.microservices.customerservice;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitSendConfiguration {
                
                @Value("${topic.exchange}")
                private String exchange;
                @Value("${queue.name}")
                private String queuename;
                
                private String routingkey = "customer.created";
                
                @Bean
                public Queue queue() {
                                return new Queue(queuename);
                }
                
                @Bean
                public TopicExchange exchange() {
                                return new TopicExchange(exchange);
                }
                
                @Bean
                public Binding declareBinding() {
                                return BindingBuilder.bind(queue()).to(exchange()).with(routingkey);
                }
                
                @Bean
                public Jackson2JsonMessageConverter producerMessageConverter() {
                                return new Jackson2JsonMessageConverter();
                }
                
                @Bean
                public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
                                RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
                                rabbitTemplate.setMessageConverter(producerMessageConverter());
                                return rabbitTemplate;
                }
                
                
                
}
