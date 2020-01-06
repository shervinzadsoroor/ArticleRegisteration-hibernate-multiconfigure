package repositories.interfaces;

import models.Article;
import models.User;

public interface ArticleRepository {
    Article create(User user, String currentDate);

    void edit(Long id, User user,String currentDate);

    void delete (Long id);

    void publish(Long id, String currentDate);

    void showAll();

    void showUserArticlesAfterLogin(Long id);

    void showSpecificArticle(Long id);

    Long countArticlesOfUser(User user);

    void searchByTitle(String title);

    Long countPublishedArticle(User user);

    void unPublish(Long id, String currentDate);
}
