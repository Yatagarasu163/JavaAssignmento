package IO;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class FileHandler {

    private static final String separator = "></";

    public static void write(String filename, List<String[]> data, boolean append) {
        String path = "src/database/" + filename;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, append))) {


            for (String[] rowData : data) {

                bw.write(String.join(separator, rowData));
                bw.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error writing to file: " + path);
            e.printStackTrace();
        }


    }

    public static List<String[]> read(String filename) {
        List<String[]> output = new ArrayList<>();
        String path = "src/database/" + filename;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = br.readLine()) != null) {
                output.add(line.split(separator));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist yet: " + path);
        } catch (IOException e) {
            System.err.println("Error reading from file: " + path);
            e.printStackTrace();
        }

        return output;
    }
}
