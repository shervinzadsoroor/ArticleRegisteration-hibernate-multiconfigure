package usecases.impl;

import confighibernate.HibernateUtil;
import models.Role;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import usecases.usecase.DefineAdminUseCase;

import java.util.ArrayList;
import java.util.List;

public class DefineAdminUseCaseImpl implements DefineAdminUseCase {
    @Override
    public boolean isAdmin(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //-------------------------------------------------------

        boolean bool = false;

        Role role = session.find(Role.class, 1L); // returns admin
        List<User> admins = role.getUsers();

        for (User admin : admins) {
            if (admin.getId() == user.getId()) {
                bool = true;
            }
        }

        //-------------------------------------------------------
        session.getTransaction().commit();
        session.close();
        return bool;
    }
}
