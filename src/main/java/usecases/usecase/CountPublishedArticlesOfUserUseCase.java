package usecases.usecase;

import models.User;

public interface CountPublishedArticlesOfUserUseCase {
    public Long count(User user);
}
