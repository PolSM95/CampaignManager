package unit;

import CampaignManager.domain.entities.Click;
import CampaignManager.domain.entities.ClickValue;
import CampaignManager.domain.entities.valueObjects.click.ClickId;
import CampaignManager.domain.entities.valueObjects.click.InstantTime;
import CampaignManager.domain.entities.valueObjects.click.TypeValue;
import CampaignManager.domain.entities.valueObjects.click.UserId;
import CampaignManager.domain.exceptions.clickIsDuplicatedException;
import CampaignManager.infrastructure.ClickRepositoryInMemory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClickRepositoryInMemoryTest {
    @Test
    public void click_is_duplicated_when_less_than_fifteen_seconds_passed(){
        ClickRepositoryInMemory clickRepositoryInMemory = new ClickRepositoryInMemory();

        ClickId clickId1 = new ClickId(UUID.randomUUID());
        ClickId clickId2 = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        Click click1 = new Click.ClickBuilder()
                .withClickId(clickId1)
                .withUserId(userId)
                .withInstantTime(new InstantTime("1588922589"))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();
        Click click2 = new Click.ClickBuilder()
                .withClickId(clickId2)
                .withUserId(userId)
                .withInstantTime(new InstantTime("1588922590"))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        clickRepositoryInMemory.addClick(userId, click1);

        assertThrows(clickIsDuplicatedException.class, () -> clickRepositoryInMemory.addClick(userId, click2));

    }
    @Test
    public void clicks_are_added_successfully(){
        ClickRepositoryInMemory clickRepositoryInMemory = new ClickRepositoryInMemory();

        ClickId clickId1 = new ClickId(UUID.randomUUID());
        ClickId clickId2 = new ClickId(UUID.randomUUID());
        UserId userId = new UserId(UUID.randomUUID());

        Click click1 = new Click.ClickBuilder()
                .withClickId(clickId1)
                .withUserId(userId)
                .withInstantTime(new InstantTime("1588922589"))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();
        Click click2 = new Click.ClickBuilder()
                .withClickId(clickId2)
                .withUserId(userId)
                .withInstantTime(new InstantTime("1588922620"))
                .withClickType(new TypeValue(ClickValue.NORMAL.getNumVal()))
                .build();

        clickRepositoryInMemory.addClick(userId, click1);
        clickRepositoryInMemory.addClick(userId, click2);

        Map<UserId, Click> clickMapExpected = new HashMap<>();
        clickMapExpected.put(userId, click1);
        clickMapExpected.put(userId, click2);

        assertEquals(clickMapExpected, clickRepositoryInMemory.clickMap);
    }
}
