package CampaignManager.domain.entities.campaignStates;

public class Activated implements State {
    @Override
    public State activate(State state) {
        return state;
    }

    @Override
    public State pause(State state) {
        return new Paused();
    }
}
