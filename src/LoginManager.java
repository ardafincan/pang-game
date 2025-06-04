package src;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class LoginManager {
    public static String userName = "";
    public static boolean isUserLoggedIn = false;
    

    public static void Register(){
        while(true){
            String userN = JOptionPane.showInputDialog("Please enter a username");
            String pass = JOptionPane.showInputDialog("Please enter a password");
            if(readFile().containsKey(userN)){
                JOptionPane.showMessageDialog(null, "Username in use please enter another name", "Username Invalid", JOptionPane.ERROR_MESSAGE);
            }else{
                String[] userCouple = {userN, pass};
                writeToFile(userCouple);
                userName = userN;
                isUserLoggedIn = true;
                break;
                }
        }
    };

    public static void Login(){
        while (true) {
            String userN = JOptionPane.showInputDialog("Please enter a username");
            String pass = JOptionPane.showInputDialog("Please enter a password");
            if(readFile().get(userN).equals(pass)){
                userName = userN;
                isUserLoggedIn = true;
                break;
            }else if(userN == null){
                isUserLoggedIn = false;
                break;
            }
            else{
                isUserLoggedIn = false;
                JOptionPane.showMessageDialog(null, "Incorrect username or password", "Unsuccessful Login", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
        

    public static void writeToFile(String[] userInfo){
        try {
            FileWriter myWriter = new FileWriter("users.txt", true);
            for(String feature : userInfo){
                myWriter.write(feature + ",");
            }
            myWriter.write("\n");
            myWriter.close();
            System.out.println("written");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static HashMap readFile(){
        HashMap<String, String> userInfos = new HashMap<>();
        try {
            Scanner myScanner = new Scanner(new File("users.txt"));
            while(myScanner.hasNextLine()){
                String line = myScanner.nextLine();
                String[] tempArr = line.split(",");

                userInfos.put(tempArr[0], tempArr[1]);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return userInfos;
    }

}
