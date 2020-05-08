package CampaignManager.domain.entities;

import CampaignManager.domain.entities.campaignStates.State;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;

public class StandardCampaign extends Campaign {
    public StandardCampaign(CampaignId campaignId, Budget budget) {
        super(campaignId, budget);
    }

    public StandardCampaign(CampaignId campaignId, Budget budget, State state) {
        super(campaignId, budget, state);
    }

    @Override
    public void updateBudget(Double clickCost) {
        super.updateBudget(clickCost);
    }
}
