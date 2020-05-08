package CampaignManager.domain.domainServices.timeService;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeFormater implements TimeService {
    @Override
    public String formatDate() {
        Date actualTime = new Date();
        long time = actualTime.getTime();
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(time));
    }
}
