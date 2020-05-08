package CampaignManager.domain.entities;

import CampaignManager.domain.entities.campaignStates.State;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;

public class TopCampaign extends Campaign{

    public TopCampaign(CampaignId campaignId, Budget budget) {
        super(campaignId, budget);
    }
    public TopCampaign(CampaignId campaignId, Budget budget, State state) {
        super(campaignId, budget, state);
    }

    @Override
    public void updateBudget(Double clickCost) {
        if(clickCost == ClickValue.NORMAL.getNumVal()){
            super.updateBudget(ClickValueTopCampaign.NORMAL.getNumVal());
        }
        if(clickCost == ClickValue.PREMIUM.getNumVal()){
            super.updateBudget(ClickValueTopCampaign.PREMIUM.getNumVal());
        }
    }
}
