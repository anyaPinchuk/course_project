package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.FilmSession;
import user.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ANYA on 14.11.2016.
 */
public class FilmSessionDAO extends AbstractDAO<FilmSession> {
    @Override
    public List<FilmSession> findAll() throws SQLException {
        List<FilmSession> list = null;
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            list = (List<FilmSession>) session.createQuery("FROM FilmSession ").list();
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
    public FilmSession findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public FilmSession findByField(Object login) throws SQLException {
        return null;
    }

    @Override
    public void updateFieldById(Integer id, Object field) throws SQLException {

    }

    @Override
    public Integer insert(FilmSession entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }
}

