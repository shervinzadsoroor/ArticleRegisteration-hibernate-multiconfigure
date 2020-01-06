package repositories.impl;

import confighibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repositories.interfaces.SpecifyExistanceOfUserArticleUseCase;

import java.util.List;

public class SpecifyExistanceOfUserArticleUseCaseImpl implements SpecifyExistanceOfUserArticleUseCase {
    @Override
    public boolean specify(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================
        boolean bool = false;

        List list = session.createQuery("from Article where user.id=:id")
                .setParameter("id", id)
                .list();
        if (list.size() > 0) {
            bool = true;
        } else {
            System.out.println("NO ARTICLES FOUND TO EDIT !!!");
        }

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
        return bool;
    }
}
