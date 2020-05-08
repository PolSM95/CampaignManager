package CampaignManager.domain.entities;

public enum ClickValue {
    NORMAL(0.01), PREMIUM(0.05);

    private double numVal;

    ClickValue(double numVal) {
        this.numVal = numVal;
    }

    public double getNumVal() {
        return numVal;
    }
}
