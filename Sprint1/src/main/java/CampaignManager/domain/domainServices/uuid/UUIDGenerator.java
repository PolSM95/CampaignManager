package CampaignManager.domain.domainServices.uuid;

import java.util.UUID;

public class UUIDGenerator implements UUIDInterface {
    @Override
    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
