package usecases.usecase;

import models.User;

public interface UserRepository {
    void changePass(String username);

    void changeRole();

    boolean isAdmin(User user);

    boolean isUserHasMultipleRoles(User user);

    User login();

    void signUp();
}
