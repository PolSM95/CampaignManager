package CampaignManager.domain.exceptions;

public class CampaignIsPausedException extends RuntimeException{
    private final String message;

    public CampaignIsPausedException(){
        this.message = "You cannot makes this action, the campaign is paused.";
    }

}
