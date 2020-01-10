package repositories.interfacesRepositories;

import models.Address;
import models.User;

public interface UserRepository {
    void changePass(String username);

    void changeRole();

    boolean isAdmin(User user);

    boolean isUserHasMultipleRoles(User user);

    User login();

    User getUserInfo();

    Address getUserAddress();

    void signUp(User user, Address address);
    void deleteUser(Long id);
}
