package com.pollongz.bookingservice;

import org.springframework.data.mongodb.repository.MongoRepository;

interface BookingRepository extends MongoRepository<Booking, String> {
}
