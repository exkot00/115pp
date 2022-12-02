package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction;
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try ( Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS newtable (" +
                            "id BIGINT NOT NULL AUTO_INCREMENT, " +
                            "name VARCHAR(255), " +
                            "lastname VARCHAR(255), " +
                            "age INT, " +
                            "PRIMARY KEY (id))" ).executeUpdate();
            System.out.println("создана табл");
        } catch (SessionException e) {
            System.out.println("Ошибка создании таблицы");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS newtable").executeUpdate();
            transaction.commit();
            System.out.println("таблица удалена");
        } catch (SessionException e) {
            System.out.println("Ошибка удаления таблицы");
        //    e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
        System.out.println("Юзер с именем " + name +" добавлен");
        } catch (SessionException e) {
            System.out.println("Ошибка добавления юзера");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("удалили юзера с id= " +id);
        } catch (SessionException e){
            System.out.println("ошибка удаления юзера");
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            users = session.createQuery(criteriaQuery).list();
            transaction.commit();
            System.out.println("таблица юзеров получена");
        } catch (SessionException e) {
            //  e.printStackTrace();
            System.out.println("Ошибка получения списка юзеров");
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE newtable").executeUpdate();
            transaction.commit();
            System.out.println("таблица очищена");
        } catch (SessionException e) {
            System.out.println("Ошибка очистки таблицы");
            //e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
