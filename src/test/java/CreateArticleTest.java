import models.Article;
import models.Category;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import repositories.impl.ArticleRepositoryImpl;
import repositories.interfaces.ArticleRepository;

import static org.junit.jupiter.api.Assertions.*;

public class CreateArticleTest {

    //private String currentDate = "1/5/2020";

    //@Mock
//    private User user = new User("ali", "1234", "1234", "1366");
//    Category category = new Category("title","description");

    @Test
    @DisplayName("create article test")
    public void createTest() {
        User user = new User("ali", "1234", "1234", "1366");
        Category category = new Category("title", "description");
        Article article = new Article("title", "brief", "content", "1/5/2020",
                null, null, "no", user, category);

        String currentDate = "1/5/2020";
        ArticleRepository articleRepository = new ArticleRepositoryImpl();
        //assertEquals(article,articleRepository.create(user,"1/5/2020"));
        Article actualArticle = articleRepository.create(user, currentDate);
        assertSame(article, actualArticle);

    }
}
