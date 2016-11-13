package dao;

import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by ANYA on 12.11.2016.
 */
public abstract class AbstractDAO<T> {

    public abstract List<T> findAll() throws SQLException;

    public abstract T findById(Integer id) throws SQLException;

    public abstract T findByField(Object login) throws SQLException;

    public abstract Integer updateById(Integer id, T entity) throws SQLException;

    public abstract Integer insert(T entity) throws SQLException;

    public abstract Integer deleteById(Integer id) throws SQLException;
}