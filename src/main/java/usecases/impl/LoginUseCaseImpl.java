package usecases.impl;

import confighibernate.HibernateUtil;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.LoginUseCase;

import java.util.List;
import java.util.Scanner;

public class LoginUseCaseImpl implements LoginUseCase {
    @Override
    public User login() {
        Scanner scanner = new Scanner(System.in);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        boolean isLogin = false;
        User user = null;

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        System.out.println("username: ");
        String username = scanner.nextLine();

        System.out.println("password: ");
        String password = scanner.nextLine();

        List dbPassword = session.createQuery("select password from User where username = :username")
                .setParameter("username", username)
                .list();
        List users = session.createQuery("from User where username= :username")
                .setParameter("username", username)
                .list();

        if (users.size() == 1 && password.equals(dbPassword.get(0))) {
            user = (User) users.get(0);
        }

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
        //return isLogin;
        return user;
    }
}
