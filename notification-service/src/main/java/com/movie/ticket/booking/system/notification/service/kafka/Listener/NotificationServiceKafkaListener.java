package com.movie.ticket.booking.system.notification.service.kafka.Listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import com.movie.ticket.booking.system.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceKafkaListener {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener (topics ="payment-response", groupId="paymentResponse-notification")
    public void receiveFromPaymentResponseTopic(String bookingDTOJson)
    {
        log.info("received conformation on booking details from payment-response kafka topic to notificationService");
        try{
            BookingDTO bookingDTO=objectMapper.readValue(bookingDTOJson, BookingDTO.class);
            this.notificationService.processNotification(bookingDTO,new String[]{"rameshchenchu469@gmail.com","rameshchenchu25@gmail.com"});
        }
        catch(JsonProcessingException e){
            log.error(" error while receiving ticket conformation details from payment response topic");
        }
    }


}