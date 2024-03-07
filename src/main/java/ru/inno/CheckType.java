package ru.inno;

import org.springframework.stereotype.Component;

@Component
public class CheckType implements Checkable{
    @Override
    public void check(FileLines fileLines, String fileName, ErrorLogable errorLog){
        String tmpStr = fileLines.getTypeApp();
        if (!(tmpStr.equals("web") || tmpStr.equals("mobile")))
            fileLines.setTypeApp("other: " + tmpStr);
    }
}
