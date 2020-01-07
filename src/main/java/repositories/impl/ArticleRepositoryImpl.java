package repositories.impl;

import confighibernate.HibernateUtil;
import models.Article;
import models.Category;
import models.Tag;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repositories.interfaces.ArticleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleRepositoryImpl implements ArticleRepository {

    public Article create(User user, String currentDate) {
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
        return article;
    }

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

    public void delete(Long id) {

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
            session.remove(article);
        } else {
            System.out.println("ID NOT FOUND !!!");
        }

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

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

    public void showAll() {
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

    public void showUserArticlesAfterLogin(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

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

    public void showSpecificArticle(Long id) {
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

    public Long countArticlesOfUser(User user) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        Query query = session.createQuery("select count(id) from Article  where user.id=:id")
                .setParameter("id", user.getId());
        Long count = (Long) query.uniqueResult();

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
        return count;
    }

    public void searchByTitle(String title) {

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

    public Long countPublishedArticle(User user) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        Query query = session.createQuery("select count(id) from Article  where user.id=:id and isPublished='yes'")
                .setParameter("id", user.getId());
        Long count = (Long) query.uniqueResult();

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
        return count;
    }

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
