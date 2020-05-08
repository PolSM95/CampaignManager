package CampaignManager.domain.entities;

import CampaignManager.domain.DTOs.click.ClickDTO;
import CampaignManager.domain.DTOs.click.InstantTimeDTO;
import CampaignManager.domain.exceptions.ClickIdIsNotDefinedException;
import CampaignManager.domain.exceptions.TimeInstantIsNotDefinedException;
import CampaignManager.domain.exceptions.UserIdIsNotDefinedException;
import CampaignManager.domain.entities.valueObjects.click.ClickId;
import CampaignManager.domain.entities.valueObjects.click.TypeValue;
import CampaignManager.domain.entities.valueObjects.click.InstantTime;
import CampaignManager.domain.entities.valueObjects.click.UserId;


import java.util.Objects;

public class Click {

    private ClickId clickId;
    private UserId userId;
    private InstantTime instant;
    private TypeValue type;

    private Click() {}

    public ClickDTO serialize(){
        ClickDTO clickDTO = new ClickDTO();
        clickDTO.clickId = clickId.serialize();
        clickDTO.userId = userId.serialize();
        clickDTO.instant = instant.serialize();
        clickDTO.type = type.serialize();

        return clickDTO;
    }

    public static class ClickBuilder{
        private ClickId clickId;
        private UserId userId;
        private InstantTime instant;
        private TypeValue type;

        public ClickBuilder withClickId(ClickId clickId){
            this.clickId = clickId;

            return this;
        }

        public ClickBuilder withUserId(UserId userId){
            this.userId = userId;

            return this;
        }

        public ClickBuilder withInstantTime(InstantTime instant){
            this.instant = instant;

            return this;
        }

        public ClickBuilder withClickType(TypeValue type){
            this.type = type;


            return this;
        }
         public Click build(){
            Click click = new Click();
            if(this.clickId == null){
                throw new ClickIdIsNotDefinedException();
            }
            if(this.userId == null){
                throw new UserIdIsNotDefinedException();
            }
            if(this.instant == null){
                throw new TimeInstantIsNotDefinedException();
            }
            if(this.type == null){
                this.type = new TypeValue(ClickValue.NORMAL.getNumVal());
            }
            click.clickId = this.clickId;
            click.userId = this.userId;
            click.instant = this.instant;
            click.type = this.type;

            return click;
         }

    }

    public boolean instantNotDuplicated(Click click){
        InstantTimeDTO instantTimeDTOOld = this.serialize().instant;
        InstantTimeDTO instantTimeDTONew = click.serialize().instant;
        long a = (Long.parseLong(instantTimeDTONew.instant) - Long.parseLong(instantTimeDTOOld.instant));
        return ((Long.parseLong(instantTimeDTONew.instant) - Long.parseLong(instantTimeDTOOld.instant)) > 15);
    }
    public boolean clickIsAfterDate(String date){
        InstantTimeDTO instantTimeDTO = this.serialize().instant;
        return (Double.parseDouble(instantTimeDTO.instant) < Double.parseDouble(date));
    }

    public double getType() {
        return type.serialize().type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Click click = (Click) o;
        return Objects.equals(clickId, click.clickId) &&
                Objects.equals(userId, click.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clickId, userId, instant, type);
    }
}
