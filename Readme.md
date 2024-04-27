# Demo

## About

The demo project is a spring boot application that manages a mobile software testing inventory. It is equipped with an in-memory H2 database and Swagger for API documentation. The systems manage a collection of mobile phones, tracking their booking status & details.

## Installation

You will need Java SDK version 17 installed on your system. As the project uses an in-memory H2 database, no further database configuration is needed.

Clone the repository and import it into your favorite Java IDE, such as IntelliJ IDEA.

## Running the application

To run the application, execute the main application class `DemoApplication.java` within your IDE.

## API Endpoints

Below are the available endpoints:

- `GET /devices`: Fetches a list of all devices.
- `GET /users`: Fetches a list of all users.
- `GET /bookings/user/{userId}`: Fetches bookings made by a specified user.
- `GET /bookings/{bookingId}`: Fetches a booking by booking id.
- `GET /bookings/device/{deviceId}`: Fetches a booking by device id.
- `POST /bookings/book`: Books a device for a user.
- `PUT /bookings/return`: Returns a booked device.

For more detailed information, as well as the input and output formats for these endpoints, please refer to the Swagger API documentation.

## Logic/Functionality

The application provides API endpoints to manage mobile devices for testing purposes. It supports operations like booking and returning devices, checking which user currently has the device, and when it was booked.

Operations also include checking device availability, and fetching further details about the device, such as Technology, 2g bands, 3g bands, and 4g bands, powered by Fonoapi.

Please note, Fonoapi is not available, so the data was manually added and work around was used.
