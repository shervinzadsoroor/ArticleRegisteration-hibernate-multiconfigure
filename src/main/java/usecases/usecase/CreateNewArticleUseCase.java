package usecases.usecase;

import models.User;

public interface CreateNewArticleUseCase {
    public void create(User user, String currentDate);
}
