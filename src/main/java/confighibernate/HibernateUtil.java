package confighibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    static SessionFactory sessionFactory;
    static SessionFactory sessionFactory2;
    static SessionFactory sessionFactoryH2;

    static {
        sessionFactory = new Configuration().configure("hibernate.dbOne.cfg.xml").buildSessionFactory();
        sessionFactory2 = new Configuration().configure("hibernate.dbTwo.cfg.xml").buildSessionFactory();
        sessionFactoryH2 = new Configuration().configure("hibernate.h2.cfg.xml").buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory2() {
        return sessionFactory2;
    }

    public static SessionFactory getSessionFactoryH2() {
        return sessionFactoryH2;
    }
}
