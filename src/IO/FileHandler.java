package IO;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class FileHandler {

    // private static final String separator = "Kinoko";
    // private static final String defaultDataFilePath = "data/";

    // private FileHandler() {

    // }

//     public static void write(String filename, ArrayList<String> data){
//         try {
//             BufferedWriter bw = new BufferedWriter(new FileWriter("src/database/" + filename, true));
//                 for(int i = 0; i < data.size(); i++){
//                     if(i != data.size() - 1){
//                         bw.write(data.get(i) + ",");
//                     } else{
//                         bw.write(data.get(i) + "\n");
//                     }
//                 }
//             bw.close();
//
//         } catch (IOException e) {
//             System.err.println("Error writing to file: " + "src/database/" + filename);
//             e.printStackTrace();
//         }
//     }

//     public static ArrayList read(String filename){
//
//         ArrayList<String[]> outputList = new ArrayList<String[]>();
//
//         try {
//             String line = "";
//             BufferedReader br = new BufferedReader(new FileReader(defaultDataFilePath + filename));
//             while ((line = br.readLine()) != null){
//                 String[] list = line.split(separator);
//             }
//         } catch(IOException e){
//             System.err.println("Error reading file: " + defaultDataFilePath + filename);
//         }
//     }

    public static ArrayList<String> read(String filename) {
        // This list will hold each FULL line as one entry
        ArrayList<String> data = new ArrayList<>();
        String path = "src/database/" + filename;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + path);
            e.printStackTrace();
        }

        return data;
    }

    public static void write(String filename, ArrayList<String> data, boolean append) {
        String path = "src/database/" + filename;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, append))) {
            for (String row : data) {
                // Write the entire string (e.g., "i ,am, a, technician")
                bw.write(row);

                // Move to the next line in the text file
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + path);
            e.printStackTrace();
        }
    }
}
