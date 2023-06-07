
// Main class that calls all methods.

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    // array to store admin information
    static String[][] adminUserNameAndPassword = new String[10][2]; // max number of admins is 10
    private static List<Customer> customersCollection = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int countNumOfUsers = 1;
        RolesAndPermissions r1 = new RolesAndPermissions();
        Flight f1 = new Flight();
        FlightReservation bookingAndReserving = new FlightReservation();
        Customer c1 = new Customer();
        f1.flightScheduler();
        Scanner read = new Scanner(System.in);
        PassengerReader.createPassenger(); // read the txt file


        welcomeScreen(1);
        System.out.println("\n\t\t\t\t\t+++++++++++++ Welcome to Binus AirLines +++++++++++++\n");
        System.out.println("To Further Proceed, Please enter a value.");
        displayMainMenu();
        int desiredOption = read.nextInt();
        while (desiredOption < 0 || desiredOption > 8) {
            System.out.print("ERROR!! Please enter value between 0 - 4. Enter the value again : ");
            desiredOption = read.nextInt();
        }


        do {
            Scanner read1 = new Scanner(System.in);

            // Call the login method
            if (desiredOption == 1) {

                // Username and Password
                printArtWork(1);
                System.out.print("\nEnter the Username to login to the Management System :     ");
                String username = read1.nextLine();
                System.out.print("Enter the Password to login to the Management System :    ");
                String password = read1.nextLine();
                System.out.println();

                // Checking RolesAndPermissions class for admin info
                if (r1.isPrivilegedUserOrNot(username, password) == -1) { // Return -1 if info not found
                    System.out.printf("\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n", "");
                } else { // if found then display the possible options
                    System.out.printf("%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....", "", username);

                    // Give options for admin
                    do {
                        System.out.printf("\n\n%-60s+++++++++ 2nd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "", "", username);
                        System.out.printf("%-30s (a) Enter 1 to add new Passenger....\n", "");
                        System.out.printf("%-30s (b) Enter 2 to search a Passenger....\n", "");
                        System.out.printf("%-30s (c) Enter 3 to update the Data of the Passenger....\n", "");
                        System.out.printf("%-30s (d) Enter 4 to delete a Passenger....\n", "");
                        System.out.printf("%-30s (e) Enter 5 to Display all Passengers....\n", "");
                        System.out.printf("%-30s (f) Enter 6 to Display all flights registered by a Passenger...\n", "");
                        System.out.printf("%-30s (g) Enter 7 to Display all registered Passengers in a Flight....\n", "");
                        System.out.printf("%-30s (h) Enter 8 to Delete a Flight....\n", "");
                        System.out.printf("%-30s (i) Enter 0 to Go back to the Main Menu/Logout....\n", "");
                        System.out.print("Enter the desired Choice :   ");
                        desiredOption = read.nextInt();

                        if (desiredOption == 1) { // if choose 1, add passenger
                            c1.displayArtWork(1);
                            c1.addNewCustomer();
                        } else if (desiredOption == 2) { // if choose 2, call search method from Customer class
                            c1.displayArtWork(2);
                            c1.displayCustomersData(false);
                            System.out.print("Enter the CustomerID to Search : ");
                            String customerID = read1.nextLine();
                            System.out.println();
                            c1.searchUser(customerID);
                        } else if (desiredOption == 3) { // if choose 3, call update method from Customer class
                            bookingAndReserving.displayArtWork(2);
                            c1.displayCustomersData(false);
                            System.out.print("Enter the CustomerID to Update its Data : ");
                            String customerID = read1.nextLine();
                            if (customersCollection.size() > 0) {
                                c1.editUserInfo(customerID);
                            } else {
                                System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
                            }
                        } else if (desiredOption == 4) { // if choose 4, delete customer by taking their id
                            bookingAndReserving.displayArtWork(3);
                            c1.displayCustomersData(false);
                            System.out.print("Enter the CustomerID to Delete its Data : ");
                            String customerID = read1.nextLine();
                            if (customersCollection.size() > 0) {
                                c1.deleteUser(customerID);
                            } else {
                                System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
                            }
                        } else if (desiredOption == 5) { // if choose 5, Display availability of Passengers
                            c1.displayArtWork(3);
                            c1.displayCustomersData(false);
                        } else if (desiredOption == 6) { // if choose 6, display flights registered by Passengers
                            bookingAndReserving.displayArtWork(6);
                            c1.displayCustomersData(false);
                            System.out.print("\n\nEnter the ID of the user to display all flights registered by that user...");
                            String id = read1.nextLine();
                            bookingAndReserving.displayFlightsRegisteredByOneUser(id);
                        } else if (desiredOption == 7) { // if choose 7, display Passengers in a flight
                            c1.displayArtWork(4);
                            System.out.print("Do you want to display Passengers of all flights or a specific flight.... 'Y/y' for displaying all flights and 'N/n' to look for a" +
                                    " specific flight.... ");
                            char choice = read1.nextLine().charAt(0);
                            if ('y' == choice || 'Y' == choice) {
                                bookingAndReserving.displayRegisteredUsersForAllFlight();
                            } else if ('n' == choice || 'N' == choice) {
                                f1.displayFlightSchedule();
                                System.out.print("Enter the Flight Number to display the list of passengers registered in that flight... ");
                                String flightNum = read1.nextLine();
                                bookingAndReserving.displayRegisteredUsersForASpecificFlight(flightNum);
                            } else {
                                System.out.println("Invalid Choice...No Response...!");
                            }
                        } else if (desiredOption == 8) { // if choose 8, delete a flight
                            c1.displayArtWork(5);
                            f1.displayFlightSchedule();
                            System.out.print("Enter the Flight Number to delete the flight : ");
                            String flightNum = read1.nextLine();
                            f1.deleteFlight(flightNum);

                        } else if (desiredOption == 0) { // if choose 0, go back to main menu
                            bookingAndReserving.displayArtWork(22);
                            System.out.println("Thanks for Using Binus Airlines Ticketing System...!!!");

                        } else {
                            System.out.println("Invalid Choice. Try Logging in again");
                            bookingAndReserving.displayArtWork(22);
                            desiredOption = 0;
                        }

                    } while (desiredOption != 0);

                }
            } else if (desiredOption == 2) { // if option 2, register admin
                printArtWork(2);
                System.out.print("\nEnter the UserName to Register :    ");
                String username = read1.nextLine();
                System.out.print("Enter the Password to Register :     ");
                String password = read1.nextLine();
                while (r1.isPrivilegedUserOrNot(username, password) != -1) { // if admin info is found, ask to change username
                    System.out.print("ERROR!!! Admin with same UserName already exist. Enter new UserName:   ");
                    username = read1.nextLine();
                    System.out.print("Enter the Password Again:   ");
                    password = read1.nextLine();
                }

                // insert admin username and password into the array
                adminUserNameAndPassword[countNumOfUsers][0] = username;
                adminUserNameAndPassword[countNumOfUsers][1] = password;

                // increment number of users
                countNumOfUsers++;
            } else if (desiredOption == 3) { // if option 3, Passenger login
                printArtWork(3);
                System.out.print("\n\nEnter the Email to Login :  ");
                String userName = read1.nextLine();
                System.out.print("Enter the Password :  ");
                String password = read1.nextLine();
                String[] result = r1.isPassengerRegistered(userName, password).split("-");

                if (Integer.parseInt(result[0]) == 1) {
                    int desiredChoice;
                    System.out.printf("\n\n%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....", "", userName);
                    do { // options for the Passenger
                        System.out.printf("\n\n%-60s+++++++++ 3rd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "", "", userName);
                        System.out.printf("%-40s (a) Enter 1 to Book a flight....\n", "");
                        System.out.printf("%-40s (b) Enter 2 to update your Data....\n", "");
                        System.out.printf("%-40s (c) Enter 3 to delete your account....\n", "");
                        System.out.printf("%-40s (d) Enter 4 to Display Flight Schedule....\n", "");
                        System.out.printf("%-40s (e) Enter 5 to Cancel a Flight....\n", "");
                        System.out.printf("%-40s (f) Enter 6 to Display all flights registered by \"%s\"....\n", "", userName);
                        System.out.printf("%-40s (g) Enter 0 to Go back to the Main Menu/Logout....\n", "");
                        System.out.print("Enter the desired Choice :   ");
                        desiredChoice = read.nextInt();
                        if (desiredChoice == 1) { // if choose 1, display all schedules and book a flight
                            bookingAndReserving.displayArtWork(1);
                            f1.displayFlightSchedule();
                            System.out.print("\nEnter the desired flight number to book :  ");
                            String flightToBeBooked = read1.nextLine();
                            System.out.print("Enter the Number of tickets for " + flightToBeBooked + " flight :   ");
                            int numOfTickets = read.nextInt();
                            while (numOfTickets > 10) { // limit the number of tickets ordered by 10
                                System.out.print("ERROR!! You can't book more than 10 tickets at a time for single flight....Enter number of tickets again : ");
                                numOfTickets = read.nextInt();
                            }
                            bookingAndReserving.bookFlight(flightToBeBooked, numOfTickets, result[1]);
                        } else if (desiredChoice == 2) { // if choose 2, call the method to edit passenger info
                            bookingAndReserving.displayArtWork(2);
                            c1.editUserInfo(result[1]);
                        } else if (desiredChoice == 3) { // if choose 3, delete a passenger
                            bookingAndReserving.displayArtWork(3);
                            System.out.print("Are you sure to delete your account...It's an irreversible action...Enter Y/y to confirm...");
                            char confirmationChar = read1.nextLine().charAt(0);
                            if (confirmationChar == 'Y' || confirmationChar == 'y') {
                                c1.deleteUser(result[1]); // call method to delete user
                                System.out.printf("User %s's account deleted Successfully...!!!", userName);
                                desiredChoice = 0; // go back to main menu
                            } else {
                                System.out.println("Action has been cancelled...");
                            }
                        } else if (desiredChoice == 4) { // if choose 4, call method to display the randomized flight schedule
                            bookingAndReserving.displayArtWork(4);
                            f1.displayFlightSchedule();
                        } else if (desiredChoice == 5) { // if choose 5, call the cancel flight method
                            bookingAndReserving.displayArtWork(5);
                            bookingAndReserving.cancelFlight(result[1]);
                        } else if (desiredChoice == 6) { // if choose 6, call the method to display flights registered by passenger
                            bookingAndReserving.displayArtWork(6);
                            bookingAndReserving.displayFlightsRegisteredByOneUser(result[1]);
                        } else {
                            bookingAndReserving.displayArtWork(7);
                            if (desiredChoice != 0) {
                                System.out.println("Invalid Choice. Try logging in again...");
                            }
                            desiredChoice = 0;
                        }
                    } while (desiredChoice != 0);

                } else {
                    System.out.printf("\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n", "");
                }
            } else if (desiredOption == 4) { // if option 4, Passenger register
                printArtWork(4);
                c1.addNewCustomer();
            }

            displayMainMenu();
            desiredOption = read1.nextInt(); // read input
            while (desiredOption < 0 || desiredOption > 8) { // option has to be a number from 0 to 4
                System.out.print("ERROR!! Please enter value between 0 - 4. Enter the value again : ");
                desiredOption = read1.nextInt();
            }
        } while (desiredOption != 0);
        welcomeScreen(-1);
    }

    static void displayMainMenu() { // main menu
        System.out.println("\n\n(a) Press 0 to Exit.");
        System.out.println("(b) Press 1 to Login as admin.");
        System.out.println("(c) Press 2 to Register as admin.");
        System.out.println("(d) Press 3 to Login as Passenger.");
        System.out.println("(e) Press 4 to Register as Passenger.");
        System.out.print(" Enter the desired option:    ");
    }


    static void welcomeScreen(int option) {
        String artWork;

        if (option == 1) {
            artWork = """

                    888       888          888                                                888                   888888b.     d8b                                            d8888 d8b         888 d8b                           \s
                    888   o   888          888                                                888                   888  "88b    Y8P                                           d88888 Y8P         888 Y8P                           \s
                    888  d8b  888          888                                                888                   888  .88P                                                 d88P888             888                               \s
                    888 d888b 888  .d88b.  888  .d8888b  .d88b.  88888b.d88b.   .d88b.        888888  .d88b.        8888888K.    888  88888b.   db    db   .d8888b           d88P 888 888 888d888 888 888 88888b.   .d88b.  .d8888b \s
                    888d88888b888 d8P  Y8b 888 d88P"    d88""88b 888 "888 "88b d8P  Y8b       888    d88""88b       888  "Y88b   888  888 "88b  88    88   88K              d88P  888 888 888P"   888 888 888 "88b d8P  Y8b 88K     \s
                    88888P Y88888 88888888 888 888      888  888 888  888  888 88888888       888    888  888       888    888   888  888 "88b  88    88   "Y8888b.        d88P   888 888 888     888 888 888 "88b 88888888 "Y8888b.\s
                    8888P   Y8888 Y8b.     888 Y88b.    Y88..88P 888  888  888 Y8b.           Y88b.  Y88..88P       888   d88P   888  888  888  88b  d88        X88       d8888888888 888 888     888 888 888  888 Y8b.          X88\s
                    888P     Y888  "Y8888  888  "Y8888P  "Y88P"  888  888  888  "Y8888         "Y888  "Y88P"        8888888P"    888  888  888  ~Y8888P'   88888P'       d88P     888 888 888     888 888 888  888  "Y8888   88888P'\s
                                                                                                                                                                                                                                    \s
                                                                                                                                                                                                                                    \s
                                                                                                                                                                                                                                    \s
                    """;
        } else {
            artWork = "Thank you for visiting Binus Airlines !";
        }
        System.out.println(artWork);
    }

    static void printArtWork(int option) {
        String artWork;
        if (option == 4) {
            artWork = """

                     .o88b. db    db .d8888. d888888b  .d88b.  .88b  d88. d88888b d8888b.      .d8888. d888888b  d888b  d8b   db db    db d8888b.\s
                    d8P  Y8 88    88 88'  YP `~~88~~' .8P  Y8. 88'YbdP`88 88'     88  `8D      88'  YP   `88'   88' Y8b 888o  88 88    88 88  `8D\s
                    8P      88    88 `8bo.      88    88    88 88  88  88 88ooooo 88oobY'      `8bo.      88    88      88V8o 88 88    88 88oodD'\s
                    8b      88    88   `Y8b.    88    88    88 88  88  88 88~~~~~ 88`8b          `Y8b.    88    88  ooo 88 V8o88 88    88 88~~~  \s
                    Y8b  d8 88b  d88 db   8D    88    `8b  d8' 88  88  88 88.     88 `88.      db   8D   .88.   88. ~8~ 88  V888 88b  d88 88     \s
                     `Y88P' ~Y8888P' `8888Y'    YP     `Y88P'  YP  YP  YP Y88888P 88   YD      `8888Y' Y888888P  Y888P  VP   V8P ~Y8888P' 88     \s
                                                                                                                                                 \s
                                                                                                                                                 \s
                    """;
        } else if (option == 3) {
            artWork = """

                     .o88b. db    db .d8888. d888888b  .d88b.  .88b  d88. d88888b d8888b.      db       .d88b.   d888b  d888888b d8b   db\s
                    d8P  Y8 88    88 88'  YP `~~88~~' .8P  Y8. 88'YbdP`88 88'     88  `8D      88      .8P  Y8. 88' Y8b   `88'   888o  88\s
                    8P      88    88 `8bo.      88    88    88 88  88  88 88ooooo 88oobY'      88      88    88 88         88    88V8o 88\s
                    8b      88    88   `Y8b.    88    88    88 88  88  88 88~~~~~ 88`8b        88      88    88 88  ooo    88    88 V8o88\s
                    Y8b  d8 88b  d88 db   8D    88    `8b  d8' 88  88  88 88.     88 `88.      88booo. `8b  d8' 88. ~8~   .88.   88  V888\s
                     `Y88P' ~Y8888P' `8888Y'    YP     `Y88P'  YP  YP  YP Y88888P 88   YD      Y88888P  `Y88P'   Y888P  Y888888P VP   V8P\s
                                                                                                                                         \s
                                                                                                                                         \s
                    """;
        } else if (option == 2) {
            artWork = """

                     .d8b.  d8888b. .88b  d88. d888888b d8b   db      .d8888. d888888b  d888b  d8b   db db    db d8888b.\s
                    d8' `8b 88  `8D 88'YbdP`88   `88'   888o  88      88'  YP   `88'   88' Y8b 888o  88 88    88 88  `8D\s
                    88ooo88 88   88 88  88  88    88    88V8o 88      `8bo.      88    88      88V8o 88 88    88 88oodD'\s
                    88~~~88 88   88 88  88  88    88    88 V8o88        `Y8b.    88    88  ooo 88 V8o88 88    88 88~~~  \s
                    88   88 88  .8D 88  88  88   .88.   88  V888      db   8D   .88.   88. ~8~ 88  V888 88b  d88 88     \s
                    YP   YP Y8888D' YP  YP  YP Y888888P VP   V8P      `8888Y' Y888888P  Y888P  VP   V8P ~Y8888P' 88     \s
                                                                                                                        \s
                                                                                                                        \s
                        \s""";
        } else {
            artWork = """

                     .d8b.  d8888b. .88b  d88. d888888b d8b   db      db       .d88b.   d888b  d888888b d8b   db\s
                    d8' `8b 88  `8D 88'YbdP`88   `88'   888o  88      88      .8P  Y8. 88' Y8b   `88'   888o  88\s
                    88ooo88 88   88 88  88  88    88    88V8o 88      88      88    88 88         88    88V8o 88\s
                    88~~~88 88   88 88  88  88    88    88 V8o88      88      88    88 88  ooo    88    88 V8o88\s
                    88   88 88  .8D 88  88  88   .88.   88  V888      88booo. `8b  d8' 88. ~8~   .88.   88  V888\s
                    YP   YP Y8888D' YP  YP  YP Y888888P VP   V8P      Y88888P  `Y88P'   Y888P  Y888888P VP   V8P\s
                                                                                                                \s
                                                                                                                \s
                    """;
        }

        System.out.println(artWork);
    }


    public static List<Customer> getCustomersCollection() {
        return customersCollection;
    }
}