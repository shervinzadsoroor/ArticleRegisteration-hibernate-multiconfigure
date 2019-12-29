package usecases.impl;

import confighibernate.HibernateUtil;
import models.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import usecases.usecase.ShowAllArticlesUseCase;

import java.util.List;

public class ShowAllArticlesUseCaseImpl implements ShowAllArticlesUseCase {
    @Override
    public void show() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        Query query = session.createQuery("from Article ");
        List<Article> articleList = query.list();
        System.out.printf("\n%-5s%-20s%-30s\n%s\n", "id", "title", "brief",
                "==================================================================");
        for (Article article : articleList) {
            article.printBrief();
        }
        System.out.println("==================================================================");

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
}
