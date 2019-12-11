package com.onlineshopping.microservices.salesorderservice;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableRabbit
@Component
public class QueueConsumer {
                
                private Logger log = LoggerFactory.getLogger(QueueConsumer.class);
                
                                @Autowired
                                CustomerSOSRepository customerSOSRepository;
                
//                            @Override
//                            @RabbitListener(queues="${queue.name}")
//                            public void onMessage(Message message) {
//                              log.info("Received (String) " + message.getBody());
//                            
//                                            processMessage(message);
//                            
//                            }
                                @RabbitListener(queues="${queue.name}")
                                public void receiveMessage(byte[] message) {
                                  String strMessage = new String(message);
                                  log.info("Received (No String) " + strMessage);
                                  processMessage(strMessage);
                                }
                                
                                private void processMessage(String message)  {
                                  try {
                                   CustomerSOS cust = new ObjectMapper().readValue(message, CustomerSOS.class);
                                   
//                               CustomerSOS cust1 = new ObjectMapper().convertValue(message, CustomerSOS.class);
                                   
                                   customerSOSRepository.save(cust);
                                  
                                  } catch (JsonParseException e) {
                                   log.warn("Bad JSON in message: " + message);
                                  } catch (JsonMappingException e) {
                                   log.warn("cannot map JSON to NotificationRequest: " + message);
                                  } catch (Exception e) {
                                   log.error(e.getMessage());
                                  }

                                }

}

Regards,
Pradeeppa T

