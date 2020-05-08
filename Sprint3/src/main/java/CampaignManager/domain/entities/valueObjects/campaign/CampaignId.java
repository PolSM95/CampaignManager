package CampaignManager.domain.entities.valueObjects.campaign;

import CampaignManager.domain.DTOs.campaign.CampaignIdDTO;

import java.util.Objects;
import java.util.UUID;

public class CampaignId {

    private final UUID campaignId;

    public CampaignId(UUID campaignId) {
        this.campaignId = campaignId;
    }

    public CampaignIdDTO serialize(){
        CampaignIdDTO campaignIdDTO = new CampaignIdDTO();
        campaignIdDTO.campaignId = campaignId.toString();
        return campaignIdDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignId that = (CampaignId) o;
        return Objects.equals(campaignId, that.campaignId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId);
    }

    @Override
    public String toString() {
        return "CampaignId{" +
                "campaignId=" + campaignId +
                '}';
    }
}
