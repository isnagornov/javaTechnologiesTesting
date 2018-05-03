package files;

import java.io.*;

public class SplitFileExecutor {

    public static void main(String[] args) {
        final String FILE_NAME = args[0];
        final int PART_SIZE = Integer.valueOf(args[1]) * 1024 * 1024;

        File inputFile = new File(args[0]);

        int fileSize = (int) inputFile.length();

        if (fileSize > PART_SIZE) {
            try (FileInputStream fileInputStream = new FileInputStream(inputFile);
                 InputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
                boolean quit = false;
                int chunkIdx = 1;
                while (!quit) {
                    int available = bufferedInputStream.available();

                    if (available > PART_SIZE) {
                        writePart(bufferedInputStream, FILE_NAME + ".part" + Integer.toString(chunkIdx++), PART_SIZE);
                    } else {
                        writePart(bufferedInputStream, FILE_NAME + ".part" + Integer.toString(chunkIdx++), available);
                        quit = true;
                    }
                }

                System.out.println(String.format("File %s successfully splitted to %d parts", FILE_NAME, chunkIdx - 1));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No split operation performed, part size greater then file size");
        }

    }

    private static void writePart(InputStream inputStream, String fileName, int fileSize) throws IOException {
        byte[] byteChunkPart = new byte[fileSize];

        inputStream.read(byteChunkPart, 0, fileSize);

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
            bufferedOutputStream.write(byteChunkPart);
        }
    }

}


