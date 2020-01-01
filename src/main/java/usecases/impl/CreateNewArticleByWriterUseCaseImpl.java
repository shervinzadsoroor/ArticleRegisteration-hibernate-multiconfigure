package usecases.impl;

import confighibernate.HibernateUtil;
import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import usecases.usecase.CreateNewArticleByWriterUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateNewArticleByWriterUseCaseImpl implements CreateNewArticleByWriterUseCase {
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

        //===============================================================

        //shows all tags to the writer
        Query<Tag> tagQuery = session.createQuery("from Tag ");
        List<Tag> tagList = tagQuery.list();
        for (Tag tag : tagList) {
            System.out.println(tag.toString());
        }
        //end of show

        //validating the tag id ==========================
        boolean isTagExist = false;
        System.out.println("enter tag id:");
        Long tagId = null;
        while (!isTagExist) {
            tagId = scannerLong.nextLong();
            List<Long> tagIds = session.createQuery("select id from Tag ")
                    .list();
            for (Long L : tagIds) {
                if (L == tagId) {
                    isTagExist = true;
                }
            }
            if (!isTagExist) {
                System.out.println("tag not found, enter an existing tag id: ");
            }
        }
        //================================================

        //defines the article's tag
        Tag tag = session.load(Tag.class, tagId); // returns the tag
        List<Tag> tags = new ArrayList<>(); // we use list because the user can have many roles
        tags.add(tag);

        article.setTags(tags);


        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
}
