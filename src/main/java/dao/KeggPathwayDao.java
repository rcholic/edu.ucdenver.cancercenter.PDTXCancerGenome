package dao;

import com.google.inject.persist.Transactional;
import models.HumanGene;
import models.KeggPathway;
import ninja.jpa.UnitOfWork;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

import java.util.List;
import java.util.Set;

/**
 * Created by tonywang on 6/23/14.
 */
public class KeggPathwayDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public KeggPathwayDao() {}

    @UnitOfWork
    public List<KeggPathway> findAllPathways()
    {
        List<KeggPathway> pathways = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(KeggPathway.class);

        pathways = (List<KeggPathway>) criteria.list();

        session.close();

        return pathways;
    }

    @Transactional
    public void savePathwayWithHumanGenes(KeggPathway keggPathway, Set<HumanGene> genes)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(keggPathway);

        for (HumanGene gene : genes)
        {
            session.save(gene);
        }

        tx.commit();;
        session.close();
    }
}
