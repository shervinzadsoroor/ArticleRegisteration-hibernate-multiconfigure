package usecases.usecase;

import models.User;

public interface CreateNewArticleByWriterUseCase {
    public void create(User user, String currentDate);
}
