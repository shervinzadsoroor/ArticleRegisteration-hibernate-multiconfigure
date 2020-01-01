package usecases.usecase;

import models.User;

public interface EditArticleByWriterUseCase {
    public void edit(Long id, User user, String currentDate);
}
