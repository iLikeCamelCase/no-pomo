import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class persistentStorage {
    public static ArrayList<String[]> readUserData(){
        ArrayList<String[]> userList = new ArrayList<String[]>();
        BufferedReader readFileBuffer = null;

        try {
            readFileBuffer = new BufferedReader(new FileReader("./data/userData.txt"));
            String tempList[];
            String line = readFileBuffer.readLine();
            while (line != null){
                tempList = line.split(",");
                userList.add(tempList);
                line = readFileBuffer.readLine();
            }
        }
        catch (IOException e){
            System.out.println("Something went wrong here :/");
        }
        finally {
            try {
                readFileBuffer.close();
            }
            catch (IOException e) {
                System.out.println("Something went wrong here :/");
            }
        }
        return userList;
    }
}
