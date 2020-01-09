import models.Article;
import models.User;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class TestFindAll {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Article article = new Article();
        Predicate<Article> predicate = a -> a.getId() < 10;
        List<Article> articles = article.findAll(predicate);
        for (Article art:articles) {
            System.out.println(art.toString());
        }

        User user = new User();
        System.out.println("enter beginning of bound number: ");
        int first = scanner.nextInt();
        System.out.println("enter end of bound number: ");
        int second = scanner.nextInt();
        Predicate<User>userPredicate = a-> a.getId()>=first && a.getId()<=second;
        List<User>users = user.findAll(userPredicate);
        for (User u :users) {
            System.out.println(u.toString());
        }
    }
}
