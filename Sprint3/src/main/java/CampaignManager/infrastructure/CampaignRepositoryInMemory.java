package CampaignManager.infrastructure;

import CampaignManager.domain.CampaignRepository;
import CampaignManager.domain.entities.Campaign;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;

import java.util.HashMap;
import java.util.Map;


public class CampaignRepositoryInMemory implements CampaignRepository {
    public Map<CampaignId, Campaign> campaignMap = new HashMap<>();
    @Override
    public void addCampaign(Campaign campaign) {
        campaignMap.put(campaign.getCampaignId(), campaign);
    }
}
