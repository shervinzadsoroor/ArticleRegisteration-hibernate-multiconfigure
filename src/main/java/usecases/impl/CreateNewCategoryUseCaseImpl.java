package usecases.impl;

import confighibernate.HibernateUtil;
import models.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.CreateNewCategoryUseCase;

import java.util.Scanner;

public class CreateNewCategoryUseCaseImpl implements CreateNewCategoryUseCase {
    @Override
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
}
