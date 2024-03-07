package ru.inno;

import org.springframework.stereotype.Component;

@Component
public class CheckFam implements Checkable{
    @Override
    public void check(FileLines fileLines, String fileName, ErrorLogable errorLog){
        fileLines.setFam(fileLines.getFam().substring(0,1).toUpperCase() + fileLines.getFam().substring(1).toLowerCase());
        fileLines.setName(fileLines.getName().substring(0,1).toUpperCase() + fileLines.getName().substring(1).toLowerCase());
        fileLines.setOtch(fileLines.getOtch().substring(0,1).toUpperCase() + fileLines.getOtch().substring(1).toLowerCase());
    }
}
