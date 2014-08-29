package dao;

import com.google.inject.persist.Transactional;
import models.HumanGene;
import models.cancer.Sample;
import models.cancer.StudyCase;
import models.results.DNASeqResult;
import models.results.DNASeqResultSample;
import ninja.jpa.UnitOfWork;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import persistence.HibernateUtil;

import java.util.List;

/**
 * Created by tonywang on 6/25/14.
 */
public class DNASeqResultDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @UnitOfWork
    public List<DNASeqResult> findAllDnaSeqResults()
    {
        List<DNASeqResult> allResults = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(DNASeqResult.class);
        allResults = criteria.list();

        session.close();
        return allResults;
    }

    @Transactional
    public void saveResult(DNASeqResult dnaSeqResult)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(dnaSeqResult);

        for (DNASeqResultSample ds : dnaSeqResult.getDnaSeqResultSamples())
        {
            session.save(ds);
        }
        tx.commit();
        session.close();
    }

    @Transactional
    public void saveResultOnly(DNASeqResult dnaSeqResult)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(dnaSeqResult);

        tx.commit();
        session.close();
    }

    @UnitOfWork
    public List<DNASeqResult> getDnaSeqResultsBySample(Sample sample)
    {
        List<DNASeqResult> results = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(DNASeqResult.class);
        results = criteria.add(Restrictions.eq("sample", sample)).list();

        session.close();
        return results;
    }

    @UnitOfWork
    public List<DNASeqResult> getDnaSeqResultsByGene(HumanGene humanGene)
    {
        List<DNASeqResult> results = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(DNASeqResult.class);
        results = criteria.add(Restrictions.eq("humanGene", humanGene)).list();

        session.close();
        return results;
    }



}
