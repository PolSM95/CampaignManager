package CampaignManager.domain.exceptions;

public class TimeInstantIsNotDefinedException extends RuntimeException{
    private final String message;

    public TimeInstantIsNotDefinedException(){
        this.message = "The ClickId must be defined.";
    }

}
