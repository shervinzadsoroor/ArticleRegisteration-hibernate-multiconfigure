import confighibernate.HibernateUtil;
import models.Article;
import models.Category;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArticleRepositoryTest {

    private Session session;

    @BeforeEach
    public void openSession() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactoryH2();
        session = sessionFactory.openSession();
        session.beginTransaction();
        User user = new User("nahid", "1234", "1234", "1366");
        Category category = new Category("title", "description");
        Article article = new Article("title", "brief", "content", "1/5/2020",
                null, null, "no", user, category);

        session.save(user);

    }

    @Test
    @DisplayName("create article test")
    public void createTest() {

        User userTest = session.load(User.class, 1L);
        List<User> userList = Arrays.asList(userTest);

        assertNotNull(userList);
        assertEquals(1, userList.size());

        session.getTransaction().commit();
        session.close();
    }

    @Test
    @DisplayName("delete article test")
    public void delete() {

        Long id = 1L;
        boolean isIdExist = false;
        List<Long> idList = session.createQuery("select id from Article ")
                .list();
        for (Long articleId : idList) {
            if (id == articleId) {
                isIdExist = true;
            }
        }
        Article article = session.load(Article.class, id);
        if (isIdExist) {
            session.remove(article);
        } else {
            System.out.println("ID NOT FOUND !!!");
        }

        boolean isArticlesNull = false;
        List<Article> articles = session.createQuery("from Article ")
                .list();
        if (articles.size() == 0) {
            isArticlesNull = true;
        }
        assertTrue(isArticlesNull);
        session.getTransaction().commit();
        session.close();
    }
}
