package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/newdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "23782750053";
    private  static  final  String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Connection connection = null;
    private static SessionFactory sessionFactory = null;




    public Util() { }
    public static Connection getConnection() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            System.out.println("получили драйвер");
        } catch (SQLException e) {
            System.out.println("ошибка в блоке с драйвером");
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Подключились к бд в UTIL");
        } catch (SQLException e) {
            System.out.println("Не смогли подключиться к бд  в UTIL");
            e.printStackTrace();
        }
        return connection;
    }

        public static SessionFactory getSessionFactory() {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.driver_class", DRIVER)
                        .setProperty("hibernate.connection.url", URL)
                        .setProperty("hibernate.connection.username", USERNAME)
                        .setProperty("hibernate.connection.password", PASSWORD)
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                        .addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable e) {
                e.printStackTrace();

            }
        return sessionFactory;
    }



}
