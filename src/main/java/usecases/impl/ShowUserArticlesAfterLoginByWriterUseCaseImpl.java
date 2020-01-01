package usecases.impl;

import confighibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.ShowUserArticlesAfterLoginByWriterUseCase;

import java.util.List;

public class ShowUserArticlesAfterLoginByWriterUseCaseImpl implements ShowUserArticlesAfterLoginByWriterUseCase {
    @Override
    public void show(Long id) { SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        List list = session.createQuery("from Article where user.id=:id")
                .setParameter("id", id)
                .list();
        if (list.size() > 0) {
            System.out.println("\n" +
                    " Article\n==========================================");
            for (Object article : list) {
                System.out.println(article.toString());
            }
            System.out.println("==========================================");
        } else {
            System.out.println("NO ARTICLES FOUND");
        }

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
}
