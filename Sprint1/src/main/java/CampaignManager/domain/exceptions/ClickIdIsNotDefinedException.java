package CampaignManager.domain.exceptions;

public class ClickIdIsNotDefinedException extends RuntimeException{
    private final String message;

    public ClickIdIsNotDefinedException(){
        this.message = "The ClickId must be defined.";
    }

}
