package com.onlineshopping.microservices.customerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EventProducer {
                
                private Logger log = LoggerFactory.getLogger(CustomerController.class);
                
                private final RabbitTemplate rabbitTemplate;

                @Value("${topic.exchange}")
                private String exchange;
                
                @Autowired
                public EventProducer(RabbitTemplate rabbitTemplate) {
                                  super();

                                  this.rabbitTemplate = rabbitTemplate;
                }
                
                public void produce(Customer customer) throws Exception {

                                  log.info("Storing notification...");

                                  rabbitTemplate.setExchange(exchange);

                                  rabbitTemplate.convertAndSend(customer);

                                  log.info("Notification stored in queue sucessfully");
                                  log.info(customer.getCustId()+" "+ customer.getEmailId()+" "+customer.getFirstName()+" "+customer.getLastName());

                                }
                
                

}
