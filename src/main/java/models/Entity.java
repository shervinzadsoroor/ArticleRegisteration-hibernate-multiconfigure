package models;

import confighibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class Entity <E, T extends Entity> {
    private SessionFactory sessionFactory;
    private Session session;

    public List<E> findAll(Predicate<E> predicate) {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        //-------------------------------------------
        List<E> returnList = new ArrayList<>();
        String query = "from " + this.getClass().getName();
        List<E> entities = session.createQuery(query)
                .list();
        for (E e : entities) {
            if (predicate.test(e)) {
                returnList.add(e);
            }
        }
        //-------------------------------------------
        session.getTransaction().commit();
        session.close();
        return returnList;
    }

    public void saveAsAnotherEntity(Function<E, T> function) {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        //-------------------------------------------
//        List<E> Eentities = new ArrayList<>();
//        List<T> Tentities = new ArrayList<>();
//        for (E e : Eentities) {
//            Tentities.add(function.apply(e));
//        }
        //todo
        //-------------------------------------------
        session.getTransaction().commit();
        session.close();

    }

}
