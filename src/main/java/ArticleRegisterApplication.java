import models.User;
import usecases.impl.*;
import usecases.usecase.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ArticleRegisterApplication {

    public static void main(String[] args) {
        new BeginHibernateUseCaseImpl().begin();
        Scanner scanner = new Scanner(System.in);
        String command = null;
        boolean isAdmin = false;
        boolean isUserHasMultipleRoles = false;
        ArticleRepository articleRepository = new ArticleRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        TagRepository tagRepository = new TagRepositoryImpl();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl();

        User user = null;
        while (true) {
            //before login
            if (user == null) {
                System.out.println("what do you want? ( sign up | login ): ");
                command = scanner.nextLine();
                //----------------------------------------------------------
                if (command.equalsIgnoreCase("sign up")) {
                    userRepository.signUp();
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("login")) {
                    user = userRepository.login();

                    if (user != null) {
                        System.out.println("LOGIN SUCCESSFUL !!!");
                        isUserHasMultipleRoles = userRepository.isUserHasMultipleRoles(user);

                        if (isUserHasMultipleRoles) {
                            System.out.println("you have multiple roles . choose your role to login: ( admin | writer )");
                            command = scanner.nextLine();
                            if (command.equalsIgnoreCase("admin")) {
                                isAdmin = true;

                            } else if (command.equalsIgnoreCase("writer")) {
                                isAdmin = false;
                            } else {
                                System.out.println("WRONG COMMAND !!!");
                            }
                        } else {
                            isAdmin = userRepository.isAdmin(user);
                        }
                        if (isAdmin) {
                            System.out.println("HELLO ADMIN !!!");
                        } else {
                            System.out.println("HELLO WRITER !!!");
                        }
                    } else {
                        System.out.println("INVALID USERNAME OR PASSWORD !!!");
                    }
                } else {
                    System.out.println("WRONG COMMAND !!!");
                }
            }
            //after login---------------------------------------------------
            if (user != null && isAdmin) {
                System.out.println("what do you want? ( show articles | show one | change pass |" +
                        " dashboard | delete article | search | edit user role | publish | unPublish " +
                        "| new tag | new category | logout  ): ");
                command = scanner.nextLine();
                //----------------------------------------------------------
                if (command.equalsIgnoreCase("show articles")) {
                    articleRepository.showAll();
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("show one")) {
                    System.out.println("enter article id :");
                    Long id = Long.parseLong(scanner.nextLine());
                    articleRepository.showSpecificArticle(id);
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("publish")) {
                    System.out.println("enter article id: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    articleRepository.publish(id, currentDate());
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("unPublish")) {
                    System.out.println("enter article id: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    articleRepository.unPublish(id, currentDate());
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("delete article")) {
                    System.out.println("enter article id: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    articleRepository.delete(id);
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("edit user role")) {
                    userRepository.changeRole();
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("new tag")) {
                    tagRepository.create();
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("new category")) {
                    categoryRepository.create();
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("change pass")) {
                    userRepository.changePass(user.getUsername());
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("dashboard")) {
                    System.out.println("quantity of your articles: " +
                            articleRepository.countArticlesOfUser(user));
                    System.out.println("quantity of your published articles: " +
                            articleRepository.countPublishedArticle(user));
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("search")) {
                    System.out.println("enter title for search: ");
                    String title = scanner.nextLine();
                    articleRepository.searchByTitle(title);
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("logout")) {
                    user = null;
                } else {
                    System.out.println("wrong command !!!");
                }
            }
            //----------------------------------------------------------
            if (user != null && !isAdmin) {

                System.out.println("what do you want? ( new article | edit | show my articles" +
                        " | change pass | logout)");
                command = scanner.nextLine();
                //----------------------------------------------------------
                if (command.equalsIgnoreCase("new article")) {
                    categoryRepository.show();
                    articleRepository.create(user, currentDate());
                    //----------------------------------------------------------
                } else if (command.equalsIgnoreCase("show my articles")) {
                    articleRepository.showUserArticlesAfterLogin(user.getId());
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("edit") &&
                        new SpecifyExistanceOfUserArticleUseCaseImpl().specify(user.getId())) {

                    System.out.println("enter article id: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    articleRepository.edit(id, user, currentDate());
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("change pass")) {
                    userRepository.changePass(user.getUsername());
                }
                //----------------------------------------------------------
                else if (command.equalsIgnoreCase("logout")) {
                    user = null;
                } else {
                    System.out.println("WRONG COMMAND !!!");
                }
            }
        }
    }

    public static String currentDate() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return date.format(now);
    }
}