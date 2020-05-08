package CampaignManager.domain.DTOs.campaign;

import CampaignManager.domain.entities.valueObjects.campaign.Budget;

import java.util.Objects;

public class BudgetDTO {
    public double budget;

    public Budget deserialize(){
        Budget budget = new Budget(this.budget);

        return budget;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetDTO budgetDTO = (BudgetDTO) o;
        return Double.compare(budgetDTO.budget, budget) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(budget);
    }
}
