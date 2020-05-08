package unit;

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
import CampaignManager.domain.entities.StandardCampaign;
import CampaignManager.domain.entities.TopCampaign;
import CampaignManager.domain.entities.DemoCampaign;
import CampaignManager.domain.entities.valueObjects.click.TypeValue;
import CampaignManager.domain.entities.valueObjects.click.UserId;
import CampaignManager.infrastructure.CampaignRepositoryInMemory;
import CampaignManager.infrastructure.ClickRepositoryInMemory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CampaignServiceTest {
    TimeService timeService;
    UUIDInterface uuid;
    CampaignRepositoryInMemory campaignRepository;
    CampaignService campaignService;
    ClickRepositoryInMemory clickRepository;
    @Before
    public void setup(){
        timeService = mock(TimeService.class);
        uuid = mock(UUIDInterface.class);
        campaignRepository = mock(CampaignRepositoryInMemory.class);
        clickRepository = mock(ClickRepositoryInMemory.class);
        campaignService = new CampaignService(timeService, uuid, campaignRepository, clickRepository);

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

        when(timeService.giveDateInSeconds()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.giveDateInSeconds());

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

        when(timeService.giveDateInSeconds()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.giveDateInSeconds());

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

        when(timeService.giveDateInSeconds()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.giveDateInSeconds());

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

        when(timeService.giveDateInSeconds()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.giveDateInSeconds());

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

        when(timeService.giveDateInSeconds()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.giveDateInSeconds());

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

        when(timeService.giveDateInSeconds()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.giveDateInSeconds());

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

        when(timeService.giveDateInSeconds()).thenReturn("07/05/2020");

        InstantTime instantTime = new InstantTime(timeService.giveDateInSeconds());

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
    public void topCampaing_with_fraudulentClicks_and_lower_clickCharged_than_five_percent() {
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new TopCampaign(campaignId, budget, new Activated());
        Budget budgetExpected = new Budget(99.96);
        Campaign campaignExpected = new TopCampaign(campaignId, budgetExpected, new Activated());

        ClickId clickId1 = new ClickId(UUID.randomUUID());
        ClickId clickId2 = new ClickId(UUID.randomUUID());
        ClickId clickId3 = new ClickId(UUID.randomUUID());
        UserId userId1 = new UserId(UUID.randomUUID());
        UserId userId2 = new UserId(UUID.randomUUID());
        UserId userId3 = new UserId(UUID.randomUUID());

        when(timeService.giveDateInSeconds()).thenReturn("0", "30", "60", "90");

        Click click1 = new Click.ClickBuilder()
                .withClickId(clickId1)
                .withUserId(userId1)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValueTopCampaign.PREMIUM.getNumVal()))
                .build();

        String date = timeService.giveDateInSeconds();

        Click click2 = new Click.ClickBuilder()
                .withClickId(clickId2)
                .withUserId(userId2)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValueTopCampaign.PREMIUM.getNumVal()))
                .build();

        Click click3 = new Click.ClickBuilder()
                .withClickId(clickId3)
                .withUserId(userId3)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValueTopCampaign.PREMIUM.getNumVal()))
                .build();

        Map<UserId, Click> clickMap = new HashMap<>();
        clickMap.put(userId1, click1);
        clickMap.put(userId2, click2);
        clickMap.put(userId3, click3);

        when(clickRepository.getMap()).thenReturn(clickMap, clickMap, clickMap);

        List<UserId> botClicks = new ArrayList<>();
        botClicks.add(userId2);

        campaignService.verifyClicks(campaign, botClicks, date);

        Assert.assertEquals(campaignExpected, campaign);
    }
    @Test
    public void topCampaing_with_fraudulentClicks_and_higher_clickCharged_than_five_percent() {
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(0.08);
        Campaign campaign = new TopCampaign(campaignId, budget, new Activated());
        Budget budgetExpected = new Budget(0.06);
        Campaign campaignExpected = new TopCampaign(campaignId, budgetExpected, new Activated());

        ClickId clickId1 = new ClickId(UUID.randomUUID());
        ClickId clickId2 = new ClickId(UUID.randomUUID());
        ClickId clickId3 = new ClickId(UUID.randomUUID());
        UserId userId1 = new UserId(UUID.randomUUID());
        UserId userId2 = new UserId(UUID.randomUUID());
        UserId userId3 = new UserId(UUID.randomUUID());

        when(timeService.giveDateInSeconds()).thenReturn("0", "30", "60", "90");

        Click click1 = new Click.ClickBuilder()
                .withClickId(clickId1)
                .withUserId(userId1)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValueTopCampaign.PREMIUM.getNumVal()))
                .build();

        String date = timeService.giveDateInSeconds();

        Click click2 = new Click.ClickBuilder()
                .withClickId(clickId2)
                .withUserId(userId2)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValueTopCampaign.PREMIUM.getNumVal()))
                .build();

        Click click3 = new Click.ClickBuilder()
                .withClickId(clickId3)
                .withUserId(userId3)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValueTopCampaign.PREMIUM.getNumVal()))
                .build();

        Map<UserId, Click> clickMap = new HashMap<>();
        clickMap.put(userId1, click1);
        clickMap.put(userId2, click2);
        clickMap.put(userId3, click3);

        when(clickRepository.getMap()).thenReturn(clickMap, clickMap, clickMap);

        List<UserId> botClicks = new ArrayList<>();
        botClicks.add(userId2);

        campaignService.verifyClicks(campaign, botClicks, date);

        Assert.assertEquals(campaignExpected, campaign);
    }
    @Test
    public void standardCampaing_with_fraudulentClicks() {
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(0.08);
        StandardCampaign campaign = new StandardCampaign(campaignId, budget, new Activated());
        Budget budgetExpected = new Budget(0.06);
        StandardCampaign campaignExpected = new StandardCampaign(campaignId, budgetExpected, new Activated());

        ClickId clickId1 = new ClickId(UUID.randomUUID());
        ClickId clickId2 = new ClickId(UUID.randomUUID());
        ClickId clickId3 = new ClickId(UUID.randomUUID());
        UserId userId1 = new UserId(UUID.randomUUID());
        UserId userId2 = new UserId(UUID.randomUUID());
        UserId userId3 = new UserId(UUID.randomUUID());

        when(timeService.giveDateInSeconds()).thenReturn("0", "30", "60", "90");

        Click click1 = new Click.ClickBuilder()
                .withClickId(clickId1)
                .withUserId(userId1)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        String date = timeService.giveDateInSeconds();

        Click click2 = new Click.ClickBuilder()
                .withClickId(clickId2)
                .withUserId(userId2)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        Click click3 = new Click.ClickBuilder()
                .withClickId(clickId3)
                .withUserId(userId3)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        Map<UserId, Click> clickMap = new HashMap<>();
        clickMap.put(userId1, click1);
        clickMap.put(userId2, click2);
        clickMap.put(userId3, click3);

        when(clickRepository.getMap()).thenReturn(clickMap, clickMap, clickMap);

        List<UserId> botClicks = new ArrayList<>();
        botClicks.add(userId2);

        campaignService.verifyClicks(campaign, botClicks, date);

        Assert.assertEquals(campaignExpected, campaign);
    }
    @Test
    public void demoCampaing_with_fraudulentClicks() {
        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(0.08);
        DemoCampaign campaign = new DemoCampaign(campaignId, budget, new Activated());
        Budget budgetExpected = new Budget(0.08);
        DemoCampaign campaignExpected = new DemoCampaign(campaignId, budgetExpected, new Activated());

        ClickId clickId1 = new ClickId(UUID.randomUUID());
        ClickId clickId2 = new ClickId(UUID.randomUUID());
        ClickId clickId3 = new ClickId(UUID.randomUUID());
        UserId userId1 = new UserId(UUID.randomUUID());
        UserId userId2 = new UserId(UUID.randomUUID());
        UserId userId3 = new UserId(UUID.randomUUID());

        when(timeService.giveDateInSeconds()).thenReturn("0", "30", "60", "90");

        Click click1 = new Click.ClickBuilder()
                .withClickId(clickId1)
                .withUserId(userId1)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        String date = timeService.giveDateInSeconds();

        Click click2 = new Click.ClickBuilder()
                .withClickId(clickId2)
                .withUserId(userId2)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        Click click3 = new Click.ClickBuilder()
                .withClickId(clickId3)
                .withUserId(userId3)
                .withInstantTime(new InstantTime(timeService.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        Map<UserId, Click> clickMap = new HashMap<>();
        clickMap.put(userId1, click1);
        clickMap.put(userId2, click2);
        clickMap.put(userId3, click3);

        when(clickRepository.getMap()).thenReturn(clickMap, clickMap, clickMap);

        List<UserId> botClicks = new ArrayList<>();
        botClicks.add(userId2);

        campaignService.verifyClicks(campaign, botClicks, date);

        Assert.assertEquals(campaignExpected, campaign);
    }

}
