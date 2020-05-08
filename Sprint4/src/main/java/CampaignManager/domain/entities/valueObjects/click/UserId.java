package CampaignManager.domain.entities.valueObjects.click;

import CampaignManager.domain.DTOs.click.UserIdDTO;

import java.util.Objects;
import java.util.UUID;

public class UserId {
    private final UUID userId;

    public UserId(UUID userId) {

        this.userId = userId;
    }

    public UserIdDTO serialize(){
        UserIdDTO userIdDTO = new UserIdDTO();
        userIdDTO.userId = userId.toString();
        return userIdDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId1 = (UserId) o;
        return Objects.equals(userId, userId1.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
