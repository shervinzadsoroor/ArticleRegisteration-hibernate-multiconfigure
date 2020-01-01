package usecases.impl;

import confighibernate.HibernateUtil;
import models.Article;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.EditArticleByWriterUseCase;

import java.util.List;
import java.util.Scanner;

public class EditArticleByWriterUseCaseImpl implements EditArticleByWriterUseCase {
    @Override
    public void edit(Long id, User user,String currentDate) {

        Scanner scanner = new Scanner(System.in);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        boolean isDone = false;
        boolean isIdExist = false;

        List<Long> idList = session.createQuery("select id from Article ")
                .list();
        for (Long articleId : idList) {
            if (id == articleId) {
                isIdExist = true;
            }
        }
        //checking the validation of the article's id
        Article article = session.load(Article.class, id);
        if (isIdExist) {
            if (article.getUser().getId() == user.getId()) {

                System.out.println("enter column's name ( title | brief | content ):");
                String columnName = scanner.nextLine();

                if (columnName.equalsIgnoreCase("title")) {
                    System.out.println("enter new title: ");
                    String newTitle = scanner.nextLine();
                    article.setTitle(newTitle);
                    isDone = true;

                } else if (columnName.equalsIgnoreCase("brief")) {
                    System.out.println("enter new brief: ");
                    String newBrief = scanner.nextLine();
                    article.setBrief(newBrief);
                    isDone = true;
                } else if (columnName.equalsIgnoreCase("content")) {
                    System.out.println("enter new content: ");
                    String newContent = scanner.nextLine();
                    article.setContent(newContent);
                    isDone = true;
                } else {
                    System.out.println("invalid column name !!!");
                }
                if (isDone) {
                    article.setLastUpdateDate(currentDate);
                    session.update(article);
                }
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
