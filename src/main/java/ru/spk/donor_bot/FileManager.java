package ru.spk.donor_bot;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class FileManager {
    public static String readFile(Path filePath) {
        List<String> list;
        try {
            list = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        }catch (IOException e) {
            log.error(e.getMessage());
            return "Файл не найден";
        }
        StringBuilder text = new StringBuilder();
        for (String s : list) {
            text.append(s).append("\n");
        }
        return text.toString();
    }
}
