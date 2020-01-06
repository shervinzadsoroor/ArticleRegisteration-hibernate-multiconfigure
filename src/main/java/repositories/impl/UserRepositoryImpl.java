package repositories.impl;

import confighibernate.HibernateUtil;
import models.Address;
import models.Role;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repositories.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepositoryImpl implements UserRepository {
    public void changePass(String username) {

        Scanner scanner = new Scanner(System.in);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================
        System.out.println("enter new password(at least 4 characters): ");
        String newPassword = scanner.nextLine();
        if (newPassword.length() >= 4) {
            System.out.println("enter new password again: ");
            String newPasswordAgain = scanner.nextLine();

            if (newPassword.equals(newPasswordAgain)) {
                Query query = session.createQuery("update User set password = :password where username=:username");
                query.setParameter("password", newPassword);
                query.setParameter("username", username);
                query.executeUpdate();
                System.out.println("password changed successfully !!!");
            } else {
                System.out.println("invalid password !!!");
            }
        } else {
            System.out.println("new password is too short !!!");
        }
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void changeRole() {
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

    public Address getUserAddress() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("country: ");
        String country = scanner.nextLine();
        System.out.println("city: ");
        String city = scanner.nextLine();
        System.out.println("street: ");
        String street = scanner.nextLine();
        System.out.println("number: ");
        int number = (Integer.parseInt(scanner.nextLine()));
        Address address = new Address(country, city, street, number);
        return address;
    }

    public User getUserInfo() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("username: ");
        String username = scanner.nextLine();
        System.out.println("national code: ");
        String nationalCode = scanner.nextLine();
        System.out.println("birthday: ");
        String birthday = scanner.nextLine();
        User user = new User(username, nationalCode, nationalCode, birthday); // password is the national code for the first time
        return user;
    }

    public void signUp(User user, Address address) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        user.setAddresses(addresses);
        session.save(user);
        System.out.println("sign up successfully done!!!");

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

    public void deleteUser(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.load(User.class,id);
        session.remove(user);

        session.getTransaction().commit();
        session.close();
    }
}
