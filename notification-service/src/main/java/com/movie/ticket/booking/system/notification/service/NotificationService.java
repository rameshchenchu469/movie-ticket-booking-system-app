package com.movie.ticket.booking.system.notification.service;


import com.movie.ticket.booking.system.commons.dto.BookingDTO;

public interface NotificationService {
    public void processNotification(BookingDTO bookingDTO,String[] toEmails);

}
