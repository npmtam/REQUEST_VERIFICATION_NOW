package facebook.nowgroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readfile {
    String email,password;
    public static void main(String[] args) {
        try {
            File myObj = new File("C:\\Attachments\\login.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            String pass = myReader.nextLine();
            System.out.println(data);
            System.out.println(pass);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
