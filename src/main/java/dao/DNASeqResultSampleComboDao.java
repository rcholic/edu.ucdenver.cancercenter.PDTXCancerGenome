package dao;

import com.google.inject.persist.Transactional;
import models.HumanGene;
import models.cancer.Sample;
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
 * Created by tonywang on 6/26/14.
 */
public class DNASeqResultSampleComboDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @UnitOfWork
    public List<DNASeqResultSample> findAllDnaSeqResults()
    {
        List<DNASeqResultSample> allResultsSamples = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(DNASeqResultSample.class);
        allResultsSamples = criteria.list();

        session.close();
        return allResultsSamples;
    }

    @UnitOfWork
    public List<DNASeqResultSample> getDnaSeqResultsSampleComboBySample(Sample sample)
    {
        List<DNASeqResultSample> results = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(DNASeqResultSample.class);
        results = criteria.add(Restrictions.eq("sample", sample)).list();

        session.close();
        return results;
    }

    @UnitOfWork
    public List<DNASeqResultSample> getDnaSeqResultSampleComboByGene(HumanGene humanGene)
    {
        List<DNASeqResultSample> results = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(DNASeqResultSample.class);
        results = criteria.add(Restrictions.eq("humanGene", humanGene)).list();

        session.close();
        return results;
    }

    @Transactional
    public void saveDnaSeqResultCombo(DNASeqResultSample combo)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

    //    if (combo.getDnaSeqResult() != null && combo.getDnaSeqResult().getDnaSeqResultId() == null)
    //        session.save(combo.getDnaSeqResult());

        session.save(combo);
        tx.commit();
        session.close();
    }

}
