package dao;

import com.google.inject.persist.Transactional;
import models.User;
import ninja.jpa.UnitOfWork;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import persistence.HibernateUtil;

import java.util.List;

/**
 * Created by tonywang on 10/14/14.
 */
public class UserDao
{
    private final static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public UserDao() {}

    @UnitOfWork
    public List<User> findAllUsers()
    {
        List<User> users = null;

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        users = (List<User>) criteria.list();
        session.close();

        return users;
    }

    @Transactional
    public void saveUser(User user)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(user);

        tx.commit();
        session.close();
    }

    @UnitOfWork
    public User getUserByEmail(String email)
    {
        User user = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));

        user = (User) criteria.uniqueResult();
        session.close();

        return user;
    }

    @Transactional
    public void deleteUserByEmail(String email)
    {
        Session session = sessionFactory.openSession();
        User user = getUserByEmail(email);
        if (user != null)
        {
            Transaction tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        }
        session.close();
    }
}
