package ru.inno;

import java.util.List;

public interface FileScannerable {
    public void runScanFolder(String path);
    public List<Files> getFiles();

}
