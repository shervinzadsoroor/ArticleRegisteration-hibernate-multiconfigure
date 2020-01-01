package usecases.impl;

import confighibernate.HibernateUtil;
import models.Article;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.PublishArticleByAdminUseCase;

import java.util.List;

public class PublishArticleByAdminUseCaseImpl implements PublishArticleByAdminUseCase {
    @Override
    public void publish(Long id, String currentDate) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================
        boolean isIdExist = false;

        List<Long> idList = session.createQuery("select id from Article ")
                .list();
        for (Long articleId : idList) {
            if (id == articleId) {
                isIdExist = true;
            }
        }
        Article article = session.load(Article.class, id);
        if (isIdExist) {
            article.setPublishDate(currentDate);
            article.setLastUpdateDate(currentDate);
            article.setPublished("yes");

            session.update(article);
        } else {
            System.out.println("ID NOT FOUND !!!");
        }
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
}
