package dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import user.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by ANYA on 12.11.2016.
 */
public class UserDAO extends AbstractDAO<User> {

    @Override
    public List<User> findAll() throws SQLException {
        List<User> list = null;
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            list = (List<User>) session.createQuery("FROM User").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("error on query findAll");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public User findById(Integer id) throws SQLException {
        Session session = null;
        Transaction tx = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            user = (User) session.get(User.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("error on query findAll");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public User findByField(Object name) throws SQLException {
        if (name != null) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                tx = session.beginTransaction();
                Query query = session.createQuery("FROM User where login=:name");
                query.setString("name", (String) name);
                List list = query.list();
                tx.commit();
                if (!list.isEmpty())
                    return (User) list.get(0);
                else return null;
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                System.out.println("error on query findAll");
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return null;
    }

    @Override
    public void updateFieldById(Integer id, Object newPassword) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user;
        try {
            tx = session.beginTransaction();

            user = (User) session.get(User.class, id);
            if (user != null) {
                user.setPassword((String) newPassword);
                session.update(user);
                tx.commit();
            }
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Integer insert(User entity) throws SQLException {
        Session session = null;
        Transaction tx = null;
        Integer employeeID = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            employeeID = (Integer) session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("error on query insert");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return employeeID;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user != null)
                session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("error on query deleteById");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
