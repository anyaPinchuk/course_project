package dao;

import org.hibernate.Query;
import org.hibernate.Session;
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
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            list = (List<User>) session.createQuery("FROM User").list();
        } catch (Exception e) {
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
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = (User) session.get(User.class, id);
        } catch (Exception e) {
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
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("FROM User where login=:name");
                query.setString("name", (String) name);
                List list = query.list();
                if (!list.isEmpty())
                    return (User) list.get(0);
                else return null;
            } catch (Exception e) {
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
    public Integer updateById(Integer id, User entity) throws SQLException {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = (User) session.get(User.class, id);
            if (user != null) {
                user.setPassword(entity.getPassword());
                user.setAdmin(entity.isAdmin());
                session.update(user);
            }
        } catch (Exception e) {
            System.out.println("error on query findAll");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public Integer insert(User entity) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.save(entity);
        } catch (Exception e) {
            System.out.println("error on query insert");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return entity.getId();
    }

    @Override
    public Integer deleteById(Integer id) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            User user = (User) session.get(User.class, id);
            if (user != null)
                session.delete(user);
        } catch (Exception e) {
            System.out.println("error on query deleteById");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
