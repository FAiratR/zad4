package ru.inno;

import java.util.ArrayList;
import java.util.List;
// класс для файлов и распарсенных строк из файлов

class Files {
    private String nameFile;
    private List<FileLines> fileLines;
    public Files(String nameFile) {
        this.nameFile = nameFile;
        this.fileLines = new ArrayList<>();
    }
    public String getNameFile() {
        return nameFile;
    }
    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
    public List<FileLines> getFileLines() {
        return fileLines;
    }
    public void setFileLines(List<FileLines> fileLines) {
        this.fileLines = fileLines;
    }
}
