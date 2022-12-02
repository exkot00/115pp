package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();
        userDao.cleanUsersTable();
        userDao.saveUser("Name1", "LastName1", (byte) 20);
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        userDao.saveUser("Name3", "LastName3", (byte) 31);
        userDao.saveUser("Name4", "LastName4", (byte) 38);

        System.out.println(userDao.getAllUsers().toString());

        System.out.println("удаляем юзера Name 1");
        userDao.removeUserById(1);
        System.out.println(userDao.getAllUsers().toString());

        System.out.println("удаляем таблицу");
        userDao.dropUsersTable();


    }
}
