package usecases.impl;

import confighibernate.HibernateUtil;
import models.Role;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.SignUpUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SignUpUseCaseImpl implements SignUpUseCase {
    @Override
    public void signUp() {
        Scanner scanner = new Scanner(System.in);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================
        System.out.println("username: ");
        String username = scanner.nextLine();

        System.out.println("national code: ");
        String nationalCode = scanner.nextLine();
        System.out.println("birthday: ");
        String birthday = scanner.nextLine();
        User user = new User(username, nationalCode, nationalCode, birthday); // password is the national code for the first time
        Long id = (Long) session.save(user);
        System.out.println("sign up successfully done!!!\nyour id is:" + id);

        //defines the user as writer
        Role role = session.find(Role.class, 2L); // returns writer
        List<Role> roles = new ArrayList<>(); // we use list because the user can have many roles
        roles.add(role);

        user.setRoles(roles);

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();

    }
}

