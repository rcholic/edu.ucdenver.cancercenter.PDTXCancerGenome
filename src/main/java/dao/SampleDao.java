package dao;

import com.google.inject.persist.Transactional;
import models.cancer.Sample;
import models.cancer.StudyCase;
import models.results.DNASeqResult;
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
public class SampleDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @UnitOfWork
    public List<Sample> findAllSamples()
    {
        List<Sample> allSamples = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Sample.class);
        allSamples = criteria.list();

        session.close();
        return allSamples;
    }

    @Transactional
    public void saveSample(Sample sample)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(sample);

        /*
        for (DNASeqResult result : sample.getDnaSeqResultSamples())
        {
            session.save(result);
        }
        */
        tx.commit();
        session.close();
    }

    @Transactional
    public Sample getSampleById(Long sampleId)
    {
        Sample sample = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Sample.class);
        sample = (Sample) criteria.add(Restrictions.eq("sampleId", sampleId)).uniqueResult();

        session.close();
        return sample;
    }

    @Transactional
    public Sample getSampleByName(String sampleName) {
        Sample sample = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Sample.class);
        sample = (Sample) criteria.add(Restrictions.eq("sampleName", sampleName)).uniqueResult();

        session.close();
        return sample;
    }

    @Transactional
    public List<Sample> getSamplesByName(String sampleName) {
        List<Sample> samples = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Sample.class);
        samples = (List<Sample>) criteria.add(Restrictions.eq("sampleName", sampleName)).list();

        session.close();
        return samples;
    }

    @Transactional
    public List<Sample> getSamplesByStudyCase(StudyCase studyCase) {
        List<Sample> samples = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Sample.class);
        samples = (List<Sample>) criteria.add(Restrictions.eq("studyCase", studyCase)).list();

        session.close();
        return samples;
    }


}
