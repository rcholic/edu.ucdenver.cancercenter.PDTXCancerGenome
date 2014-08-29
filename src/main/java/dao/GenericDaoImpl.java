package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import persistence.HibernateUtil;

import java.io.Serializable;

/**
 * Created by tonywang on 6/25/14.
 */
public class GenericDaoImpl <T, PK extends Serializable> implements GenericDaoInterface<T, PK>, Serializable
{
    private Class<T> type;
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public GenericDaoImpl(Class<T> type)
    {
        this.type = type;
    }

    @Override
    public PK create(T obj)
    {
        Session session = sessionFactory.openSession();
        return (PK) session.save(obj);
    }

    @Override
    public T read(PK id)
    {
        Session session = sessionFactory.openSession();
        return (T) session.get(type, id);
    }

    @Override
    public void update(T transientObject)
    {
        Session session = sessionFactory.openSession();
        session.update(transientObject);
        session.close();
    }

    @Override
    public void delete(T persistedObject)
    {
        Session session = sessionFactory.openSession();
        session.delete(persistedObject);
        session.close();
    }
}
