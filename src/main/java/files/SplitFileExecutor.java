package files;

import java.io.*;

public class SplitFileExecutor {
    private static String FILE_NAME = "D:/Temp/file/file.mp4";
    private static int PART_SIZE = 4 * 1024 * 1024;

    public static void main(String[] args) {
        File inputFile = new File(FILE_NAME);

        int fileSize = (int) inputFile.length();

        if (fileSize > PART_SIZE) {
            try (FileInputStream fileInputStream = new FileInputStream(inputFile);
                 InputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

                int partsLength = fileSize / PART_SIZE;
                int lastPartSize = fileSize % PART_SIZE;

                for (int chunkIdx = 1; chunkIdx <= partsLength; chunkIdx++) {
                    writePart(bufferedInputStream, chunkIdx, PART_SIZE);
                }

                if (lastPartSize > 0) {
                    writePart(bufferedInputStream, partsLength + 1, lastPartSize);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No split operation performed, part size greater then file size");
        }

    }

    private static void writePart(InputStream inputStream, int chunkIdx, int size) throws IOException {

        byte[] byteChunkPart = new byte[size];
        inputStream.read(byteChunkPart, 0, size);

        try (FileOutputStream fileOutputStream = new FileOutputStream(
                new File(FILE_NAME + ".part" + Integer.toString(chunkIdx)));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
            bufferedOutputStream.write(byteChunkPart);
        }

    }

}


