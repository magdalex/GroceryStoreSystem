import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.HashMap;

public class Userdata {
    public static HashMap<String, String> read() throws FileNotFoundException {
        // create stack instance
        HashMap<String, String> userdata = new HashMap<>();
        // create file reader
        Scanner fileInput = new Scanner(new FileReader("users.in"));
        // send data to stack class
        // skipping first line
        fileInput.nextLine();
        while (fileInput.hasNextLine()) {
            String email = fileInput.next();
            String password = fileInput.next();
            userdata.put(email, password);
        }
        fileInput.close();
        return userdata;
    }

    public static void write(HashMap<String, String> userdata) throws IOException {
        FileWriter writer = new FileWriter("users.in");
        BiConsumer<String, String> wr = (s1, s2) -> {
            try {
                writer.write("\n" + s1 + "\t" + s2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        userdata.forEach(wr);
        writer.close();
    }
}
