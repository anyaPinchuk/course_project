package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.Place;
import user.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ANYA on 14.11.2016.
 */
public class PlaceDAO extends AbstractDAO<Place> {
    @Override
    public List<Place> findAll() throws SQLException {
        List<Place> list = null;
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            list = (List<Place>) session.createQuery("FROM Place ").list();
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
    public Place findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public Place findByField(Object login) throws SQLException {
        return null;
    }

    @Override
    public void updateFieldById(Integer id, Object field) throws SQLException {

    }

    @Override
    public Integer insert(Place entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }
}
