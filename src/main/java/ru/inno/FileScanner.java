package ru.inno;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

// класс для сканирования папки и считывания строк из файлов
// файлы и строки считываются в класс Files

@Component
public class FileScanner implements FileScannerable{

    private List<Files> files;
    public FileScanner() {
        files = new ArrayList<Files>();
    }

    public void runScanFolder(String path){
        File folder = new File(path);
        listFilesForFolder(folder);
    }
    void listFilesForFolder (final File folder){
        if (folder.isDirectory()){
            for (final File file : folder.listFiles())
                listFilesForFolder(file);}
        else
            if (folder.getName().contains(".csv")) {
                Files f = new Files(folder.getName());

                try {
                    FileReader fileReader = new FileReader(folder);
                    CSVReader csvReader = new CSVReader(fileReader);

                    List<String[]> allData = csvReader.readAll();
                     for (String[] row : allData) {
                         FileLines fl = new FileLines(row[0], row[1], row[2], row[3], row[4], row[5]);
                         f.getFileLines().add(fl);
                    }
                    files.add(f);
                }
                catch (Exception e) {
                    throw new RuntimeException();
                }
            }
    }

    public List<Files> getFiles() {
        return this.files;
    }
}
