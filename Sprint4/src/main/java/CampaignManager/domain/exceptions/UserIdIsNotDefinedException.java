package CampaignManager.domain.exceptions;

public class UserIdIsNotDefinedException extends RuntimeException{
    private final String message;

    public UserIdIsNotDefinedException(){
        this.message = "The UserId must be defined.";
    }

}
