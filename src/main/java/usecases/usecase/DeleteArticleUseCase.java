package usecases.usecase;

import models.User;

public interface DeleteArticleUseCase {
    public void delete(Long id, User user);
}
