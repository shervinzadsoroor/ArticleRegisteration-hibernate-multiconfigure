package usecases.usecase;

import models.User;

public interface PublishArticleUseCase {
    public void publish(Long id, User user, String currentDate);
}
