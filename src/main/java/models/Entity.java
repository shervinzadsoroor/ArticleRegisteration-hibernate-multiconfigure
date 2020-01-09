package models;

import confighibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class Entity<E, T extends Entity> {
    SessionFactory sessionFactory;
    Session session;

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

    public List<E> findAll(Function<T, E> function) {
        List<E> Eentities = new ArrayList<>();
        List<T> Tentities = new ArrayList<>();
        for (T t : Tentities) {
            Eentities.add(function.apply(t));
        }

        return Eentities;
    }

}
