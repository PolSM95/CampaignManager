package CampaignManager.domain.exceptions;

public class CannotChargeMoreThanTheBudgetException extends RuntimeException{
    private final String message;

    public CannotChargeMoreThanTheBudgetException(){
        this.message = "You cannot charge more than the budget";
    }

}
