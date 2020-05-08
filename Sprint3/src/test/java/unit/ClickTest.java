package unit;

import CampaignManager.domain.entities.Click;
import CampaignManager.domain.exceptions.ClickIdIsNotDefinedException;
import CampaignManager.domain.exceptions.TimeInstantIsNotDefinedException;
import CampaignManager.domain.exceptions.UserIdIsNotDefinedException;
import CampaignManager.domain.entities.valueObjects.click.ClickId;
import CampaignManager.domain.entities.valueObjects.click.UserId;
import CampaignManager.domain.domainServices.timeService.TimeFormater;
import CampaignManager.domain.domainServices.uuid.UUIDGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClickTest {

    UUIDGenerator uuid;
    TimeFormater timeFormater;

    @Before
    public void setup(){
        uuid = mock(UUIDGenerator.class);
        timeFormater = mock(TimeFormater.class);

    }
    @Test
    public void click_cannot_be_initialized_without_a_clickId(){

        assertThrows(ClickIdIsNotDefinedException.class, () -> new Click.ClickBuilder().build());

    }

    @Test
    public void click_cannot_be_initialized_without_an_userId(){

        when(uuid.generateUUID()).thenReturn(UUID.randomUUID());

        ClickId clickId = new ClickId(UUID.randomUUID());

        assertThrows(UserIdIsNotDefinedException.class, () -> new Click.ClickBuilder()
                .withClickId(clickId).build());
    }
    @Test
    public void click_cannot_be_initialized_without_an_TimeInstant(){

        when(uuid.generateUUID()).thenReturn(UUID.randomUUID(),UUID.randomUUID());

        ClickId clickId = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        assertThrows(TimeInstantIsNotDefinedException.class, () -> new Click.ClickBuilder()
                .withClickId(clickId)
                .withUserId(userId)
                .build());
    }


}
