package usecases.usecase;

import models.User;

public interface PublishArticleByAdminUseCase {
    public void publish(Long id, String currentDate);
}
