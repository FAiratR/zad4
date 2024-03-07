package ru.inno;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class Main
{
    public static void main(String[] args) throws ClassNotFoundException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("ru.inno");
        StartProject startProject = ctx.getBean(StartProject.class);
        startProject.start();
        ctx.close();
    }
}
