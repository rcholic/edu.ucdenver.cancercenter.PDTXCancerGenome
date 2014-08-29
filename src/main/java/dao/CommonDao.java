package dao;

import com.google.inject.persist.Transactional;
import models.cancer.CancerType;
import ninja.jpa.UnitOfWork;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by tonywang on 6/24/14.
 */
public class CommonDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public CommonDao() {}

    @UnitOfWork
    public List findAll(Object className)
    {
        List types = null;

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(className + ".class");
        types = (List <Object>) criteria.list();
        session.close();

        return types;
    }

    @Transactional
    public void saveObject(Object obj)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(obj);

        tx.commit();
        session.close();
    }

    @Transactional
    public void saveObjectWithManyEntities(Object obj, Set<Object> objects)   /* for OneToMany relationships */
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(obj);

        for (Object o : objects)
        {
            session.save(o);
        }

        tx.commit();
        session.close();
    }
}
