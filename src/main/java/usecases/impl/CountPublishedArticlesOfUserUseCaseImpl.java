package usecases.impl;

import confighibernate.HibernateUtil;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import usecases.usecase.CountPublishedArticlesOfUserUseCase;

public class CountPublishedArticlesOfUserUseCaseImpl implements CountPublishedArticlesOfUserUseCase {
    @Override
    public Long count(User user) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        Query query = session.createQuery("select count(id) from Article  where user.id=:id and isPublished='yes'")
                .setParameter("id", user.getId());
        Long count = (Long) query.uniqueResult();

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
        return count;
    }
}
