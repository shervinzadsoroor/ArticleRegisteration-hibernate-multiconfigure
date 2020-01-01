package usecases.impl;

import confighibernate.HibernateUtil;
import models.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.UnPublishArticleByAdminUseCase;

import java.util.List;

public class UnPublishArticleByAdminUseCaseImpl implements UnPublishArticleByAdminUseCase {
    @Override
    public void unPublish(Long id, String currentDate) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //----------------------------------------------------

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
            article.setPublishDate(null);
            article.setLastUpdateDate(currentDate);
            article.setPublished("no");

            session.update(article);
        } else {
            System.out.println("ID NOT FOUND !!!");
        }

        //----------------------------------------------------
        session.getTransaction().commit();
        session.close();
    }
}
