package ru.gold.ordance.board.web.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static ru.gold.ordance.board.common.utils.TestUtils.randomString;

@Component
public final class PhotoStorageUtils {
    public static List<String> whiteList = List.of(".png", ".jpg", ".jpeg", ".bmp", ".dib", ".rle");

    private static String PATH;
    private static String LOCATION;

    private PhotoStorageUtils(@Value("${storage.path}") String path,
                              @Value("${storage.location}") String location) {
        PATH = path;
        LOCATION = location;
    }

    public static String moveFile(MultipartFile file) throws IOException {
        String urn = getUrn(file);
        Path path = Path.of(PATH + urn);

        if (!Files.isDirectory(Path.of(PATH + LOCATION))) {
            Files.createDirectories(path);
        }

        file.transferTo(path.toFile());

        return urn;
    }

    private static String getUrn(MultipartFile file) {
        return LOCATION  + "/" + randomString() + getExtension(file.getOriginalFilename());
    }

    public static String getExtension(String fullName) {
        int index = fullName.lastIndexOf(".");

        if (index == -1) {
            return "";
        }

        return fullName.substring(index);
    }

    public static void deleteFile(String urn) throws IOException {
        Files.deleteIfExists(Paths.get(PATH + urn));
    }
}
