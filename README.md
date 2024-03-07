Health partner - version 1.0.0 - MVP


5 services:

1. appointment-service
2. patient-service
3. booking-service
4. api-gateway
5. discovery-server


- Booking service sends a call to appointment-service and patient-service by WebClient methods

- All services are registered in eureka discovery server on port 8761

- Every call is done trough api gateway on port 8080

- Appointment service is notifying clients about new appointment dates


Technologies used so far:

- Webflux
- Eureka
- Cloud gateway
- Kafka