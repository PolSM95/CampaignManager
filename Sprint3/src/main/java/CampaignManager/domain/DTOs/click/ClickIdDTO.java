package CampaignManager.domain.DTOs.click;

import java.util.Objects;


public class ClickIdDTO {

    public String clickId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClickIdDTO that = (ClickIdDTO) o;
        return Objects.equals(clickId, that.clickId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clickId);
    }
}
