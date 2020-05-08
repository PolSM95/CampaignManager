package unit;

import CampaignManager.domain.DTOs.campaign.BudgetDTO;
import CampaignManager.domain.domainServices.uuid.UUIDInterface;
import CampaignManager.domain.entities.Campaign;
import CampaignManager.domain.entities.campaignStates.Activated;
import CampaignManager.domain.entities.campaignStates.Finished;
import CampaignManager.domain.entities.campaignStates.Paused;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;
import CampaignManager.domain.entities.valueObjects.click.ClickId;
import CampaignManager.domain.entities.valueObjects.click.UserId;
import CampaignManager.domain.domainServices.uuid.UUIDGenerator;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CampaingTest {

    UUIDGenerator uuid = new UUIDGenerator();

    @Test
    public void campaign_is_activated(){

        CampaignId campaignId = new CampaignId(uuid.generateUUID());
        Budget budget = new Budget(100);

        Campaign campaign = new Campaign(campaignId, budget);
        Campaign campaignExpected = new Campaign(campaignId, budget, new Activated());

        campaign.activate();

        assertEquals(campaignExpected, campaign);
    }

    @Test
    public void campaign_is_paused(){

        CampaignId campaignId = new CampaignId(uuid.generateUUID());
        Budget budget = new Budget(100);

        Campaign campaign = new Campaign(campaignId, budget);
        Campaign campaignExpected = new Campaign(campaignId, budget, new Paused());

        campaign.activate();
        campaign.pause();

        assertEquals(campaignExpected, campaign);
    }

    @Test
    public void campaign_is_finished(){

        CampaignId campaignId = new CampaignId(uuid.generateUUID());
        Budget budget = new Budget(100);

        Campaign campaign = new Campaign(campaignId, budget);
        Campaign campaignExpected = new Campaign(campaignId, budget, new Finished());

        campaign.finish();

        campaign.activate();
        campaign.pause();

        assertEquals(campaignExpected, campaign);
    }

}
