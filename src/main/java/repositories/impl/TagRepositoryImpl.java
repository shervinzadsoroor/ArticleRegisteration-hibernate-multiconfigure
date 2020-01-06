package repositories.impl;

import confighibernate.HibernateUtil;
import models.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repositories.interfaces.TagRepository;

import java.util.Scanner;

public class TagRepositoryImpl implements TagRepository {
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
