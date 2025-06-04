package src;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class HistoryManager {
    
    public static void showHistory(){
        ArrayList<String[]> records = readFile();
        ListIterator<String[]> iterator = records.listIterator();
        String history = String.format("Last 15 Games\n");

        for(int i = 0; i < 15; i++){
            if(iterator.hasNext()){
                String[] record = iterator.next();
                if(record.length < 3) continue;
                String recordString = String.format("%s / %s / %s\n", record[0], record[1], record[2]);
                history = String.format(history + recordString);
                System.out.println(recordString);
            }
        }
        System.out.println(history);
        JOptionPane.showMessageDialog(null, history, "Game History", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showHighScores(){
        ArrayList<String[]> records = readFile();
        Collections.sort(records, new Comparator<String[]>() {
            @Override
            public int compare(String[] a, String[] b) {
                return Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1]));
            }
        });
        ListIterator<String[]> topIterator = records.listIterator();
        String topScores = String.format("Top 10 Scores\n");
        for(int i = 0; i < 10; i++){
            if(topIterator.hasNext()){
                String[] record = topIterator.next();
                String recordString = String.format("%s / %s \n", record[0], record[1]);
                topScores = String.format(topScores + recordString);
            }
        }
        JOptionPane.showMessageDialog(null, topScores, "Top Scores", JOptionPane.INFORMATION_MESSAGE);
    };
    

    public static void writeToFile(String[] gameLog){
        try {
            FileWriter myWriter = new FileWriter("records.txt", true);
            for(String feature : gameLog){
                myWriter.write(feature + ",");
            }
            myWriter.write("\n");
            myWriter.close();
            System.out.println("written");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static ArrayList<String[]> readFile(){
        ArrayList<String[]> records = new ArrayList<>();
        try {
            Scanner myScanner = new Scanner(new File("records.txt"));
            while(myScanner.hasNextLine()){
                String line = myScanner.nextLine();
                String[] tempArr = line.split(",");

                records.add(tempArr);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return records;
    }
}
