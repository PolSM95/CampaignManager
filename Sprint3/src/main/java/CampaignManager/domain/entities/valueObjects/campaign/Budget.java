package CampaignManager.domain.entities.valueObjects.campaign;

import CampaignManager.domain.DTOs.campaign.BudgetDTO;

import java.util.Objects;

public class Budget {
    private double budget;

    public Budget(double budget) {
        this.budget = budget;
    }

    public BudgetDTO serialize(){
        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.budget = budget;
        return budgetDTO;
    }

    public double getBudget() {
        return budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget1 = (Budget) o;
        return Double.compare(budget1.budget, budget) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(budget);
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budget=" + budget +
                '}';
    }
}
