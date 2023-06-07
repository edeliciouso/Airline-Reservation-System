import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Customer {
    
    private final String userID;
    private String email;
    private String name;
    private String phone;
    private final String password;
    private String address;
    private int age;
    public List<Flight> flightsRegisteredByUser;
    public List<Integer> numOfTicketsBookedByUser;
    public static final List<Customer> customerCollection = User.getCustomersCollection();
    
    Customer() {
        this.userID = null;
        this.name = null;
        this.email = null;
        this.password = null;
        this.phone = null;
        this.address = null;
        this.age = 0;
    }


    // registers new Passengers to the program
    Customer(String name, String email, String password, String phone, String address, int age) {
        RandomGenerator random = new RandomGenerator();
        random.randomIDGen(); // assign random IDs to the Passengers
        this.name = name;
        this.userID = random.getRandomNumber();
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.flightsRegisteredByUser = new ArrayList<>();
        this.numOfTicketsBookedByUser = new ArrayList<>();
    }


    // takes input from Passenger and adds them to memory
    public void addNewCustomer() {
        System.out.printf("\n\n\n%60s ++++++++++++++ Welcome to the Customer Registration Portal ++++++++++++++", "");
        Scanner read = new Scanner(System.in);
        System.out.print("\nEnter your name : ");
        String name = read.nextLine();
        System.out.print("Enter your email address : ");
        String email = read.nextLine();
        while (isUniqueData(email)) { // checks email, if already exists, prompts Passenger to enter new email
            System.out.println("ERROR!!! User with the same email already exists... Use new email or login using the previous credentials....");
            System.out.print("Enter your email address : ");
            email = read.nextLine();
        }
        System.out.print("Enter your Password : ");
        String password = read.nextLine();
        System.out.print("Enter your Phone number : ");
        String phone = read.nextLine();
        System.out.print("Enter your address : ");
        String address = read.nextLine();
        System.out.print("Enter your age : ");
        int age = read.nextInt();
        customerCollection.add(new Customer(name, email, password, phone, address, age));

        // adds all the input into a txt file
        File file = new File("C:/Users/edely/Downloads/AirLineReservationSystem/AirLineReservationSystem/src/PassengerData.txt");
        Writer fileWrite = null;
        try {
            fileWrite = new FileWriter(file, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fileWrite.write("Passenger:\n");
            fileWrite.write(name + "\n");
            fileWrite.write(email + "\n");
            fileWrite.write(password + "\n");
            fileWrite.write(phone + "\n");
            fileWrite.write(address + "\n");
            fileWrite.write(age + " \n");
            fileWrite.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    // returns String that consists of Passenger's data
    private String toString(int i) {
        return String.format("%10s| %-10d | %-10s | %-32s | %-7s | %-27s | %-35s | %-23s |", "", i, randomIDDisplay(userID), name, age, email, address, phone); // randomIDDisplay adds space between userID to make it easier to read
    }


    // searches for Passenger with ID and display
    public void searchUser(String ID) {
        boolean isFound = false;
        Customer customerWithTheID = customerCollection.get(0);
        for (Customer c : customerCollection) {
            if (ID.equals(c.getUserID())) { // if ID equals the ID in the customerCollection list
                System.out.printf("%-50sCustomer Found...!!!Here is the Full Record...!!!\n\n\n", " ");
                displayHeader();
                isFound = true;
                customerWithTheID = c;
                break;
            }
        }
        if (isFound) {
            System.out.println(customerWithTheID.toString(1));
            System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    // returns true if email is registered
    public boolean isUniqueData(String emailID) {
        boolean isUnique = false;
        for (Customer c : customerCollection) { // searches through each Customer
            if (emailID.equals(c.getEmail())) {
                isUnique = true; // returns true if emailID inputted is the same as email in list
                break;
            }
        }
        return isUnique;
    }

    // update Passenger data
    public void editUserInfo(String ID) {
        boolean isFound = false;
        Scanner read = new Scanner(System.in);
        for (Customer c : customerCollection) { // search every Customer for their ID
            if (ID.equals(c.getUserID())) {
                isFound = true;
                System.out.print("\nEnter the new name of the Passenger: ");
                String name = read.nextLine();
                c.setName(name); // set new name
                System.out.print("Enter the new email address of Passenger " + name + ": ");
                c.setEmail(read.nextLine()); // set new email
                System.out.print("Enter the new Phone number of Passenger " + name + ": ");
                c.setPhone(read.nextLine()); // set new phone number
                System.out.print("Enter the new address of Passenger " + name + ": ");
                c.setAddress(read.nextLine()); // set new address
                System.out.print("Enter the new age of Passenger " + name + ": ");
                c.setAge(read.nextInt()); // set new age
                displayCustomersData(false);
                break;
            }
        }
        if (!isFound) { // if found then will print out message
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    // delete Passenger data
    public void deleteUser(String ID) {
        boolean isFound = false;
        Iterator<Customer> iterator = customerCollection.iterator(); // iterator to modify the whole list
        while (iterator.hasNext()) { // as long as there's an element to iterate to next
            Customer customer = iterator.next(); // makes a Customer object using the next iteration
            if (ID.equals(customer.getUserID())) {
                isFound = true; // if Customer found, set to true
                break;
            }
        }
        if (isFound) { // if isFound is true
            iterator.remove(); // remove an element from the customerCollection
            System.out.printf("\n%-50sPrinting all  Customer's Data after deleting Customer with the ID %s.....!!!!\n", "", ID);
            displayCustomersData(false);
        } else { // if false, print out message
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }


    // show Passenger's data
    public void displayCustomersData(boolean showHeader) { // showHeader for ASCII art
        if (showHeader) {
            displayArtWork(3);
        }
        displayHeader();
        Iterator<Customer> iterator = customerCollection.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            Customer c = iterator.next();
            System.out.println(c.toString(i)); // turns the c object of i index into a string
            System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        }
    }

    // header for the Passenger data table
    void displayHeader() {
        System.out.println();
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        System.out.printf("%10s| SerialNum  |   UserID   | Passenger Names                  | Age     | EmailID\t\t       | Home Address\t\t\t     | Phone Number\t       |%n", "");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        System.out.println();

    }


    // function to add space between the userID to make it easier to read
    String randomIDDisplay(String randomID) {
        StringBuilder newString = new StringBuilder(); // stringbuilder to manipulate strings
        for (int i = 0; i <= randomID.length(); i++) { // loop through the length of the id
            if (i == 3) { // add an empty space to the fourth character in randomID
                newString.append(" ").append(randomID.charAt(i));
            } else if (i < randomID.length()) { // if it isn't the 3rd index yet
                newString.append(randomID.charAt(i));
            }
        }
        return newString.toString(); // return a string id with the added space
    }

    // associates new flight with the Passenger
    void addNewFlightToCustomerList(Flight f) {
        this.flightsRegisteredByUser.add(f);
    }

    // add number of tickets to already booked flights
    void addExistingFlightToCustomerList(int index, int numOfTickets) {
        // index represents position of flight in the list
        // numOfTickets represents number of tickets to be added
        int newNumOfTickets = numOfTicketsBookedByUser.get(index) + numOfTickets; // add number of new tickets to the current list
        this.numOfTicketsBookedByUser.set(index, newNumOfTickets); // updates the numOfTicketsBookedByUser list
    }

    // prints ASCII art
    void displayArtWork(int option) {
        String artWork = "";
        if (option == 1) {
            artWork = """
                                        
                    d8b   db d88888b db   d8b   db       .o88b. db    db .d8888. d888888b  .d88b.  .88b  d88. d88888b d8888b.\s
                    888o  88 88'     88   I8I   88      d8P  Y8 88    88 88'  YP `~~88~~' .8P  Y8. 88'YbdP`88 88'     88  `8D\s
                    88V8o 88 88ooooo 88   I8I   88      8P      88    88 `8bo.      88    88    88 88  88  88 88ooooo 88oobY'\s
                    88 V8o88 88~~~~~ Y8   I8I   88      8b      88    88   `Y8b.    88    88    88 88  88  88 88~~~~~ 88`8b  \s
                    88  V888 88.     `8b d8'8b d8'      Y8b  d8 88b  d88 db   8D    88    `8b  d8' 88  88  88 88.     88 `88.\s
                    VP   V8P Y88888P  `8b8' `8d8'        `Y88P' ~Y8888P' `8888Y'    YP     `Y88P'  YP  YP  YP Y88888P 88   YD\s
                                                                                                                             \s
                                                                                                                             \s
                    """;;
        } else if (option == 2) {
            artWork = """
                                        
                    .d8888. d88888b  .d8b.  d8888b.  .o88b. db   db       .o88b. db    db .d8888. d888888b  .d88b.  .88b  d88. d88888b d8888b.\s
                    88'  YP 88'     d8' `8b 88  `8D d8P  Y8 88   88      d8P  Y8 88    88 88'  YP `~~88~~' .8P  Y8. 88'YbdP`88 88'     88  `8D\s
                    `8bo.   88ooooo 88ooo88 88oobY' 8P      88ooo88      8P      88    88 `8bo.      88    88    88 88  88  88 88ooooo 88oobY'\s
                      `Y8b. 88~~~~~ 88~~~88 88`8b   8b      88~~~88      8b      88    88   `Y8b.    88    88    88 88  88  88 88~~~~~ 88`8b  \s
                    db   8D 88.     88   88 88 `88. Y8b  d8 88   88      Y8b  d8 88b  d88 db   8D    88    `8b  d8' 88  88  88 88.     88 `88.\s
                    `8888Y' Y88888P YP   YP 88   YD  `Y88P' YP   YP       `Y88P' ~Y8888P' `8888Y'    YP     `Y88P'  YP  YP  YP Y88888P 88   YD\s
                                                                                                                                              \s
                                                                                                                                              \s
                    """;;
        } else if (option == 3) {
            artWork = """
                                        
                    .d8888. db   db  .d88b.  db   d8b   db d888888b d8b   db  d888b        .d8b.  db      db           d8888b.  .d8b.  .d8888. .d8888. d88888b d8b   db  d888b  d88888b d8888b. .d8888.\s
                    88'  YP 88   88 .8P  Y8. 88   I8I   88   `88'   888o  88 88' Y8b      d8' `8b 88      88           88  `8D d8' `8b 88'  YP 88'  YP 88'     888o  88 88' Y8b 88'     88  `8D 88'  YP\s
                    `8bo.   88ooo88 88    88 88   I8I   88    88    88V8o 88 88           88ooo88 88      88           88oodD' 88ooo88 `8bo.   `8bo.   88ooooo 88V8o 88 88      88ooooo 88oobY' `8bo.  \s
                      `Y8b. 88~~~88 88    88 Y8   I8I   88    88    88 V8o88 88  ooo      88~~~88 88      88           88~~~   88~~~88   `Y8b.   `Y8b. 88~~~~~ 88 V8o88 88  ooo 88~~~~~ 88`8b     `Y8b.\s
                    db   8D 88   88 `8b  d8' `8b d8'8b d8'   .88.   88  V888 88. ~8~      88   88 88booo. 88booo.      88      88   88 db   8D db   8D 88.     88  V888 88. ~8~ 88.     88 `88. db   8D\s
                    `8888Y' YP   YP  `Y88P'   `8b8' `8d8'  Y888888P VP   V8P  Y888P       YP   YP Y88888P Y88888P      88      YP   YP `8888Y' `8888Y' Y88888P VP   V8P  Y888P  Y88888P 88   YD `8888Y'\s
                                                                                                                                                                                                       \s
                                                                                                                                                                                                       \s
                    """;
        } else if (option == 4) {
            artWork = """
                                        
                    d8888b. d88888b  d888b  d888888b .d8888. d888888b d88888b d8888b. d88888b d8888b.      d8888b.  .d8b.  .d8888. .d8888. d88888b d8b   db  d888b  d88888b d8888b. .d8888.     \s
                    88  `8D 88'     88' Y8b   `88'   88'  YP `~~88~~' 88'     88  `8D 88'     88  `8D      88  `8D d8' `8b 88'  YP 88'  YP 88'     888o  88 88' Y8b 88'     88  `8D 88'  YP     \s
                    88oobY' 88ooooo 88         88    `8bo.      88    88ooooo 88oobY' 88ooooo 88   88      88oodD' 88ooo88 `8bo.   `8bo.   88ooooo 88V8o 88 88      88ooooo 88oobY' `8bo.       \s
                    88`8b   88~~~~~ 88  ooo    88      `Y8b.    88    88~~~~~ 88`8b   88~~~~~ 88   88      88~~~   88~~~88   `Y8b.   `Y8b. 88~~~~~ 88 V8o88 88  ooo 88~~~~~ 88`8b     `Y8b.     \s
                    88 `88. 88.     88. ~8~   .88.   db   8D    88    88.     88 `88. 88.     88  .8D      88      88   88 db   8D db   8D 88.     88  V888 88. ~8~ 88.     88 `88. db   8D     \s
                    88   YD Y88888P  Y888P  Y888888P `8888Y'    YP    Y88888P 88   YD Y88888P Y8888D'      88      YP   YP `8888Y' `8888Y' Y88888P VP   V8P  Y888P  Y88888P 88   YD `8888Y' \s
                                        
                       \s
                    d888888b d8b   db      d88888b db      d888888b  d888b  db   db d888888b                                                                                                    \s
                      `88'   888o  88      88'     88        `88'   88' Y8b 88   88 `~~88~~'                                                                                                    \s
                       88    88V8o 88      88ooo   88         88    88      88ooo88    88                                                                                                       \s
                       88    88 V8o88      88~~~   88         88    88  ooo 88~~~88    88                                                                                                       \s
                      .88.   88  V888      88      88booo.   .88.   88. ~8~ 88   88    88                                                                                                       \s
                    Y888888P VP   V8P      YP      Y88888P Y888888P  Y888P  YP   YP    YP                                                                                                       \s
                                                                                                                                                                                                \s
                                                                                                                                                                                                \s
                    """;
        } else if (option == 5) {
            artWork = """
                                        
                    d8888b. d88888b db      d88888b d888888b d88888b      d88888b db      d888888b  d888b  db   db d888888b\s
                    88  `8D 88'     88      88'     `~~88~~' 88'          88'     88        `88'   88' Y8b 88   88 `~~88~~'\s
                    88   88 88ooooo 88      88ooooo    88    88ooooo      88ooo   88         88    88      88ooo88    88   \s
                    88   88 88~~~~~ 88      88~~~~~    88    88~~~~~      88~~~   88         88    88  ooo 88~~~88    88   \s
                    88  .8D 88.     88booo. 88.        88    88.          88      88booo.   .88.   88. ~8~ 88   88    88   \s
                    Y8888D' Y88888P Y88888P Y88888P    YP    Y88888P      YP      Y88888P Y888888P  Y888P  YP   YP    YP   \s
                                                                                                                           \s
                                                                                                                           \s
                    """;
        }
        System.out.println(artWork);
    }

    // Setters and Getters
    public List<Flight> getFlightsRegisteredByUser() {
        return flightsRegisteredByUser;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getNumOfTicketsBookedByUser() {
        return numOfTicketsBookedByUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }
}