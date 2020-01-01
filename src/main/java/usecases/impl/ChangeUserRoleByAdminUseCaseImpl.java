package usecases.impl;

import confighibernate.HibernateUtil;
import models.Role;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import usecases.usecase.ChangeUserRoleByAdminUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeUserRoleByAdminUseCaseImpl implements ChangeUserRoleByAdminUseCase {
    @Override
    public void change() {
        Scanner scanner = new Scanner(System.in);
        Scanner stringScanner = new Scanner(System.in);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //---------------------------------------------------
        boolean userExist = false;
        boolean roleExist = false;
        String command = null;

        //shows users to admin-------------------------------
        Query<User> userQuery = session.createQuery("from User");
        List<User> users = userQuery.list();
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println("select user id:");
        Long userId = scanner.nextLong();

        //checking the existence of user
        for (User u : users) {
            if (u.getId() == userId) {
                userExist = true;
            }
        }

        if (userExist) {
            //shows roles to admin...................
            Query<Role> query = session.createQuery("from Role");
            List<Role> roles = query.list();
            for (Role role : roles) {
                System.out.println(role.toString());
            }
            //.......................................
            User user = session.load(User.class, userId);
            System.out.println("what do you want?:( change | add )");
            command = stringScanner.nextLine();
            if (command.equalsIgnoreCase("change")) {

                System.out.println("select role id:");
                Long roleId = scanner.nextLong();
                Role role = session.load(Role.class, roleId);
                List<Role> changedRoles = new ArrayList<>();
                changedRoles.add(role);
                user.setRoles(changedRoles);

            } else if (command.equalsIgnoreCase("add")) {

                List<Role> userRoles = user.getRoles();
                System.out.println("select role id:");
                Long roleId = scanner.nextLong();
                Role role = session.load(Role.class, roleId);

                boolean roleNotExist = true;
                for (Role role1 : userRoles) {
                    if (role1 == role) {
                        roleNotExist = false;
                    }
                }
                if (roleNotExist) {
                    userRoles.add(role);//adds new role to the roles list of the user
                } else {
                    System.out.println("the role already exist !!!");
                }

            } else {
                System.out.println("WRONG COMMAND !!!");
            }

        } else {
            System.out.println("USER NOT EXIST !!!");
        }

        //---------------------------------------------------
        session.getTransaction().commit();
        session.close();
    }
}
