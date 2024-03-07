package ru.inno;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CheckDate implements Checkable{
    @LogTransformation("logCheckDate.txt")
    @Override
    public void check(FileLines fileLines, String fileName, ErrorLogable errorLog){
        if (fileLines.getAccessDate().isEmpty() || !parseDate(fileLines.getAccessDate())) {
            errorLog.insLog(fileLines, fileName);
            fileLines.clear();
        }
    }

    boolean parseDate(String strDate){
        try {
            Date tmpDate = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
            return true;
        }
        catch (ParseException e){
            return false;
        }
    }
}
