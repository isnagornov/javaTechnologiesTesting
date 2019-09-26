package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ReadFilesByManyWays {

    public static void main(String[] args) throws IOException {

        final String divider = "|-----------------------------------------------------------|";
        final String fileName = "/txt/testFile.txt";

        URL fileUrl = ReadFilesByManyWays.class.getResource(fileName);

        if (fileUrl == null) {
            throw new IllegalArgumentException("File with name " + fileName + " is absent!");
        }

        String pathToFile = fileUrl.toString().replace("file:/", "");

        System.out.println("readByFileReader\n");
        readByFileReader(pathToFile);
        System.out.println(divider);

        System.out.println("readByScanner\n");
        readByScanner(pathToFile);
        System.out.println(divider);

        System.out.println("readAsString\n");
        readAsString(pathToFile);
        System.out.println(divider);

    }

    private static void readByFileReader(String fileName) {

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line = bufferedReader.readLine();

            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void readByScanner(String fileName) {

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void readAsString(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);

        lines.forEach(System.out::println);

    }
}
