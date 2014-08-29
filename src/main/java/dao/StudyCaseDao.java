package dao;

import com.google.inject.persist.Transactional;
import models.cancer.Sample;
import models.cancer.StudyCase;
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
public class StudyCaseDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @UnitOfWork
    public List<StudyCase> findAllStudyCases()
    {
        List<StudyCase> allStudies = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(StudyCase.class);
        allStudies = criteria.list();

        session.close();
        return allStudies;
    }

    @Transactional
    public void saveStudyCase(StudyCase studyCase)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(studyCase);


        for (Sample s : studyCase.getSamples())  // save individual samples in the studyCase
        {
            session.save(s);
        }

        tx.commit();
        session.close();
    }

    @UnitOfWork
    public StudyCase getStudyCaseById(Long studyId)
    {
        StudyCase studyCase = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(StudyCase.class);
        studyCase = (StudyCase) criteria.add(Restrictions.eq("studyId", studyId)).uniqueResult();

        session.close();
        return studyCase;
    }

    @UnitOfWork
    public StudyCase getStudyCaseByName(String studyName)
    {
        StudyCase studyCase = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(StudyCase.class);
        studyCase = (StudyCase) criteria.add(Restrictions.eq("studyName", studyName)).uniqueResult();

        session.close();
        return studyCase;
    }



}
