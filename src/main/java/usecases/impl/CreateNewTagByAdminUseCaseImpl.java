package usecases.impl;

import confighibernate.HibernateUtil;
import models.Category;
import models.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.CreateNewTagByAdminUseCase;

import java.util.Scanner;

public class CreateNewTagByAdminUseCaseImpl implements CreateNewTagByAdminUseCase {
    @Override
    public void create() {

        Scanner scanner = new Scanner(System.in);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        System.out.println("tag title: ");
        String title = scanner.nextLine();

        Tag tag = new Tag(title);
        session.save(tag); // insert into Tag

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }
}
