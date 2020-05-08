package CampaignManager.domain.domainServices;

import CampaignManager.domain.ClickRepository;
import CampaignManager.domain.domainServices.timeService.TimeService;
import CampaignManager.domain.domainServices.uuid.UUIDInterface;
import CampaignManager.domain.entities.Click;
import CampaignManager.domain.entities.valueObjects.click.ClickId;
import CampaignManager.domain.entities.valueObjects.click.InstantTime;
import CampaignManager.domain.entities.valueObjects.click.UserId;

public class ClickService {
    private final TimeService timeService;
    private final UUIDInterface uuidInterface;
    private final ClickRepository clickRepository;

    public ClickService(TimeService timeService, UUIDInterface uuidInterface, ClickRepository clickRepository) {
        this.timeService = timeService;
        this.uuidInterface = uuidInterface;
        this.clickRepository = clickRepository;
    }

    public void createClick(UserId userId){
        ClickId clickId = new ClickId(uuidInterface.generateUUID());
        InstantTime instantTime = new InstantTime(timeService.giveDateInSeconds());
        Click click = new Click.ClickBuilder()
                .withClickId(clickId)
                .withUserId(userId)
                .withInstantTime(instantTime)
                .build();
        clickRepository.addClick(userId, click);
    }
}
