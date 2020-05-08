package acceptance;

import CampaignManager.domain.domainServices.CampaignService;
import CampaignManager.domain.domainServices.timeService.TimeFormater;
import CampaignManager.domain.domainServices.timeService.TimeService;
import CampaignManager.domain.domainServices.uuid.UUIDGenerator;
import CampaignManager.domain.entities.*;
import CampaignManager.domain.entities.campaignStates.Activated;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;
import CampaignManager.domain.entities.valueObjects.click.ClickId;
import CampaignManager.domain.entities.valueObjects.click.InstantTime;
import CampaignManager.domain.entities.valueObjects.click.TypeValue;
import CampaignManager.domain.entities.valueObjects.click.UserId;
import CampaignManager.infrastructure.CampaignRepositoryInMemory;
import CampaignManager.infrastructure.ClickRepositoryInMemory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CampaignManagerRunTest {
    TimeFormater timeFormater;
    UUIDGenerator uuid;
    CampaignRepositoryInMemory campaignRepository;
    ClickRepositoryInMemory clickRepository;
    CampaignService campaignService;


    @Test
    public void running_campaingManager_for_every_type_of_click(){
        timeFormater = mock(TimeFormater.class);
        uuid = new UUIDGenerator();
        campaignRepository = new CampaignRepositoryInMemory();
        clickRepository = new ClickRepositoryInMemory();
        campaignService = new CampaignService(timeFormater, uuid, campaignRepository, clickRepository);

        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new Campaign(campaignId, budget);
        Budget budgetExpected = new Budget(99.94);
        Campaign campaignExpected = new Campaign(campaignId, budgetExpected);


        ClickId clickId1 = new ClickId(UUID.randomUUID());
        ClickId clickId2 = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        when(timeFormater.giveDateInSeconds()).thenReturn("0","16");

        Click click1 = new Click.ClickBuilder()
                .withClickId(clickId1)
                .withUserId(userId)
                .withInstantTime(new InstantTime(timeFormater.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();
        Click click2 = new Click.ClickBuilder()
                .withClickId(clickId2)
                .withUserId(userId)
                .withInstantTime(new InstantTime(timeFormater.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValue.PREMIUM.getNumVal()))
                .build();

        campaignService.activateCampaign(campaign);
        campaignService.chargeClick(campaign, click1);

        Assert.assertEquals(campaignExpected,campaignService.chargeClick(campaign, click2));
    }

    @Test
    public void campaignManager_can_detect_bot_clicks_and_charge_the_real_ones(){
        timeFormater = mock(TimeFormater.class);
        uuid = new UUIDGenerator();
        campaignRepository = new CampaignRepositoryInMemory();
        clickRepository = new ClickRepositoryInMemory();
        campaignService = new CampaignService(timeFormater, uuid, campaignRepository, clickRepository);

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

        when(timeFormater.giveDateInSeconds()).thenReturn("0","30","60","90");

        Click click1 = new Click.ClickBuilder()
                .withClickId(clickId1)
                .withUserId(userId1)
                .withInstantTime(new InstantTime(timeFormater.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValueTopCampaign.PREMIUM.getNumVal()))
                .build();

        String date = timeFormater.giveDateInSeconds();

        Click click2 = new Click.ClickBuilder()
                .withClickId(clickId2)
                .withUserId(userId2)
                .withInstantTime(new InstantTime(timeFormater.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValueTopCampaign.PREMIUM.getNumVal()))
                .build();

        Click click3 = new Click.ClickBuilder()
                .withClickId(clickId3)
                .withUserId(userId3)
                .withInstantTime(new InstantTime(timeFormater.giveDateInSeconds()))
                .withClickType(new TypeValue(ClickValueTopCampaign.PREMIUM.getNumVal()))
                .build();

        List<UserId> botClicks = new ArrayList<>();
        botClicks.add(userId2);


        campaignService.chargeClick(campaign, click1);
        campaignService.chargeClick(campaign, click2);
        campaignService.chargeClick(campaign, click3);

        campaignService.verifyClicks(campaign, botClicks, date);

        Assert.assertEquals(campaignExpected, campaign);
    }
}
