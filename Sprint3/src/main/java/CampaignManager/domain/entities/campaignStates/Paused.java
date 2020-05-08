package CampaignManager.domain.entities.campaignStates;

public class Paused implements State {

    @Override
    public State activate(State state) {
        return new Activated();
    }

    @Override
    public State pause(State state) {
        return state;
    }
}
