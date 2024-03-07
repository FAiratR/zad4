package ru.inno;

// класс для считанных и распарсенных из файла строк

public class FileLines {
    private String userName;
    private String fam;
    private String name;
    private String otch;
    private String accessDate;
    private String typeApp;
    public FileLines(String userName, String fam, String name, String otch, String accessDate, String typeApp) {
        this.userName = userName;
        this.fam = fam;
        this.name = name;
        this.otch = otch;
        this.accessDate = accessDate;
        this.typeApp = typeApp;
    }
    public String getUserName() {
        return userName;
    }
    public String getFam() {
        return fam;
    }
    public String getName() {
        return name;
    }
    public String getOtch() {
        return otch;
    }
    public String getAccessDate() {
        return accessDate;
    }
    public String getTypeApp() {
        return typeApp;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOtch(String otch) {
        this.otch = otch;
    }

    public void setAccessDate(String accessDate) {
        this.accessDate = accessDate;
    }

    public void setTypeApp(String typeApp) {
        this.typeApp = typeApp;
    }

    @Override
    public String toString() {
        return "FileLines{" +
                "userName='" + userName + '\'' +
                ", fam='" + fam + '\'' +
                ", name='" + name + '\'' +
                ", otch='" + otch + '\'' +
                ", accessDate='" + accessDate + '\'' +
                ", typeApp='" + typeApp + '\'' +
                '}';
    }
    public void clear(){
        this.userName = "null";
        this.fam = "null";
        this.name = "null";
        this.otch = "null";
        this.accessDate = "null";
        this.typeApp = "null";
    }
}
