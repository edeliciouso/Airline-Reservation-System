import java.io.*;
import java.util.ArrayList;

public class PassengerReader {
    public static void createPassenger() throws IOException {
        File file = new File("C:/Users/edely/Downloads/AirLineReservationSystem/AirLineReservationSystem/src/PassengerData.txt");
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String txt;
        ArrayList<String> txtArray = new ArrayList<>();
        while((txt = buffer.readLine()) != null){
            txtArray.add(txt);
        }

        for(int i = 0; i < txtArray.size(); i += 6){
            String name = txtArray.get(1 + i);
            String email = txtArray.get(2 + i);
            String password = txtArray.get(3 + i);
            String phone = txtArray.get(4 + i);
            String address = txtArray.get(5 + i);
            int age = Integer.parseInt(txtArray.get(6 + i));
            i++;
            Customer.customerCollection.add(new Customer(name, email, password, phone, address, age));
        }
    }
}
