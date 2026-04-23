package IO;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class FileHandler {

    private static final String separator = "/><\\";
    // private static final String defaultDataFilePath = "data/";

    // private FileHandler() {

    // }

    // public static ArrayList<String> read(String filename) {
    //     // This list will hold each FULL line as one entry
    //     ArrayList<String> data = new ArrayList<>();
    //     String path = "src/database/" + filename;

    //     try (BufferedReader br = new BufferedReader(new FileReader(path))) {
    //         String line;

    //         while ((line = br.readLine()) != null) {
    //             data.add(line);
    //         }
    //     } catch (IOException e) {
    //         System.err.println("Error reading from file: " + path);
    //         e.printStackTrace();
    //     }

    //     return data;
    // }

    // public static void write(String filename, ArrayList<String> data, boolean append) {
    //     String path = "src/database/" + filename;

    //     try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, append))) {
    //         for (String row : data) {
    //             // Write the entire string (e.g., "i ,am, a, technician")
    //             bw.write(row);

    //             // Move to the next line in the text file
    //             bw.newLine();
    //         }
    //     } catch (IOException e) {
    //         System.err.println("Error writing to file: " + path);
    //         e.printStackTrace();
    //     }
    // }

    public static void write(String filename, ArrayList<String> data){
        String path = "src/database/" + filename;

        try{
            BufferedWriter bw;
            File file = new File(path);

            if(file.exists() && file.isFile()){
               System.out.println("File exists!"); 
                bw = new BufferedWriter(new FileWriter(path, true));
            } else{
                bw = new BufferedWriter(new FileWriter(path));
            }

            String inputString = "";
            for (String info : data){
                inputString += info + separator;
            }

            bw.write(inputString);

            bw.newLine();

            bw.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + path);
            e.printStackTrace();
        } 

    }

    public static List<String[]> read(String filename){

        List<String[]> output = new ArrayList<>();

        String path = "src/database/" + filename;

        try{
            BufferedReader br;
            File file = new File(path);
            if(file.exists() && file.isFile()){
                System.out.println("File exists!");

                br = new BufferedReader(new FileReader(path));

                String line;
                while ((line = br.readLine()) != null){
                    String[] array = line.split(separator);
                    output.add(array);
                }

                br.close();
            }
        } catch (IOException e){
            System.err.println("Error reading from file: " + path);
            e.printStackTrace();
        }

        return output;
    }
}
