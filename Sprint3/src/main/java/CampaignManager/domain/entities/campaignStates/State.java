package CampaignManager.domain.entities.campaignStates;

public interface State {
    State activate(State state);
    State pause(State state);
}
