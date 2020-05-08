package unit;

import CampaignManager.domain.CampaignRepository;
import CampaignManager.domain.domainServices.CampaignService;
import CampaignManager.domain.domainServices.timeService.TimeService;
import CampaignManager.domain.domainServices.uuid.UUIDInterface;
import CampaignManager.domain.entities.*;
import CampaignManager.domain.entities.campaignStates.Activated;
import CampaignManager.domain.entities.campaignStates.Finished;
import CampaignManager.domain.entities.campaignStates.Paused;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;
import CampaignManager.domain.entities.valueObjects.click.ClickId;
import CampaignManager.domain.entities.valueObjects.click.InstantTime;
import CampaignManager.domain.entities.valueObjects.click.TypeValue;
import CampaignManager.domain.entities.valueObjects.click.UserId;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CampaignServiceTest {
    TimeService timeService;
    UUIDInterface uuid;
    CampaignRepository campaignRepository;
    CampaignService campaignService;

    @Before
    public void setup(){
        timeService = mock(TimeService.class);
        uuid = mock(UUIDInterface.class);
        campaignRepository = mock(CampaignRepository.class);
        campaignService = new CampaignService(timeService, uuid, campaignRepository);

    }

    @Test
    public void add_campaign_into_the_repository(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaignExpected = new Campaign(campaignId, budget);

        when(uuid.generateUUID()).thenReturn(uuidExpected);

        campaignService.createCampaign(new Budget(100));

        verify(campaignRepository).addCampaign(campaignExpected);
    }

    @Test
    public void campaign_is_activated(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new Campaign(campaignId, budget);

        when(uuid.generateUUID()).thenReturn(uuidExpected);

        campaignService.activateCampaign(campaign);

        assertTrue(campaign.getState() instanceof Activated);
    }

    @Test
    public void campaign_is_paused(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new Campaign(campaignId, budget);

        when(uuid.generateUUID()).thenReturn(uuidExpected);

        campaignService.activateCampaign(campaign);
        campaignService.pauseCampaign(campaign);

        assertTrue(campaign.getState() instanceof Paused);
    }

    @Test
    public void campaign_has_been_charged_for_a_normal_click(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new Campaign(campaignId, budget);
        Budget budgetExpected = new Budget(99.99);
        Campaign campaignExpected = new Campaign(campaignId, budgetExpected);

        when(uuid.generateUUID()).thenReturn(UUID.randomUUID(),UUID.randomUUID());

        ClickId clickId = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        when(timeService.formatDate()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.formatDate());

        Click click = new Click.ClickBuilder()
                .withClickId(clickId)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .build();

        campaignService.activateCampaign(campaign);

        assertEquals(campaignExpected, campaignService.chargeClick(campaign, click));
    }
    @Test
    public void campaign_has_been_charged_for_a_premium_click(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new Campaign(campaignId, budget);
        Budget budgetExpected = new Budget(99.95);
        Campaign campaignExpected = new Campaign(campaignId, budgetExpected);

        when(uuid.generateUUID()).thenReturn(UUID.randomUUID(),UUID.randomUUID());

        ClickId clickId = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        when(timeService.formatDate()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.formatDate());

        Click click = new Click.ClickBuilder()
                .withClickId(clickId)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .withClickType(new TypeValue(ClickValue.PREMIUM.getNumVal()))
                .build();

        campaignService.activateCampaign(campaign);

        assertEquals(campaignExpected, campaignService.chargeClick(campaign, click));
    }
    @Test
    public void campaign_has_finished(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(0.05);
        Campaign campaign = new Campaign(campaignId, budget);
        Budget budgetExpected = new Budget(0.00);
        Campaign campaignExpected = new Campaign(campaignId, budgetExpected, new Finished());

        when(uuid.generateUUID()).thenReturn(UUID.randomUUID(),UUID.randomUUID());

        ClickId clickId = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        when(timeService.formatDate()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.formatDate());

        Click click = new Click.ClickBuilder()
                .withClickId(clickId)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .withClickType(new TypeValue(ClickValue.PREMIUM.getNumVal()))
                .build();

        campaignService.activateCampaign(campaign);

        assertEquals(campaignExpected, campaignService.chargeClick(campaign, click));
    }

    @Test
    public void demoCampaign_has_been_charged_for_a_normal_click(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new DemoCampaign(campaignId, budget);
        Budget budgetExpected = new Budget(100);
        Campaign campaignExpected = new DemoCampaign(campaignId, budgetExpected);

        when(uuid.generateUUID()).thenReturn(UUID.randomUUID(),UUID.randomUUID());

        ClickId clickId = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        when(timeService.formatDate()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.formatDate());

        Click click = new Click.ClickBuilder()
                .withClickId(clickId)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        campaignService.activateCampaign(campaign);

        assertEquals(campaignExpected, campaignService.chargeClick(campaign, click));
    }

    @Test
    public void demoCampaign_has_been_charged_for_a_premium_click(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new DemoCampaign(campaignId, budget);
        Budget budgetExpected = new Budget(100);
        Campaign campaignExpected = new DemoCampaign(campaignId, budgetExpected);

        when(uuid.generateUUID()).thenReturn(UUID.randomUUID(),UUID.randomUUID());

        ClickId clickId = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        when(timeService.formatDate()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.formatDate());

        Click click = new Click.ClickBuilder()
                .withClickId(clickId)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .withClickType(new TypeValue(ClickValue.PREMIUM.getNumVal()))
                .build();

        campaignService.activateCampaign(campaign);

        assertEquals(campaignExpected, campaignService.chargeClick(campaign, click));
    }


    @Test
    public void topCampaign_has_been_charged_for_a_normal_click(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new TopCampaign(campaignId, budget);
        Budget budgetExpected = new Budget(99.99);
        Campaign campaignExpected = new TopCampaign(campaignId, budgetExpected);

        when(uuid.generateUUID()).thenReturn(UUID.randomUUID(),UUID.randomUUID());

        ClickId clickId = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        when(timeService.formatDate()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.formatDate());

        Click click = new Click.ClickBuilder()
                .withClickId(clickId)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        campaignService.activateCampaign(campaign);

        assertEquals(campaignExpected, campaignService.chargeClick(campaign, click));
    }

    @Test
    public void topCampaign_has_been_charged_for_a_premium_click(){
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new TopCampaign(campaignId, budget);
        Budget budgetExpected = new Budget(99.98);
        Campaign campaignExpected = new TopCampaign(campaignId, budgetExpected);

        when(uuid.generateUUID()).thenReturn(UUID.randomUUID(),UUID.randomUUID());

        ClickId clickId = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        when(timeService.formatDate()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.formatDate());

        Click click = new Click.ClickBuilder()
                .withClickId(clickId)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .withClickType(new TypeValue(ClickValue.PREMIUM.getNumVal()))
                .build();

        campaignService.activateCampaign(campaign);

        assertEquals(campaignExpected, campaignService.chargeClick(campaign, click));
    }

}
