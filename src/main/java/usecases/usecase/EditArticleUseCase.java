package usecases.usecase;

import models.User;

public interface EditArticleUseCase {
    public void edit(Long id, User user, String currentDate);
}
