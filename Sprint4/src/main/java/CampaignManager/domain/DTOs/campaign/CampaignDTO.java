package CampaignManager.domain.DTOs.campaign;

import CampaignManager.domain.entities.Campaign;
import CampaignManager.domain.entities.campaignStates.Finished;

import java.util.Objects;

public class CampaignDTO {
    public CampaignIdDTO campaignId;
    public BudgetDTO budgetDTO;

    public Campaign deserialize(){
        if(budgetDTO.budget > 0){
            Campaign campaign = new Campaign(campaignId.deserialize(), budgetDTO.deserialize());
            return campaign;
        }
        Campaign campaign = new Campaign(campaignId.deserialize(), budgetDTO.deserialize(), new Finished());
        return campaign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignDTO that = (CampaignDTO) o;
        return Objects.equals(campaignId, that.campaignId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId);
    }
}
