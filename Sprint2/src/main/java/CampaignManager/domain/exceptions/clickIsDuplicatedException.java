package CampaignManager.domain.exceptions;

public class clickIsDuplicatedException extends RuntimeException{
    private final String message;

    public clickIsDuplicatedException(){
        this.message = "Must have passed 15 seconds before charging for another click";
    }

}
