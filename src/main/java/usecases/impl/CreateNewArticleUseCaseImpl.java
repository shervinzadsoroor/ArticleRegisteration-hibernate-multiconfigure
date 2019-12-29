package usecases.impl;

import confighibernate.HibernateUtil;
import models.Article;
import models.Category;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.CreateNewArticleUseCase;

import java.util.List;
import java.util.Scanner;

public class CreateNewArticleUseCaseImpl implements CreateNewArticleUseCase {
    @Override
    public void create(User user, String currentDate) {

        Scanner scanner = new Scanner(System.in);
        Scanner scannerLong = new Scanner(System.in);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        boolean isCategoryExist = false;

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        System.out.println("category id: ");
        Long category_id = null;

        // validating the category id ====================================
        while (!isCategoryExist) {
            category_id = scannerLong.nextLong();
            List categoryIds = session.createQuery("select id from Category ")
                    .list();
            for (Object obj : categoryIds) {
                if (obj == category_id) {
                    isCategoryExist = true;
                    break;
                }
            }
            if (!isCategoryExist) {
                System.out.println("category not found, enter an existing category id: ");
            }
        }

        //end of validating ==============================================
        System.out.println("article title: ");
        String title = scanner.nextLine();
        System.out.println("article brief: ");
        String brief = scanner.nextLine();
        System.out.println("article content: ");
        String content = scanner.nextLine();
        String createDate = currentDate;
        String isPublished = "no";
        String lastUpdateDate = currentDate;

        List<Category> categoryList = session.createQuery("from Category where id= :id")
                .setParameter("id", category_id)
                .list();
        Category category = categoryList.get(0);

        Article article = new Article(title, brief, content, createDate, lastUpdateDate,
                null, isPublished, user, category);

        session.save(article); // insert into article


        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
}
