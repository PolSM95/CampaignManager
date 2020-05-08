package CampaignManager.domain;

import CampaignManager.domain.entities.Campaign;
import CampaignManager.domain.entities.Click;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;
import CampaignManager.domain.entities.valueObjects.click.UserId;

public interface ClickRepository {
    void addClick(UserId UserId, Click click);
}
