package CampaignManager.domain;

import CampaignManager.domain.entities.Campaign;

public interface CampaignRepository {
    void addCampaign(Campaign campaign);
}
