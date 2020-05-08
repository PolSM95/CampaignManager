package unit;

import CampaignManager.domain.CampaignRepository;
import CampaignManager.domain.entities.Campaign;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;
import CampaignManager.infrastructure.CampaignRepositoryInMemory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CampaignRepositoryInMemoryTest {

    @Test
    public void campaignRepository_adds_a_new_campaign(){
        CampaignRepositoryInMemory campaignRepositoryInMemory = new CampaignRepositoryInMemory();

        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new Campaign(campaignId, budget);

        Map<CampaignId, Campaign> campaignMapExpected = new HashMap<>();
        campaignMapExpected.put(campaignId, campaign);

        campaignRepositoryInMemory.addCampaign(campaign);

        assertEquals(campaignRepositoryInMemory.campaignMap, campaignMapExpected);
    }
}
