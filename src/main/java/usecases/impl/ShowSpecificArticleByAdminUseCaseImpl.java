package usecases.impl;

import confighibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.ShowSpecificArticleByAdminUseCase;

import java.util.List;

public class ShowSpecificArticleByAdminUseCaseImpl implements ShowSpecificArticleByAdminUseCase {
    @Override
    public void show(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        List list = session.createQuery("from Article where id=:id")
                .setParameter("id", id)
                .list();
        if (list.size() > 0) {
            System.out.println("\n" +
                    " Article\n==========================================");

            System.out.println(list.get(0).toString());

            System.out.println("==========================================");
        } else {
            System.out.println("id not found !!!");
        }
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
}
