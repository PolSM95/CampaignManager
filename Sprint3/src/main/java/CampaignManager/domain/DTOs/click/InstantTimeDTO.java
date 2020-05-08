package CampaignManager.domain.DTOs.click;

import java.util.Objects;

public class InstantTimeDTO {
    public String instant;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstantTimeDTO that = (InstantTimeDTO) o;
        return Objects.equals(instant, that.instant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instant);
    }
}
