package usecases.impl;

import confighibernate.HibernateUtil;
import models.Role;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import usecases.usecase.DefineUserWithMultipleRolesUseCase;

import java.util.List;

public class DefineUserWithMultipleRolesUseCaseImpl implements DefineUserWithMultipleRolesUseCase {
    @Override
    public boolean isUserHasMultipleRoles(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //-------------------------------------------------------

        boolean isAdmin = false;
        boolean isWriter = false;
        boolean isMultipleRole = false;

        Role admin = session.find(Role.class, 1L); // returns admin
        List<User> admins = admin.getUsers();

        Role writer = session.find(Role.class, 2L); // returns writer
        List<User> writers = writer.getUsers();

        for (User u : admins) {
            if (u.getId() == user.getId()) {
                isAdmin = true;
            }
        }
        for (User u : writers) {
            if (u.getId() == user.getId()) {
                isWriter = true;
            }
        }
        if (isAdmin && isWriter) {
            isMultipleRole = true;
        }

        //-------------------------------------------------------
        session.getTransaction().commit();
        session.close();
        return isMultipleRole;
    }
}
