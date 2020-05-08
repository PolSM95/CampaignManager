package CampaignManager.domain.entities.valueObjects.click;

import CampaignManager.domain.DTOs.click.ClickIdDTO;

import java.util.Objects;
import java.util.UUID;

public class ClickId {
    private final UUID clickId;

    public ClickId(UUID clickId) {
        this.clickId = clickId;
    }

    public ClickIdDTO serialize(){
        ClickIdDTO clickIdDTO = new ClickIdDTO();
        clickIdDTO.clickId = clickId.toString();
        return clickIdDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClickId clickId1 = (ClickId) o;
        return Objects.equals(clickId, clickId1.clickId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clickId);
    }
}
