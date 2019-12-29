package usecases.impl;

import confighibernate.HibernateUtil;
import models.Article;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.DeleteArticleUseCase;

import java.util.List;

public class DeleteArticleUseCaseImpl implements DeleteArticleUseCase {
    @Override
    public void delete(Long id, User user) {

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
            if (article.getUser().getId() == user.getId()) {
                session.remove(article);
            } else {
                System.out.println("THE ARTICLE IS NOT YOURS !!!");
            }
        } else {
            System.out.println("ID NOT FOUND !!!");
        }

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
}
