package CampaignManager.domain.entities;

import CampaignManager.domain.DTOs.campaign.CampaignDTO;
import CampaignManager.domain.entities.campaignStates.Finished;
import CampaignManager.domain.entities.campaignStates.Paused;
import CampaignManager.domain.entities.campaignStates.State;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class Campaign {
    private CampaignId campaignId;
    private Budget budget;
    private State state;


    public Campaign(CampaignId campaignId, Budget budget) {
        this.campaignId = campaignId;
        this.budget = budget;
        this.state = new Paused();
    }

    public Campaign(CampaignId campaignId, Budget budget, State state) {
        this.campaignId = campaignId;
        this.budget = budget;
        this.state = state;
    }

    public CampaignDTO serialize(){
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.campaignId = campaignId.serialize();
        campaignDTO.budgetDTO = budget.serialize();

        return campaignDTO;
    }

    public void updateBudget(Double clickCost){
        DecimalFormat decimalFormat = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.US));
        Double result = budget.getBudget() - clickCost;
        this.budget = new Budget(Double.parseDouble(decimalFormat.format(result)));
    }

    public void activate(){
        state = state.activate(state);
    }

    public void pause(){
        state = state.pause(state);
    }

    public void finish(){
        state = new Finished();
    }

    public State getState() {
        return state;
    }

    public CampaignId getCampaignId() {
        return campaignId;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return Objects.equals(campaignId, campaign.campaignId) &&
                Objects.equals(budget, campaign.budget) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId, budget, state);
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "campaignId=" + campaignId +
                ", budget=" + budget +
                ", state=" + state +
                '}';
    }
}
