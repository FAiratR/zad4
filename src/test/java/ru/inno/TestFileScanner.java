package ru.inno;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartProject.class) // нужна для того, чтобы тест знал о реализованном Spring-приложении
public class TestFileScanner {
    @Autowired
    private StartProject startProject;
    @Autowired
    private FileScannerable fileScanner;
    @Autowired
    private List<Checkable> rules;
    @Autowired
    private ErrorLogable errorLog;
    @Autowired
    private DbSaveable dbSave;
    public final String URL = "jdbc:h2:C:\\Users\\AFakhretdinov\\Documents\\courses2\\zad4\\db\\default";

    @Test
    // проверим, что @SpringBootTest работает, и объекты инициализируются
    public void test1() {
        Assertions.assertNotEquals(startProject,null);
    }

    void createTestFile() {
        try {
            File file = new File("C:/Temp/test2.csv/");
            file.delete();
            Files.write(Path.of("C:/Temp/test2.csv/"),
                    ("login,fam,name,otch,2024-01-01,web" + "\n").getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void test2() {
        // создадим файл с определенными записями
        // потом считаем его и проверим
        createTestFile();
        fileScanner.runScanFolder("C:\\Temp\\test2.csv");

        var tmp = fileScanner.getFiles();
        for (var file : tmp){
            Assertions.assertEquals(file.getNameFile(), "test2.csv");
            for (var fl : file.getFileLines()){
                Assertions.assertEquals(fl.getUserName(), "login");
                Assertions.assertEquals(fl.getFam(), "fam");
                Assertions.assertEquals(fl.getName(), "name");
                Assertions.assertEquals(fl.getOtch(), "otch");
                Assertions.assertEquals(fl.getAccessDate(), "2024-01-01");
                Assertions.assertEquals(fl.getTypeApp(), "web");
            }
        }
    }

    @Test
    public void test3() {
        fileScanner.getFiles().add(new ru.inno.Files("test3.csv"));
        FileLines flTmp = new FileLines("login", "fam", "name", "otch", "2024-01-01", "web");
        fileScanner.getFiles().get(0).getFileLines().add(flTmp);

        for (Checkable ch : rules) {
            ch.check(flTmp, "test3.csv", errorLog);
        }

        Assertions.assertEquals(flTmp.getAccessDate(), "2024-01-01");
        Assertions.assertEquals(flTmp.getFam(),"Fam");
        Assertions.assertEquals(flTmp.getName(),"Name");
        Assertions.assertEquals(flTmp.getOtch(),"Otch");
        Assertions.assertEquals(flTmp.getTypeApp(),"web");
    }

    @Test
    public void test4() {
        fileScanner.getFiles().add(new ru.inno.Files("test4.csv"));
        FileLines flTmp = new FileLines("login", "fam", "name", "otch", "2024-01-01", "web");
        fileScanner.getFiles().get(0).getFileLines().add(flTmp);

        dbSave.saveToBD(URL);

        try {
            Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();
            String SQL = "SELECT ID FROM USERS WHERE USERNAME='" + "login" + "' and rownum=1";
            ResultSet result = statement.executeQuery(SQL);
            while (result.next()) {
                Assertions.assertNotEquals(result.getString("ID"),null);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    @Test
    public void test5() {
        // создадим файл с определенными записями
        // потом считаем его, загрузим и проверим
        createTestFile();
        fileScanner.runScanFolder("C:\\Temp\\test2.csv");
        startProject.check();
        dbSave.saveToBD(URL);

        try {
            Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();
            String SQL = "SELECT ID FROM USERS WHERE USERNAME='" + "login" + "' and rownum=1";
            ResultSet result = statement.executeQuery(SQL);
            while (result.next()) {
                Assertions.assertNotEquals(result.getString("ID"),null);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
