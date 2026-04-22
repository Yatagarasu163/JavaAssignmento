package IO;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;

public class FileHandler {

    // private static final String separator = "Kinoko";
    // private static final String defaultDataFilePath = "data/";

    // private FileHandler() {

    // }

//     public static void write(String filename, ArrayList<String> data){
//         try {
//             BufferedWriter bw = new BufferedWriter(new FileWriter("src/Databases/" + filename, true));
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
//             System.err.println("Error writing to file: " + "src/Databases/" + filename);
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
}
