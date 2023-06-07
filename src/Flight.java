import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Flight extends FlightDistance {

    private final String flightSchedule;
    private final String flightNumber;
    private final String fromWhichCity;
    private final String gate;
    private final String toWhichCity;
    private double distanceInMiles;
    private double distanceInKm;
    private String flightTime;
    private int numOfSeatsInTheFlight;
    private List<Customer> listOfRegisteredCustomersInAFlight;
    private int customerIndex;
    private static int nextFlightDay = 0;
    private static final List<Flight> flightList = new ArrayList<>();

    Flight() {
        this.flightSchedule = null;
        this.flightNumber = null;
        this.numOfSeatsInTheFlight = 0;
        toWhichCity = null;
        fromWhichCity = null;
        this.gate = null;
    }

    // creates a new random flight
    Flight(String flightSchedule, String flightNumber, int numOfSeatsInTheFlight, String[][] chosenDestinations, String[] distanceBetweenTheCities, String gate) {
        this.flightSchedule = flightSchedule; // departure date and time
        this.flightNumber = flightNumber; // unique id for each flight
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight; // available seats
        this.fromWhichCity = chosenDestinations[0][0]; // origin cities
        this.toWhichCity = chosenDestinations[1][0]; // destination cities
        this.distanceInMiles = Double.parseDouble(distanceBetweenTheCities[0]); // gives distance in miles
        this.distanceInKm = Double.parseDouble(distanceBetweenTheCities[1]); // gives distance in km
        this.flightTime = calculateFlightTime(distanceInMiles);
        this.listOfRegisteredCustomersInAFlight = new ArrayList<>();
        this.gate = gate; // where Passengers will board plane
    }


    // creates flight schedule
    public void flightScheduler() {
        int numOfFlights = 15; // decides how many unique flights to be included/display in scheduler
        RandomGenerator r1 = new RandomGenerator();
        for (int i = 0; i < numOfFlights; i++) { // loop for 15 times
            String[][] chosenDestinations = r1.randomDestinations();
            // calculate distance using the latitude and longitude values of the chosen destinations
            String[] distanceBetweenTheCities = calculateDistance(Double.parseDouble(chosenDestinations[0][1]), Double.parseDouble(chosenDestinations[0][2]), Double.parseDouble(chosenDestinations[1][1]), Double.parseDouble(chosenDestinations[1][2]));
            String flightSchedule = createNewFlightsAndTime(); // generates new flight and the time
            String flightNumber = r1.randomFlightNumbGen(2, 1).toUpperCase();
            int numOfSeatsInTheFlight = r1.randomNumOfSeats(); // generate random number of seats
            String gate = r1.randomFlightNumbGen(1, 30); // generates gate with parameters being the length of gate number and maximum gate number is 30
            // a flight object is created with all those details, gate is converted to uppercase
            flightList.add(new Flight(flightSchedule, flightNumber, numOfSeatsInTheFlight, chosenDestinations, distanceBetweenTheCities, gate.toUpperCase()));
        }
    }

    // register new Passenger in flight
    void addNewCustomerToFlight(Customer customer) {
        this.listOfRegisteredCustomersInAFlight.add(customer);
    }

    // adds number of tickets to existing customers tickets for the flight
    void addTicketsToExistingCustomer(Customer customer, int numOfTickets) {
        customer.addExistingFlightToCustomerList(customerIndex, numOfTickets);
    }


    // check if Passenger is registered in the flight's array list
    boolean isCustomerAlreadyAdded(List<Customer> customersList, Customer customer) { // customerList of flight, specified customer to be checked
        boolean isAdded = false;
        for (Customer customer1 : customersList) {
            if (customer1.getUserID().equals(customer.getUserID())) {
                isAdded = true; // return true if registered
                customerIndex = customersList.indexOf(customer1);
                break;
            }
        }
        return isAdded;
    }


    // calculate flight time using average ground speed of 450 knots
    public String calculateFlightTime(double distanceBetweenTheCities) { // distance between cities in miles
        double groundSpeed = 450;
        double time = (distanceBetweenTheCities / groundSpeed);
        String timeInString = String.format("%.4s", time); // formats the time as a string with four characters
        String[] timeArray = timeInString.replace('.', ':').split(":"); // replace decimal point with a colon, and the colon is used to divide the string
        int hours = Integer.parseInt(timeArray[0]); // array[0] contains the hours
        int minutes = Integer.parseInt(timeArray[1]); // array[1] contains the minutes
        int modulus = minutes % 5;
        // Changing flight time to make minutes near/divisible to 5.
        if (modulus < 3) {
            minutes -= modulus;
        } else {
            minutes += 5 - modulus;
        }
        if (minutes >= 60) { // if minutes exceed 60, then reduce it by 60 and add the hour by 1
            minutes -= 60;
            hours++;
        }
        if (hours <= 9 && Integer.toString(minutes).length() == 1) { // return formatted flight time
            return String.format("0%s:%s0", hours, minutes); // if hour less than 9 and minutes is single digit, time is formatted as 0H:0M
        } else if (hours <= 9 && Integer.toString(minutes).length() > 1) {
            return String.format("0%s:%s", hours, minutes); // time is formatted as 0H:MM
        } else if (hours > 9 && Integer.toString(minutes).length() == 1) {
            return String.format("%s:%s0", hours, minutes); // time is formatted as HH:0M
        } else {
            return String.format("%s:%s", hours, minutes); // time is formatted as HH:MM
        }
    }


    // Creates flight arrival time by adding flight time to departure time
    public String fetchArrivalTime() {
        // format the time as for example: Wednesday, 12 July 2023, 08:45 PM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a ");
        // convert the String of flightSchedule to LocalDateTime and add the arrivalTime to it
        LocalDateTime departureDateTime = LocalDateTime.parse(flightSchedule, formatter);

        // Getting the Flight Time, plane was in air
        String[] flightTime = getFlightTime().split(":");
        int hours = Integer.parseInt(flightTime[0]);
        int minutes = Integer.parseInt(flightTime[1]);


        LocalDateTime arrivalTime;

        // add departure time by the hours it takes for the flight for the arrival time
        arrivalTime = departureDateTime.plusHours(hours).plusMinutes(minutes);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EE, dd-MM-yyyy HH:mm a");
        return arrivalTime.format(formatter1); // return flight arrival time

    }

    // method to delete a flight
    void deleteFlight(String flightNumber) {
        boolean isFound = false;
        Iterator<Flight> list = flightList.iterator();
        while (list.hasNext()) { // make object flight for every item in list
            Flight flight = list.next();
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) { // if flight id inputted is in flight list, return true
                isFound = true;
                break;
            }
        }
        if (isFound) { // remove the flight from the list
            list.remove();
        } else {
            System.out.println("Flight with given Number not found...");
        }
        displayFlightSchedule();
    }

    // calculate distance between cities based on the latitudes and longitudes
    @Override
    public String[] calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        // equation to calculate the distance
        double distance = Math.sin(degreeToRadian(lat1)) * Math.sin(degreeToRadian(lat2)) + Math.cos(degreeToRadian(lat1)) * Math.cos(degreeToRadian(lat2)) * Math.cos(degreeToRadian(theta));
        distance = Math.acos(distance);
        distance = radianToDegree(distance);
        distance = distance * 60 * 1.1515;
        String[] distanceString = new String[3];
        distanceString[0] = String.format("%.2f", distance * 0.8684); // distance in miles
        distanceString[1] = String.format("%.2f", distance * 1.609344); // distance in km
        distanceString[2] = Double.toString(Math.round(distance * 100.0) / 100.0); // distance in knots
        return distanceString; // return distance both in miles and km between the cities/airports
    }

    private double degreeToRadian(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double radianToDegree(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void displayFlightSchedule() {

        Iterator<Flight> flightIterator = flightList.iterator();
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE\t\t\t\t\t\t   | FLIGHT NO | No. of Seats  | \tFROM ====>>       | \t====>> TO\t\t   | \t    ARRIVAL TIME       | FLIGHT TIME |  GATE  |   DISTANCE(MILES/KMS)  |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        int i = 0;
        while (flightIterator.hasNext()) {
            i++;
            Flight f1 = flightIterator.next();
            System.out.println(f1.toString(i));
             System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        }
    }

    @Override
    public String toString(int i) {
        return String.format("| %-5d| %-41s | %-9s | \t%-9s | %-21s | %-22s | %-10s  |   %-6sHrs |  %-4s  |  %-8s / %-11s|", i, flightSchedule, flightNumber, numOfSeatsInTheFlight, fromWhichCity, toWhichCity, fetchArrivalTime(), flightTime, gate, distanceInMiles, distanceInKm);
    }


    // creates new random flight schedule
    public String createNewFlightsAndTime() {

        Calendar c = Calendar.getInstance();
        // incrementing nextFlightDay, so that next scheduled flight would be in the future, not in the present
        nextFlightDay += Math.random() * 7;
        c.add(Calendar.DATE, nextFlightDay);
        c.add(Calendar.HOUR, nextFlightDay);
        c.set(Calendar.MINUTE, ((c.get(Calendar.MINUTE) * 3) - (int) (Math.random() * 45)));
        Date myDateObj = c.getTime();
        LocalDateTime date = Instant.ofEpochMilli(myDateObj.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        date = getNearestHourQuarter(date);
        return date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a "));
    }

    // Formats flight schedule, so that the minutes would be to the nearest quarter.
    public LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {
        int minutes = datetime.getMinute();
        int mod = minutes % 15;
        LocalDateTime newDatetime;
        if (mod < 8) {
            newDatetime = datetime.minusMinutes(mod); // subtract the datetime with the mod value to round it down to the prev quarter
        } else {
            newDatetime = datetime.plusMinutes(15 - mod); // add datetime with the result of 15 - mod to round it up to the next quarter
        }
        newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES); // only hour and minute components remain
        return newDatetime; // returns formatted LocalDateTime with minutes close to the nearest hour quarter
    }

    // Other Setters and Getters
    public int getNoOfSeats() {
        return numOfSeatsInTheFlight;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setNoOfSeatsInTheFlight(int numOfSeatsInTheFlight) {
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public List<Customer> getListOfRegisteredCustomersInAFlight() {
        return listOfRegisteredCustomersInAFlight;
    }

    public String getFlightSchedule() {
        return flightSchedule;
    }

    public String getFromWhichCity() {
        return fromWhichCity;
    }

    public String getGate() {
        return gate;
    }

    public String getToWhichCity() {
        return toWhichCity;
    }

}