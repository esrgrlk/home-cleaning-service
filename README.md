# Justlife Home Cleaning Service Case Study

- There are hardworking cleaning professionals
  - Can serve for multiple appointments same day based on their schedule
  - Must have a break at least 30 minutes between appointments one after
    another
  - Not working on Fridays
  - With working hours as 08.00-22:00
    - First appointment can not be started before 08:00
    - The last appointment must be finished before 22:00
- There are multiple vehicles with drivers ready to drop off and pick up cleaning professionals for
  appointments
  - A vehicle has a list of cleaning professionals and only serve the whole day
  - Each vehicle have 5 assigned cleaner professionals
- Customers want to make a reservation for a cleaning service with the following
  attributes:
  - Start date and time which they want that service starts
  - duration for that service ( can be 2 or 4 hours long )
  - cleaning professional count
    - can be 1, 2 or 3 professionals for the appointment
    - only professionals assigned to the same vehicle work together for a single appointment
      
The booking process should be created as below:
- Availability check:
  - If the only date provided, this service should return available cleaner professional list
    their available times for the given date
  - If the date, appointment start time, and appointment duration is given, this service
    should return only available cleaner professional list for the given time period at the given date
    
- Booking creation:
  - Each booking should be assigned to one or multiple cleaner professionals (1, 2 or 3).
  - If multiple cleaners must be assigned they should belong to the same vehicle.
  - After a booking is created, the cleaner professionals' available times will be updated.
- Booking update:
  - The date and time of booking should be able to be updated.
  - After a booking is updated, the cleaner professionals' available times will be updated.

# Technology Stack
- Java
- Spring Boot
- Maven
- MySQL
- Docker
- JUnit

# How to run the project

You need to have Docker installed on your machine.


    git clone https://github.com/esrgrlk/home-cleaning-service.git

    docker compose up

Docker will build an image of the application at the first run.
If you change something after the first run, you need to add `--build` flag to tell Docker to rebuild the image:

    docker compose up --build