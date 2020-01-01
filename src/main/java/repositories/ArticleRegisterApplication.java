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
        boolean isUserHasMultipleRoles = false;

        User user = null;
        while (true) {
            //before login
            if (user == null) {
                System.out.println("what do you want? ( sign up | login ): ");
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
                        System.out.println("LOGIN SUCCESSFUL !!!");
                        isUserHasMultipleRoles = new DefineUserWithMultipleRolesUseCaseImpl()
                                .isUserHasMultipleRoles(user);

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
                        }else {
                            isAdmin = new DefineAdminUseCaseImpl().isAdmin(user);
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
                    ShowAllArticlesByAdminUseCase showAllArticlesUseCase =
                            new ShowAllArticlesByAdminUseCaseImpl();
                    showAllArticlesUseCase.show();
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("show one")) {
                    System.out.println("enter article id :");
                    Long id = Long.parseLong(scanner.nextLine());
                    ShowSpecificArticleByAdminUseCase showSpecificArticleUseCase =
                            new ShowSpecificArticleByAdminUseCaseImpl();
                    showSpecificArticleUseCase.show(id);
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("publish")) {
                    System.out.println("enter article id: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    PublishArticleByAdminUseCase publishArticleUseCase =
                            new PublishArticleByAdminUseCaseImpl();
                    publishArticleUseCase.publish(id, currentDate());
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("unPublish")) {
                    System.out.println("enter article id: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    UnPublishArticleByAdminUseCase unPublishArticleByAdminUseCase =
                            new UnPublishArticleByAdminUseCaseImpl();
                    unPublishArticleByAdminUseCase.unPublish(id, currentDate());
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("delete article")) {
                    System.out.println("enter article id: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    DeleteArticleByAdminUseCase deleteArticleUseCase =
                            new DeleteArticleByAdminUseCaseImpl();
                    deleteArticleUseCase.delete(id);
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("edit user role")) {
                    ChangeUserRoleByAdminUseCase changeUserRoleByAdminUseCase =
                            new ChangeUserRoleByAdminUseCaseImpl();
                    changeUserRoleByAdminUseCase.change();
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("new tag")) {
                    CreateNewTagByAdminUseCase createNewTagByAdminUseCase
                            = new CreateNewTagByAdminUseCaseImpl();
                    createNewTagByAdminUseCase.create();
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("new category")) {
                    CreateNewCategoryByAdminUseCase createNewCategoryByAdminUseCase
                            = new CreateNewCategoryByAdminUseCaseImpl();
                    createNewCategoryByAdminUseCase.create();
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
                    new SearchTitleByAdminUseCaseImpl().search(title);
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("logout")) {
                    user = null;
                } else {
                    System.out.println("wrong command !!!");
                }

                //----------------------------------------------------------
            }
            if (user != null && !isAdmin) {

                System.out.println("what do you want? ( new article | edit | show my articles" +
                        " | change pass | logout)");
                command = scanner.nextLine();

                //----------------------------------------------------------
                if (command.equalsIgnoreCase("new article")) {
                    ShowListOfCategoriesUseCase showListOfCategoriesUseCase =
                            new ShowListOfCategoriesUseCaseImpl();
                    showListOfCategoriesUseCase.show();

                    CreateNewArticleByWriterUseCase createNewArticleByWriterUseCase =
                            new CreateNewArticleByWriterUseCaseImpl();
                    createNewArticleByWriterUseCase.create(user, currentDate());

                    //----------------------------------------------------------
                } else if (command.equalsIgnoreCase("show my articles")) {
                    ShowUserArticlesAfterLoginByWriterUseCase showUserArticlesAfterLoginUseCase =
                            new ShowUserArticlesAfterLoginByWriterUseCaseImpl();
                    showUserArticlesAfterLoginUseCase.show(user.getId());
                }

                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("edit") &&
                        new SpecifyExistanceOfUserArticleUseCaseImpl().specify(user.getId())) {

                    System.out.println("enter article id: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    EditArticleByWriterUseCase editArticleUseCase =
                            new EditArticleByWriterUseCaseImpl();
                    editArticleUseCase.edit(id, user, currentDate());

                }
                //----------------------------------------------------------

                else if (command.equalsIgnoreCase("change pass")) {
                    ChangePasswordUseCase changePasswordUseCase =
                            new ChangePasswordUseCaseImpl();
                    changePasswordUseCase.changePass(user.getUsername());
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
