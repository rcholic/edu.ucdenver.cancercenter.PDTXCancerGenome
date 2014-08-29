package dao;

import com.google.inject.persist.Transactional;
import models.HumanGene;
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
public class HumanGeneDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @UnitOfWork
    public List<HumanGene> findAllHumanGenes()
    {
        List<HumanGene> allGenes = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(HumanGene.class);
        allGenes = criteria.list();

        session.close();

        return allGenes;
    }

    @UnitOfWork
    public HumanGene getGeneById(int geneId)
    {
        HumanGene gene = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(HumanGene.class);
        criteria.add(Restrictions.eq("geneId", geneId));
        gene = (HumanGene) criteria.uniqueResult();

        session.close();
        return gene;
    }

    @UnitOfWork
    public HumanGene getGeneBySymbol(String geneSymbol)
    {
        if (geneSymbol == null)
            return null;

        HumanGene gene = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(HumanGene.class);
        criteria.add(Restrictions.eq("geneSymbol", geneSymbol));
        gene = (HumanGene) criteria.uniqueResult();

        session.close();
        return gene;
    }

    @UnitOfWork
    public HumanGene getGeneByEntrezId(String entrezGeneId)
    {
        if (entrezGeneId == null)
            return null;

        HumanGene gene = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(HumanGene.class);
        criteria.add(Restrictions.eq("entrezGeneId", entrezGeneId));
        gene = (HumanGene) criteria.uniqueResult();

        session.close();
        return gene;
    }

    @Transactional
    public void saveGene(HumanGene humanGene)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(humanGene);

        tx.commit();
        session.close();
    }
}
