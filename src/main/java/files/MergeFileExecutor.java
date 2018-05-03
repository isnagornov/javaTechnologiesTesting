package files;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeFileExecutor {

    public static void main(String[] args) {
        final String INPUT_FILE_NAME = args[0];
        final String OUTPUT_FILE_NAME = args[1];

        File homeDir = new File(INPUT_FILE_NAME);
        File[] partFiles = homeDir.listFiles((dir, name) -> {
            File foundFile = new File(dir.getName() + "/" + name);

            return foundFile.getName().contains(".part");
        });

        if (partFiles == null || partFiles.length == 0) {
            throw new RuntimeException("Can't merge to single file, parts not found");
        }

        List<File> partList = Arrays.asList(partFiles);
        partList.sort(new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                String file1Name = file1.getName();
                String file2Name = file2.getName();

                String file1Number = file1Name.split(".part")[1];
                String file2Number = file2Name.split(".part")[1];

                return Integer.valueOf(file1Number).compareTo(Integer.valueOf(file2Number));
            }
        });

        try (OutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE_NAME);
             OutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

            for (File file : partFiles) {
                try (FileInputStream fileInputStream = new FileInputStream(file);
                     InputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                    byte[] fileBytes = new byte[(int) file.length()];
                    bufferedInputStream.read(fileBytes, 0, (int) file.length());

                    bufferedOutputStream.write(fileBytes);

                }
            }

            System.out.println(String.format("File %s successfully merged from %d parts", OUTPUT_FILE_NAME, partFiles.length));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

