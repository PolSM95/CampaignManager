package acceptance;

import CampaignManager.domain.domainServices.CampaignService;
import CampaignManager.domain.domainServices.timeService.TimeFormater;
import CampaignManager.domain.domainServices.timeService.TimeService;
import CampaignManager.domain.domainServices.uuid.UUIDGenerator;
import CampaignManager.domain.entities.Campaign;
import CampaignManager.domain.entities.Click;
import CampaignManager.domain.entities.ClickValue;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;
import CampaignManager.domain.entities.valueObjects.click.ClickId;
import CampaignManager.domain.entities.valueObjects.click.InstantTime;
import CampaignManager.domain.entities.valueObjects.click.TypeValue;
import CampaignManager.domain.entities.valueObjects.click.UserId;
import CampaignManager.infrastructure.CampaignRepositoryInMemory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CampaignManagerRunTest {
    TimeService timeFormater;
    UUIDGenerator uuid;
    CampaignRepositoryInMemory campaignRepository;
    CampaignService campaignService;

    @Test
    public void running_campaingManager_for_every_type_of_click(){
        timeFormater = new TimeFormater();
        uuid = new UUIDGenerator();
        campaignRepository = new CampaignRepositoryInMemory();
        campaignService = new CampaignService(timeFormater, uuid, campaignRepository);

        UUID uuidExpected = UUID.randomUUID();
        CampaignId campaignId = new CampaignId(uuidExpected);
        Budget budget = new Budget(100);
        Campaign campaign = new Campaign(campaignId, budget);
        Budget budgetExpected = new Budget(99.94);
        Campaign campaignExpected = new Campaign(campaignId, budgetExpected);


        ClickId clickId1 = new ClickId(UUID.randomUUID());
        ClickId clickId2 = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());


        InstantTime instantTime = new InstantTime(timeFormater.formatDate());

        Click click1 = new Click.ClickBuilder()
                .withClickId(clickId1)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();
        Click click2 = new Click.ClickBuilder()
                .withClickId(clickId2)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .withClickType(new TypeValue(ClickValue.PREMIUM.getNumVal()))
                .build();


        campaignService.activateCampaign(campaign);
        campaignService.chargeClick(campaign, click1);

        Assert.assertEquals(campaignExpected,campaignService.chargeClick(campaign, click2));
    }

}
