package CampaignManager.domain.entities.valueObjects.click;

import CampaignManager.domain.DTOs.click.InstantTimeDTO;

import java.util.Objects;

public class InstantTime {
    private String instant;

    public InstantTime(String instant) {
        this.instant = instant;
    }

    public InstantTimeDTO serialize(){
        InstantTimeDTO instantTimeDTO = new InstantTimeDTO();
        instantTimeDTO.instant = instant;
        return instantTimeDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstantTime that = (InstantTime) o;
        return Objects.equals(instant, that.instant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instant);
    }
}
