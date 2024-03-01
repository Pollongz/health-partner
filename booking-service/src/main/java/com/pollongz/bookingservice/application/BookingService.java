package com.pollongz.bookingservice.application;

import com.pollongz.bookingservice.model.Booking;
import com.pollongz.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final WebClient.Builder webClientBuilder;
    private final BookingRepository bookingRepository;

    Logger LOG = LoggerFactory.getLogger(BookingService.class);

    public void bookAppointment(Booking booking) {
        Mono<Boolean> isPatientBlockedMono =
                isUserBlocked(booking.getPatientId());
        Mono<Boolean> isAppointmentAvailableMono =
                isAppointmentAvailable(booking.getAppointmentId());

        Mono.zip(isPatientBlockedMono, isAppointmentAvailableMono)
            .subscribe(response -> {
                boolean isPatientBlocked = response.getT1();
                boolean isAppointmentAvailable = response.getT2();

                if (isPatientBlocked) {
                    LOG.error("This patient is not allowed to book an appointment");
                } else if (!isAppointmentAvailable) { //ugly
                    LOG.error("The appointment is not available. Try again later.");
                } else {
                    finalizeBookingAppointment(booking);
                }
            }, error -> LOG.error("Error in booking appointment: " + error.getMessage()));
    }

    private void finalizeBookingAppointment(Booking booking) {
        makeAppointmentUnavailable(booking);
        Booking bookingToSave = new Booking();
        bookingToSave.setPatientId(booking.getPatientId());
        bookingToSave.setAppointmentId(booking.getAppointmentId());
        bookingToSave.setTimestamp(LocalDateTime.now());

        bookingRepository.save(bookingToSave);
        LOG.info("Appointment is booked: {}", bookingToSave);
    }

    private void makeAppointmentUnavailable(Booking booking) {
        webClientBuilder.build().post()
        .uri("lb://appointment-service/api/v1/appointments/" + booking.getAppointmentId() + "/block-appointment")
        .retrieve()
        .bodyToMono(Void.class)
        .doOnError(error -> LOG.error("Error in appointment service: " + error.getMessage()))
        .subscribe();
    }


    private Mono<Boolean> isAppointmentAvailable(long booking) {
        return webClientBuilder.build().get()
                .uri("lb://appointment-service/api/v1/appointments/" + booking + "/check-status")
                .retrieve()
                .bodyToMono(Boolean.class)
                .doOnError(error -> LOG.error("Error in appointment service: " + error.getMessage()));
    }

    private Mono<Boolean> isUserBlocked(long booking) {
        return webClientBuilder.build().get()
                .uri("lb://patient-service/api/v1/patients/" + booking + "/check-status")
                .retrieve()
                .bodyToMono(Boolean.class)
                .doOnError(error -> LOG.error("Error in patient service: " + error.getMessage()));
    }
}
