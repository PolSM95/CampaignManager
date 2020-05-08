package CampaignManager.domain.domainServices;

import CampaignManager.domain.DTOs.campaign.BudgetDTO;
import CampaignManager.domain.DTOs.campaign.CampaignDTO;
import CampaignManager.domain.DTOs.click.ClickDTO;
import CampaignManager.domain.DTOs.click.ClickTypeDTO;
import CampaignManager.domain.domainServices.timeService.TimeService;
import CampaignManager.domain.domainServices.uuid.UUIDInterface;
import CampaignManager.domain.entities.*;
import CampaignManager.domain.entities.campaignStates.Activated;
import CampaignManager.domain.entities.valueObjects.campaign.Budget;
import CampaignManager.domain.entities.valueObjects.campaign.CampaignId;
import CampaignManager.domain.entities.valueObjects.click.UserId;
import CampaignManager.domain.exceptions.CampaignIsPausedException;
import CampaignManager.domain.exceptions.CannotChargeMoreThanTheBudgetException;
import CampaignManager.infrastructure.CampaignRepositoryInMemory;
import CampaignManager.infrastructure.ClickRepositoryInMemory;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class CampaignService {

    private final TimeService timeService;
    private final UUIDInterface uuidInterface;
    private final CampaignRepositoryInMemory campaignRepository;
    private final ClickRepositoryInMemory clickRepository;

    public CampaignService(TimeService timeService, UUIDInterface uuidInterface, CampaignRepositoryInMemory campaignRepository, ClickRepositoryInMemory clickRepository) {
        this.timeService = timeService;
        this.uuidInterface = uuidInterface;
        this.campaignRepository = campaignRepository;
        this.clickRepository = clickRepository;
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
        clickRepository.addClick(new UserId(UUID.fromString(clickDTO.userId.userId)), click);

        campaign.updateBudget(clickTypeDTO.type);
        return campaign;
    }

    public Campaign verifyClicks(Campaign campaign, List<UserId> userList, String date) {
        double resultBeforeDate = 0;
        double resultRealClicks = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.US));
        BudgetDTO resultBudgetDTO = new BudgetDTO();

        if (!(campaign instanceof DemoCampaign)) {
            resultBeforeDate = calculateClicksBefore(date);
            removeFraudulentClicks(userList, date);
            resultRealClicks = calculateClicks();

            if (campaign instanceof TopCampaign) {
                resultBudgetDTO = campaign.serialize().budgetDTO;
                if ((resultBudgetDTO.budget * 0.05) > resultBeforeDate) {
                    resultBudgetDTO.budget -= (resultBeforeDate + resultRealClicks);
                }
                if ((resultBudgetDTO.budget * 0.05) < resultBeforeDate) {
                    resultBudgetDTO.budget -= resultBeforeDate;
                }
            }

            if (campaign instanceof StandardCampaign) {
                resultBudgetDTO = campaign.serialize().budgetDTO;
                resultBudgetDTO.budget -= (resultBeforeDate + resultRealClicks);
            }

            Budget budget = resultBudgetDTO.deserialize();
            campaign.setBudget(budget);
        }
        return campaign;
    }

    private double calculateClicksBefore(String date) {
        double result = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.US));
        for(Map.Entry<UserId, Click> actualClickMap : clickRepository.getMap().entrySet()){
            if((actualClickMap.getValue().clickIsAfterDate(date))) {
                result += actualClickMap.getValue().getType();
            }
        }
        return Double.parseDouble(decimalFormat.format(result));
    }
    private double calculateClicks() {
        double result = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.US));
        for(Map.Entry<UserId, Click> actualClickMap : clickRepository.getMap().entrySet()){
            result += actualClickMap.getValue().getType();
        }
        return Double.parseDouble(decimalFormat.format(result));
    }

    private void removeFraudulentClicks(List<UserId> userList, String date) {
        for(UserId userId : userList){
            clickRepository.getMap().entrySet().removeIf(e ->
                    e.getKey().equals(userId) || e.getValue().clickIsAfterDate(date)
            );
        }
    }


}
