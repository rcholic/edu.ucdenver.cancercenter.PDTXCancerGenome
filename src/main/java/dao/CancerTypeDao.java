package dao;

import com.google.inject.persist.Transactional;
import models.HumanGene;
import models.KeggPathway;
import models.cancer.CancerType;
import ninja.jpa.UnitOfWork;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import persistence.HibernateUtil;

import java.util.List;
import java.util.Set;

/**
 * Created by tonywang on 6/24/14.
 */
public class CancerTypeDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public CancerTypeDao() {}

    @UnitOfWork
    public List<CancerType> findAllCancerTypes()
    {
        List<CancerType> types = null;

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CancerType.class);
        types = (List<CancerType>) criteria.list();
        session.close();

        return types;
    }

    @Transactional
    public void saveCancerType(CancerType cancerType)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(cancerType);

        tx.commit();
        session.close();
    }

    @UnitOfWork
    public CancerType getCancerTypeById(int id)
    {
        CancerType cancerType = null;

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CancerType.class);
        criteria.add(Restrictions.eq("cancerTypeId", id));

        cancerType = (CancerType) criteria.uniqueResult();
        session.close();

        return cancerType;
    }

    @UnitOfWork
    public CancerType getCancerTypeByName(String name)
    {
        CancerType cancerType = null;

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CancerType.class);
        criteria.add(Restrictions.eq("name", name));

        cancerType = (CancerType) criteria.uniqueResult();
        session.close();

        return cancerType;
    }

    @UnitOfWork
    public List<CancerType> getCancerTypesByName(String name)
    {
        List<CancerType> cancerTypes = null;

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CancerType.class);
        criteria.add(Restrictions.eq("name", name));

        cancerTypes = (List<CancerType>) criteria.list();
        session.close();

        return cancerTypes;
    }
}
