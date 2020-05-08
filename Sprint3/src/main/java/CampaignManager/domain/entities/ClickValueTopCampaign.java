package CampaignManager.domain.entities;

public enum ClickValueTopCampaign {
    NORMAL(0.01), PREMIUM(0.02);

    private double numVal;

    ClickValueTopCampaign(double numVal) {
        this.numVal = numVal;
    }

    public double getNumVal() {
        return numVal;
    }
}
