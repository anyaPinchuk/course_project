package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import session.Ticket;
import user.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ANYA on 14.11.2016.
 */
public class TicketDAO extends AbstractDAO<Ticket> {
    @Override
    public List<Ticket> findAll() throws SQLException {
        List<Ticket> list = null;
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            list = (List<Ticket>) session.createQuery("FROM Ticket ").list();
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
    public Ticket findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public Ticket findByField(Object login) throws SQLException {
        return null;
    }

    @Override
    public void updateFieldById(Integer id, Object field) throws SQLException {

    }

    @Override
    public Integer insert(Ticket entity) throws SQLException {
        return null;
    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }
}
