package dao;

import com.google.inject.persist.Transactional;
import models.cancer.Sample;
import models.cancer.StudyCase;
import models.institutions.Laboratory;
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
public class LaboratoryDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @UnitOfWork
    public List<StudyCase> findAllLabs()
    {
        List<StudyCase> allLabs = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Laboratory.class);
        allLabs = criteria.list();

        session.close();
        return allLabs;
    }

    @Transactional
    public void saveLab(Laboratory laboratory)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(laboratory);


        for (StudyCase studyCase : laboratory.getStudyCases())  // save individual samples in the studyCase
        {
            session.save(studyCase);
        }

        tx.commit();
        session.close();
    }

    @UnitOfWork
    public Laboratory getLaboratoryById(Long laboratoryId)
    {
        Laboratory lab = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Laboratory.class);
        lab = (Laboratory) criteria.add(Restrictions.eq("labId", laboratoryId)).uniqueResult();

        session.close();
        return lab;
    }
}
