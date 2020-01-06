package repositories.impl;

import confighibernate.HibernateUtil;
import models.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repositories.interfaces.CategoryRepository;

import java.util.List;
import java.util.Scanner;

public class CategoryRepositoryImpl implements CategoryRepository {
    public void create() {

        Scanner scanner = new Scanner(System.in);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        System.out.println("category title: ");
        String title = scanner.nextLine();
        System.out.println("category description: ");
        String description = scanner.nextLine();

        Category category = new Category(title, description);
        session.save(category); // insert into category

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
    public void show() {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        List list = session.createQuery("from Category")
                .list();
        System.out.println("\nCATEGORY TITLES\n==========================================");
        for (Object title : list) {

            System.out.println(title.toString());
        }
        System.out.println("==========================================");

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }}
