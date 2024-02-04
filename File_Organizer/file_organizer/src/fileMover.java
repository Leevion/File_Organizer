import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class fileMover {

    private static final List<String> AUDIO_EXTENSIONS = Arrays.asList(".mp3", ".wav", ".ogg", ".flac", ".aac", ".wma",
            ".m4a", ".ape", ".pcm", ".aiff", ".au", ".midi", ".ac3");
    private static final List<String> PICTURE_EXTENSIONS = Arrays.asList(".jpg", ".png", ".gif", ".bmp", ".svg",
            ".jpeg", ".heic", ".tif", ".tiff", ".webp", ".ico");
    private static final List<String> ZIP_EXTENSIONS = Arrays.asList(".zip", ".rar", ".tar", ".gz", ".z", ".iso");
    private static final List<String> DOC_EXTENSIONS = Arrays.asList(".docx", ".doc", ".txt");
    private static final List<String> POWERPOINT_EXTENSIONS = Arrays.asList(".pptx", ".ppt");
    private static final List<String> PDF_EXTENSIONS = Arrays.asList(".pdf", ".epub", ".mobi", ".azw", ".djvu", ".xps");
    private static final List<String> VIDEO_EXTENSIONS = Arrays.asList(".mp4", ".avi", ".mkv", ".mov", ".wmv", ".flv",
            ".webm", ".mpeg", ".3gp", ".m4v", ".rmvb", ".ts", ".vob", ".ogv", ".mpg", ".m2ts", ".divx");

    public static void main(String[] args) {
        // Change to your File Path  
        File source = new File("your source folder goes path goes here");  // You can make your source and target file path the same to organize the file in the folder. 
        File general = new File("your target file path goes here"); 
        File audio = new File(general, "Music");
        File pictures = new File(general, "Pictures");
        File docs = new File(general, "Docs");
        File pdfs = new File(docs, "PDFs");
        File powerPoints = new File(docs, "PowerPoints");
        File zip = new File(general, "ZipFiles");
        File video = new File(general, "video");

        File[] directories = { source, general, audio, pictures, docs, pdfs, powerPoints, zip, video };
        createDirectories(directories);

        moveFilesBasedOnType(source, general, audio, pictures, docs, pdfs, powerPoints, zip, video);
    }

    private static void createDirectories(File[] directories) {
        for (File dir : directories) {
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    private static void moveFilesBasedOnType(File source, File general, File audio, File pictures, File docs, File pdfs,
            File powerPoints, File zip, File video) {
        File[] files = source.listFiles();
        if (files != null) {
            for (File file : files) {
                moveFile(file,
                        determineDestination(file, general, audio, pictures, docs, pdfs, powerPoints, zip, video));
            }
        }
    }

    private static void moveFile(File sourceFile, File destination) {
        try {
            Files.move(sourceFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved file: " + sourceFile.getName());
        } catch (IOException e) {
            System.err.println("Error moving file: " + sourceFile.getName());
            e.printStackTrace();
        }
    }

    private static File determineDestination(File file, File general, File audio, File pictures, File docs, File pdfs,
            File powerPoints, File zip, File video) {
        String fileName = file.getName().toLowerCase();

        if (isExtensionMatch(fileName, AUDIO_EXTENSIONS)) {
            return new File(audio, file.getName());
        } else if (isExtensionMatch(fileName, PICTURE_EXTENSIONS)) {
            return new File(pictures, file.getName());
        } else if (isExtensionMatch(fileName, ZIP_EXTENSIONS)) {
            return new File(zip, file.getName());
        } else if (isExtensionMatch(fileName, DOC_EXTENSIONS)) {
            return new File(docs, file.getName());
        } else if (isExtensionMatch(fileName, POWERPOINT_EXTENSIONS)) {
            return new File(powerPoints, file.getName());
        } else if (isExtensionMatch(fileName, PDF_EXTENSIONS)) {
            return new File(pdfs, file.getName());
        } else if (isExtensionMatch(fileName, VIDEO_EXTENSIONS)) {
            return new File(video, file.getName());
        } else {
            return new File(general, file.getName());
        }
    }

    private static boolean isExtensionMatch(String fileName, List<String> extensions) {
        return extensions.stream().anyMatch(fileName::endsWith);
    }

}
