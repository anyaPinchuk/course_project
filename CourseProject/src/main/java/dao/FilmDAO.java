package dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.Film;
import user.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ANYA on 14.11.2016.
 */
public class FilmDAO extends AbstractDAO<Film> {
    @Override
    public List<Film> findAll() throws SQLException {
        List<Film> list = null;
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            list = (List<Film>) session.createQuery("FROM Film ").list();
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
    public Film findById(Integer id) throws SQLException {
        Session session = null;
        Transaction tx = null;
        Film film = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            film = (Film) session.get(Film.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            System.out.println("error on query findAll");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return film;
    }

    @Override
    public Film findByField(Object filmName) throws SQLException {
        if (filmName != null) {
            Session session = null;
            Transaction tx = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                tx = session.beginTransaction();
                Query query = session.createQuery("FROM Film where filmName=:name");
                query.setString("name", (String) filmName);
                List list = query.list();
                tx.commit();
                if (!list.isEmpty())
                    return (Film) list.get(0);
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
    public void updateFieldById(Integer id, Object field) throws SQLException {

    }

    @Override
    public Integer insert(Film entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }
}
