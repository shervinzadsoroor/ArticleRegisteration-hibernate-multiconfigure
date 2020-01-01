package usecases.impl;

import confighibernate.HibernateUtil;
import models.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.SearchTitleByAdminUseCase;

import java.util.List;

public class SearchTitleByAdminUseCaseImpl implements SearchTitleByAdminUseCase {
    @Override
    public void search(String title) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================
        boolean isTitleExist = false;

        List articles1 = session.createQuery("select title from Article")
                .list();
        for (Object obj : articles1) {
            if (obj.equals(title)) {
                isTitleExist = true;
            }
        }
        if (isTitleExist) {
            List<Article> articles2 = session.createQuery("from Article where title=:titleName")
                    .setParameter("titleName", title)
                    .list();

            for (Article article : articles2) {
                System.out.println(article.toString());
            }
        } else {
            System.out.println("TITLE NOT FOUND !!!");
        }

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
}
