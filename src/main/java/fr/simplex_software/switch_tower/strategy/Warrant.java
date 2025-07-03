package fr.simplex_software.switch_tower.strategy;

import fr.simplex_software.switch_tower.exceptions.*;
import fr.simplex_software.switch_tower.model.*;

import java.math.*;

public class Warrant extends AbstractTitle
{
  protected Warrant(TitleType titleType)
  {
    super(titleType);
  }

  // Warrant value = option value * dilution factor
  @Override
  public BigDecimal recalculate(CalculationData calculationData) throws IllegalTitleTypeException
  {
    BigDecimal optionValue = calculationData.spotPrice()
      .subtract(calculationData.strikePrice()).max(BigDecimal.ZERO);
    BigDecimal dilutionFactor = new BigDecimal("0.95"); // 5% dilution
    return optionValue.multiply(dilutionFactor);
  }

  @Override
  public TitleType getTitleType()
  {
    return TitleType.WARRANT;
  }
}
