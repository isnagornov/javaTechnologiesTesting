package files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MergeFileExecutor {

    private static String INPUT_NAME = "D:/Temp/file/file.mp4";
    private static String OUT_FILENAME = "D:/Temp/file/out.mp4";

    public static void main(String[] args) {

        List<File> files = new ArrayList<>();

        int chunkIdx = 1;
        while (true) {
            File file = new File(INPUT_NAME + ".part" + String.valueOf(chunkIdx));

            if (!file.exists()) {
                break;
            } else {
                files.add(file);
                chunkIdx++;
            }
        }

        if (!files.isEmpty()) {
            try (OutputStream fileOutputStream = new FileOutputStream(OUT_FILENAME);
                 OutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

                for (File file: files) {
                    try (FileInputStream fileInputStream = new FileInputStream(file);
                         InputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                        byte[] fileBytes = new byte[(int) file.length()];
                        bufferedInputStream.read(fileBytes, 0,(int)  file.length());

                        bufferedOutputStream.write(fileBytes);

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Can't merge to single file, parts not found");
        }



    }
}
