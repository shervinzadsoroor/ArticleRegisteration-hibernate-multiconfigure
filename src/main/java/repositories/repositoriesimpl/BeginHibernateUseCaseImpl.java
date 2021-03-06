package repositories.repositoriesimpl;

import confighibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repositories.interfacesRepositories.BeginHibernateUseCase;

import javax.persistence.Embeddable;

@Embeddable
public class BeginHibernateUseCaseImpl implements BeginHibernateUseCase {
    @Override
    public void begin() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //--------------------------------------------------

        System.out.println("hello, let's start !!!");

        //--------------------------------------------------
        session.getTransaction().commit();
        session.close();
    }
}
