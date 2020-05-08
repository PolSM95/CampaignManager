package CampaignManager.domain.DTOs.click;

import java.util.Objects;

public class ClickDTO {
    public ClickIdDTO clickId;
    public UserIdDTO userId;
    public InstantTimeDTO instant;
    public ClickTypeDTO type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClickDTO clickDTO = (ClickDTO) o;
        return Objects.equals(clickId, clickDTO.clickId) &&
                Objects.equals(userId, clickDTO.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clickId, userId, instant, type);
    }
}
