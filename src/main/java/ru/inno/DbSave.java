package ru.inno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbSave implements DbSaveable{
    private Connection connection;
    private FileScannerable fileScanner;

    @Autowired
    public DbSave(FileScannerable fileScanner) {
        this.fileScanner = fileScanner;
    }

    String selectUsers(FileLines fileLines){
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT ID FROM USERS WHERE USERNAME='" + fileLines.getUserName() + "' and rownum=1";
            ResultSet result = statement.executeQuery(SQL);
            while (result.next()) {
                return result.getString("ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return "";
    }

    void insertUsers(FileLines fileLines){
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO Users(USERNAME, FIO) VALUES('" + fileLines.getUserName() + "', '" + fileLines.getFam() + " " + fileLines.getName() + " " + fileLines.getOtch() + "');";
            int resultIns = statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    void insertLogins(FileLines fileLines, String idUser) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO Logins(ACCESS_DATE, USER_ID, APPLICATION) VALUES('" + fileLines.getAccessDate() + "', " + idUser+ ", '" + fileLines.getTypeApp() + "');";
            int resultIns = statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    public void saveToBD(String url) {
        try {
            connection = DriverManager.getConnection(url);
            for (var f : fileScanner.getFiles()){
                for (var cell : f.getFileLines()) {
                    if (!cell.getUserName().equals("null")) {
                        // ищем пользователя
                        String idUser = selectUsers(cell);
                        // если не нашли, вставляем и получаем его айди
                        if (idUser.equals(""))
                            {
                                insertUsers(cell);
                                idUser = selectUsers(cell);
                            }
                        // Тут надо вставить в таблицу Logins
                        insertLogins(cell, idUser);
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }
    public List<Users> getUserList(String url){
        List<Users> usersList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setFio(resultSet.getString("fio"));
                usersList.add(user);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

}
