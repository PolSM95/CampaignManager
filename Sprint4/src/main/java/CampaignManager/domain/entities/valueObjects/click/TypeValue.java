package CampaignManager.domain.entities.valueObjects.click;

import CampaignManager.domain.DTOs.click.ClickTypeDTO;

import java.util.Objects;

public class TypeValue {
    private double type;

    public TypeValue(double type) {
        this.type = type;
    }

    public ClickTypeDTO serialize(){
        ClickTypeDTO clickTypeDTO = new ClickTypeDTO();
        clickTypeDTO.type = type;
        return clickTypeDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeValue typeValue = (TypeValue) o;
        return Objects.equals(type, typeValue.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
