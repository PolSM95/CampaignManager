package CampaignManager.domain.DTOs.click;

import java.util.Objects;

public class UserIdDTO {
    public String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIdDTO userIdDTO = (UserIdDTO) o;
        return Objects.equals(userId, userIdDTO.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
