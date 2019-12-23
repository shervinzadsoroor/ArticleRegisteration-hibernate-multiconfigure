package repositories;

import confighibernate.HibernateUtil;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class ArticleRegisterApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArticleRegisterApplication articleRegisterApplication =
                new ArticleRegisterApplication();
        String command = null;
        // boolean isLogin = false;
        User user = null;
        while (true) {
            if (user == null) {
                System.out.println("what do you want? (sign up | login | show articles): ");
                command = scanner.nextLine();
                if (command.equalsIgnoreCase("sign up")) {
                    articleRegisterApplication.signUp();
                }
                if (command.equalsIgnoreCase("login")) {
                    user = articleRegisterApplication.login();
                    if (user != null) {
                        System.out.println("    LOGIN SUCCESSFUL !!!");
                    } else {
                        System.out.println("    INVALID USERNAME OR PASSWORD !!!");
                    }
                }
                if (command.equalsIgnoreCase("show articles")) {
                    articleRegisterApplication.showAllArticles();
                }
            }
            if (user != null) {
                System.out.println("what do you want? (show | edit | new | change pass | dashboard | logout): ");
                command = scanner.nextLine();

                if (command.equalsIgnoreCase("show")) {

                }
                if (command.equalsIgnoreCase("edit")) {

                }
                if (command.equalsIgnoreCase("new")) {
                    articleRegisterApplication.showListOfCategories();
                    articleRegisterApplication.createNewArticle();
                }
                if (command.equalsIgnoreCase("change pass")) {
                    articleRegisterApplication.changePassword(user.getUsername());
                }
                if (command.equalsIgnoreCase("dashboard")) {

                }
                if (command.equalsIgnoreCase("logout")) {
                    user = null;
                }
            }

        }
    }

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

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();

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

    public void showAllArticles() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void showSpecificArticle() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void showUserArticlesAfterLogin() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void createNewArticle() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================


        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void showListOfCategories() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        List list = session.createQuery("select title from Category")
                .list();
        System.out.println("\nCATEGORY TITLES\n==========================================");
        for (Object title : list) {
            System.out.println(title);
        }
        System.out.println("==========================================");

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void creatNewCategory() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void edit() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void editArticle() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void changePassword(String username) {
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

    public void deleteArticle() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void publishArticle() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void updateLastUpdateDate() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================
        //todo

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void updatePublishDate() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================
        //todo

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void dashboard() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void countOfAllArticles() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================

        //todo
        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

    public void countOfPublishedArticles() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        //get session
        Session session = sessionFactory.openSession();
        //transaction start
        session.beginTransaction();
        //====================================
        //todo

        //====================================
        //transaction commit
        session.getTransaction().commit();
        session.close();
    }

}
