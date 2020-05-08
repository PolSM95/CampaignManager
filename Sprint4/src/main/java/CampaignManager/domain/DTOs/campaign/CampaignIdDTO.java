package CampaignManager.domain.DTOs.campaign;

import CampaignManager.domain.domainServices.uuid.UUIDGenerator;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;

import java.util.Objects;
import java.util.UUID;

public class CampaignIdDTO {
    public String campaignId;

    public CampaignId deserialize(){
        CampaignId campaignId = new CampaignId(UUID.fromString(this.campaignId));
        return campaignId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignIdDTO that = (CampaignIdDTO) o;
        return Objects.equals(campaignId, that.campaignId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId);
    }
}
