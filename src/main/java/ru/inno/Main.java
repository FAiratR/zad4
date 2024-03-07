package ru.inno;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main(String[] args) throws ClassNotFoundException {
        /*FileScanner fileScanner = new FileScanner();
        List<Files> l = fileScanner.runScanFolder("C:\\Users\\AFakhretdinov\\Documents\\courses2\\zad4\\files");
        for (Files f : l) {
            System.out.println(f.getNameFile());
            for (FileLines fl : f.getFileLines()){
                System.out.println(fl.toString());
            }
        }

        DbSave dbSave = new DbSave();
        for (Users u: dbSave.getUserList())
            System.out.println(u.getId() + ", " + u.getUsername() + ", " + u.getFio());*/
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("ru.inno");
        StartProject startProject = ctx.getBean(StartProject.class);
        startProject.start();
        ctx.close();

    }
}
