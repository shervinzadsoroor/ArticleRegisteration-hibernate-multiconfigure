package repositories;

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

        User user = null;
        while (true) {
            if (user == null) {
                System.out.println("what do you want? ( sign up | login | show articles | show one ): ");
                command = scanner.nextLine();
                //----------------------------------------------------------

                if (command.equalsIgnoreCase("sign up")) {
                    SignUpUseCase signUpUseCase = new SignUpUseCaseImpl();
                    signUpUseCase.signUp();
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("login")) {
                    LoginUseCase loginUseCase = new LoginUseCaseImpl();
                    user = loginUseCase.login();

                    if (user != null) {
                        isAdmin = new DefineAdminUseCaseImpl().isAdmin(user);
                        System.out.println("  LOGIN SUCCESSFUL !!!");
                        if (isAdmin){
                            System.out.println("HELLO ADMIN !!!");
                        }else {
                            System.out.println("HELLO WRITER !!!");
                        }
                    } else {
                        System.out.println("  INVALID USERNAME OR PASSWORD !!!");
                    }
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("show articles")) {
                    ShowAllArticlesUseCase showAllArticlesUseCase =
                            new ShowAllArticlesUseCaseImpl();
                    showAllArticlesUseCase.show();
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("show one")) {
                    System.out.println("enter article id :");
                    Long id = Long.parseLong(scanner.nextLine());
                    ShowSpecificArticleUseCase showSpecificArticleUseCase =
                            new ShowSpecificArticleUseCaseImpl();
                    showSpecificArticleUseCase.show(id);
                } else {
                    System.out.println("wrong command !!!");
                }

                //----------------------------------------------------------
            }
            if (user != null) {
                System.out.println("what do you want? ( show | edit | new | change pass | dashboard | search | logout ): ");
                command = scanner.nextLine();

                //----------------------------------------------------------

                if (command.equalsIgnoreCase("show")) {
                    ShowUserArticlesAfterLoginUseCase showUserArticlesAfterLoginUseCase =
                            new ShowUserArticlesAfterLoginUseCaseImpl();
                    showUserArticlesAfterLoginUseCase.show(user.getId());
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("edit") &&
                        new SpecifyExistanceOfUserArticleUseCaseImpl().specify(user.getId())) {
                    System.out.println("choose an option: ( edit article | publish | delete )");
                    String articleCommand = scanner.nextLine();
                    Long id;
                    if (articleCommand.equalsIgnoreCase("edit article")) {
                        System.out.println("enter article id: ");
                        id = Long.parseLong(scanner.nextLine());
                        EditArticleUseCase editArticleUseCase =
                                new EditArticleUseCaseImpl();
                        editArticleUseCase.edit(id, user, currentDate());
                    }
                    if (articleCommand.equalsIgnoreCase("publish")) {
                        System.out.println("enter article id: ");
                        id = Long.parseLong(scanner.nextLine());
                        PublishArticleUseCase publishArticleUseCase =
                                new PublishArticleUseCaseImpl();
                        publishArticleUseCase.publish(id, user, currentDate());
                    }
                    if (articleCommand.equalsIgnoreCase("delete")) {
                        System.out.println("enter article id: ");
                        id = Long.parseLong(scanner.nextLine());
                        DeleteArticleUseCase deleteArticleUseCase =
                                new DeleteArticleUseCaseImpl();
                        deleteArticleUseCase.delete(id, user);
                    }

                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("new")) {
                    ShowListOfCategoriesUseCase showListOfCategoriesUseCase =
                            new ShowListOfCategoriesUseCaseImpl();
                    showListOfCategoriesUseCase.show();

                    System.out.println("select category option ( new | existing ): ");
                    String catOption = scanner.nextLine();
                    if (catOption.equalsIgnoreCase("new")) {
                        CreateNewCategoryUseCase createNewCategoryUseCase =
                                new CreateNewCategoryUseCaseImpl();
                        createNewCategoryUseCase.create();

                        showListOfCategoriesUseCase.show();
                        CreateNewArticleUseCase createNewArticleUseCase =
                                new CreateNewArticleUseCaseImpl();
                        createNewArticleUseCase.create(user, currentDate());
                    } else if (catOption.equalsIgnoreCase("existing")) {
                        CreateNewArticleUseCase createNewArticleUseCase =
                                new CreateNewArticleUseCaseImpl();
                        createNewArticleUseCase.create(user, currentDate());
                    } else {
                        System.out.println("wrong command !!!");
                    }

                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("change pass")) {
                    ChangePasswordUseCase changePasswordUseCase =
                            new ChangePasswordUseCaseImpl();
                    changePasswordUseCase.changePass(user.getUsername());
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("dashboard")) {
                    CountAllArticlesOfUserUseCase countAllArticlesOfUserUseCase =
                            new CountAllArticlesOfUserUseCaseImpl();
                    CountPublishedArticlesOfUserUseCase countPublishedArticlesOfUserUseCase =
                            new CountPublishedArticlesOfUserUseCaseImpl();
                    System.out.println("quantity of your articles: " +
                            countAllArticlesOfUserUseCase.count(user));

                    System.out.println("quantity of your published articles: " +
                            countPublishedArticlesOfUserUseCase.count(user));
                }
                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("search")) {
                    System.out.println("enter title for search: ");
                    String title = scanner.nextLine();
                    new SearchByTitleUseCaseImpl().search(title);
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("logout")) {
                    user = null;
                } else {
                    System.out.println("wrong command !!!");
                }

                //----------------------------------------------------------
            }
        }
    }

    public static String currentDate() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return date.format(now);
    }

}
