package ru.inno;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Component
public class ErrorLog implements ErrorLogable{
    @Override
    public void insLog(FileLines fileLines, String fileName){
        try {
            Files.write(Path.of("C:/Temp/"+"file_err.txt/"),
                    ("В файле '" + fileName + "' не указана дата" + "\n").getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
