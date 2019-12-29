package usecases.usecase;

import models.User;

public interface CountAllArticlesOfUserUseCase {
    public Long count(User user);
}
