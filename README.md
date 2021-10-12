
# Meeting Scheduler
> Frontend repo [github](https://github.com/tanveer-rajeev/Meeting-Scheduler-react)
<br/>

## Features

Scheduler APIs provides
* This service gives a solution of scheduling multiple meetings at different times without overlaping existing ongoing meetings for the internal office rooms. 
* A user/admin will be able to do CRUD operation and also book multiple schedules as well as create a new meeting room with start time and end time.
* The user will get a warning when the user's schedule ranges overlap with other meetings.

## Entity Relationship Diagram
![entity-diagram](https://user-images.githubusercontent.com/39630470/136958431-03116b5c-6b67-44ac-b840-b00f93c3d83a.PNG)

## How to run

. Prerequisite
* JDK 1.8
* Maven 4.0.0
. Build
```
mvn install
```
. Run 
```
mvn spring-boot:run
```

## Api Documantation 
* Post mappings
   * localhost:8080/api/rooms
      - Create a room endpoint. Here capacity defines the number of person can be a
      ```
      {
     "roomName":"Interview",
     "capacity":6,
     "startTime":"11:00 AM",
     "endTime":"3:00 PM"
     }
     ```
   * localhost:8080/api/booking/userName/tanveer/roomId/47
      - Schedule a meeting endpoint . This schedule will be done by checking some condition like start time and end time are in range/correct compare to room schedule.
     ```
     {
     "bookingDate": "2021-10-25",
     "startTime": "1:00 AM",
     "endTime": "1:30 PM"
     }
     
     ```
     > Warning response for overlaping time
     ```
     {
     "timestamp": "2021-10-12T13:16:34.451+00:00",
     "message": "Time slot already taken . Try another room" --> 11:00 AM to 12:30 PM",
     "details": "uri=/booking/userName/tanveer/roomId/4"
     }
     ```
     > Warning response for exceed time
     ```
     {
     "timestamp": "2021-10-12T13:16:34.451+00:00",
     "message": "Given start time or end time is not in the requested room time range 4:00 AM to 10:30 AM",
     "details": "uri=/booking/userName/tanveer/roomId/8"
     }
     ```
   * Get Mappings
     
      - localhost:8080/api/rooms/4 
        > This endpoint gives one spacific room by id(4) with booking information
     ```
     [
       {
        "id": 3,
        "roomName": "Team",
        "capacity": 5,
        "startTime": "9:00 AM",
        "endTime": "5:00 PM",
        "booking": [
            {
                "id": 30,
                "bookingDate": "2021-10-11",
                "startTime": "12:30 PM",
                "endTime": "2:30 PM"
            },
            {
                "id": 34,
                "bookingDate": "2021-10-12",
                "startTime": "1:00 PM",
                "endTime": "2:00 PM"
            }
      }
     ]
     ```
      - localhost:8080/api/booking/room/6
         > Booking room by id
     ```
     {
     "roomName": "Team",
     "capacity": 5,
     "startTime": "9:00 AM",
     "endTime": "5:00 PM"
     }
     ```
