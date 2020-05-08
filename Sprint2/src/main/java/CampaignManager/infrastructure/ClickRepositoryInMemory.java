package CampaignManager.infrastructure;

import CampaignManager.domain.ClickRepository;
import CampaignManager.domain.entities.Campaign;
import CampaignManager.domain.entities.Click;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;
import CampaignManager.domain.entities.valueObjects.click.UserId;
import CampaignManager.domain.exceptions.clickIsDuplicatedException;

import java.util.HashMap;
import java.util.Map;

public class ClickRepositoryInMemory implements ClickRepository {
    public Map<UserId, Click> clickMap = new HashMap<>();
    @Override
    public void addClick(UserId userId, Click click) {
        boolean clickIsValid = clickMap.isEmpty();

        for(Map.Entry<UserId, Click> entry : clickMap.entrySet()){
            if(entry.getKey() == userId){
                clickIsValid = entry.getValue().instantNotDuplicated(click);
            }
        }
        if(!clickIsValid){
            throw new clickIsDuplicatedException();
        }
        clickMap.put(userId, click);
    }
}
