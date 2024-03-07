package ru.inno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import ru.inno.Checkable;

import java.io.File;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan("ru.inno")
//@Component
@EnableAspectJAutoProxy // аннотация для добавления прокси AOP
public class StartProject {
    private FileScannerable fileScanner;
    private DbSaveable dbSave;
    private List<Checkable> rules;
    private ErrorLogable errorLog;

    @Autowired
    public void setFileScanner(FileScannerable fileScanner) {
        this.fileScanner = fileScanner;
    }
    @Autowired
    public void setDbSave(DbSaveable dbSave) {
        this.dbSave = dbSave;
    }
    @Autowired
    public void setRules(List<Checkable> rules) { this.rules = rules; }
    @Autowired
    public void setErrorLog(ErrorLogable errorLog) { this.errorLog = errorLog; }

    public void start(){
        // считаем все строки из всех файлов
        fileScanner.runScanFolder("C:\\Users\\AFakhretdinov\\Documents\\courses2\\zad4\\files");

        // запустим промежуточные проверки с помощью соответствующих компонент
        check();

        // добавим данные в БД используя компоненту сохранения
        dbSave.saveToBD("jdbc:h2:C:\\Users\\AFakhretdinov\\Documents\\courses2\\zad4\\db\\default");
    }

    public void check(){
        for (Files f : fileScanner.getFiles()) {
            for (FileLines cell : f.getFileLines()) {
                for (Checkable ch : rules) {
                    ch.check(cell, f.getNameFile(), errorLog);
                }
            }
        }
    }
}
