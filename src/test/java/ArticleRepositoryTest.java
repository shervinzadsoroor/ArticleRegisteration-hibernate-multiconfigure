import confighibernate.HibernateUtil;
import models.Article;
import models.Category;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import repositories.impl.ArticleRepositoryImpl;
import repositories.interfaces.ArticleRepository;

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
    }

    @Test
    @DisplayName("create article test")
    public void createTest() {
        User user = new User("nahid", "1234", "1234", "1366");
        Category category = new Category("title", "description");
        Article article = new Article("title", "brief", "content", "1/5/2020",
                null, null, "no", user, category);

        session.save(user);

        User userTest = session.load(User.class, 1L);
        List<User> userList = Arrays.asList(userTest);

        assertNotNull(userList);
        assertEquals(1, userList.size());

        String str = """
                """;

    }
}
