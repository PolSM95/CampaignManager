package CampaignManager.domain.domainServices;

import CampaignManager.domain.CampaignRepository;
import CampaignManager.domain.DTOs.campaign.BudgetDTO;
import CampaignManager.domain.DTOs.campaign.CampaignDTO;
import CampaignManager.domain.DTOs.click.ClickDTO;
import CampaignManager.domain.DTOs.click.ClickTypeDTO;
import CampaignManager.domain.domainServices.timeService.TimeService;
import CampaignManager.domain.domainServices.uuid.UUIDInterface;
import CampaignManager.domain.entities.Campaign;
import CampaignManager.domain.entities.Click;
import CampaignManager.domain.entities.campaignStates.Activated;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;
import CampaignManager.domain.exceptions.CampaignIsPausedException;
import CampaignManager.domain.exceptions.CannotChargeMoreThanTheBudgetException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CampaignService {

    private final TimeService timeService;
    private final UUIDInterface uuidInterface;
    private final CampaignRepository campaignRepository;


    public CampaignService(TimeService timeService, UUIDInterface uuidInterface, CampaignRepository campaignRepository) {
        this.timeService = timeService;
        this.uuidInterface = uuidInterface;
        this.campaignRepository = campaignRepository;
    }



    public void createCampaign(Budget budget){
        CampaignId campaignId = new CampaignId(uuidInterface.generateUUID());
        Campaign campaign = new Campaign(campaignId, budget);
        campaignRepository.addCampaign(campaign);
    }

    public void activateCampaign(Campaign campaign){
        campaign.activate();
    }

    public void pauseCampaign(Campaign campaign){
        campaign.pause();
    }

    public Campaign chargeClick(Campaign campaign, Click click){
        if(!(campaign.getState() instanceof Activated)){
            throw new CampaignIsPausedException();
        }

        CampaignDTO campaignDTO = campaign.serialize();
        BudgetDTO budgetDTO = campaignDTO.budgetDTO;

        ClickDTO clickDTO = click.serialize();
        ClickTypeDTO clickTypeDTO = clickDTO.type;

        if(budgetDTO.budget < clickTypeDTO.type){
            throw new CannotChargeMoreThanTheBudgetException();
        }

        campaign.updateBudget(clickTypeDTO.type);



        return campaign;
    }


}
