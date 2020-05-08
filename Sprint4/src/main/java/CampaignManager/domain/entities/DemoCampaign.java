package CampaignManager.domain.entities;

import CampaignManager.domain.entities.campaignStates.State;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;

public class DemoCampaign extends Campaign{
    public DemoCampaign(CampaignId campaignId, Budget budget) {
        super(campaignId, budget);
    }

    public DemoCampaign(CampaignId campaignId, Budget budget, State state) {
        super(campaignId, budget, state);
    }

    @Override
    public void updateBudget(Double clickCost) {

    }
}
