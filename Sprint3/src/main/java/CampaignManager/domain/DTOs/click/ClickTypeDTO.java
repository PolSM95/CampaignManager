package CampaignManager.domain.DTOs.click;

import java.util.Objects;

public class ClickTypeDTO {
    public double type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClickTypeDTO that = (ClickTypeDTO) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
