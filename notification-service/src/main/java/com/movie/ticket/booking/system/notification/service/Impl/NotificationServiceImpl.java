package com.movie.ticket.booking.system.notification.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.ticket.booking.system.commons.constants.LoggerConstants;
import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import com.movie.ticket.booking.system.notification.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
   private  ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender sender;

   @Value("${spring.mail.username}")
    private String fromEmail;
   String text=null;

   public String sendMail(String msg,String[] toEmails) throws  MessagingException {
       MimeMessage message=sender.createMimeMessage();
       MimeMessageHelper helper=new MimeMessageHelper(message,true);
       helper.setFrom(fromEmail);
       helper.setCc(toEmails);
       helper.setSubject("movie ticket booking details");
       helper.setSentDate(new Date());
       helper.setText(msg);
       sender.send(message);
       return "mail sent";
   }
    @Override
    public void processNotification(BookingDTO bookingDTO,String[] toEmails) {
        log.info(LoggerConstants.ENTERED_SERVICE_MESSAGE.getValue(), "createPayment", this.getClass());
try{
    String bookingId= objectMapper.writeValueAsString(bookingDTO.getBookingId());
    String userId=objectMapper.writeValueAsString(bookingDTO.getUserId());
    String movieId=objectMapper.writeValueAsString(bookingDTO.getMovieId());
    String seatsBooked=objectMapper.writeValueAsString(bookingDTO.getSeatsBooked());
    String showDate=objectMapper.writeValueAsString(bookingDTO.getShowDate());
    String showTime=objectMapper.writeValueAsString(bookingDTO.getShowTime());
    String bookingStatus=objectMapper.writeValueAsString(bookingDTO.getBookingStatus());
    String bookingAmount=objectMapper.writeValueAsString(bookingDTO.getBookingAmount());

    text="Thanks for using bookmyshow"+"\n"+"\n"+"\n"+"\n"
            +"BookingId:"+bookingId+"\n"
            +"UserId:"+userId+"\n"
            +"movieId"+movieId+"\n"
            +"seatsBooked:"+seatsBooked+"\n"
            +"showDate:"+showDate+"\n"
            +"showTime:"+showTime+"\n"
            +"bookingStatus:"+bookingStatus+"\n"
            +"bookingAmount:"+bookingAmount;

    String result=sendMail(text,toEmails);
    System.out.println(result);
}
catch(JsonProcessingException | MessagingException jpe)
{
   log.error("  failed to sending movie ticket booking details to mail "+ jpe.getMessage());
}

    }
}
