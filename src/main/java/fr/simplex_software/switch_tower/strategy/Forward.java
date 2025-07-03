package fr.simplex_software.switch_tower.strategy;

import fr.simplex_software.switch_tower.exceptions.*;
import fr.simplex_software.switch_tower.model.*;

import java.math.*;

public class Forward extends AbstractTitle
{
  protected Forward(TitleType titleType)
  {
    super(titleType);
  }

  // Forward value = spotPrice * (1 + riskFreeRate * timeToExpiry)
  @Override
  public BigDecimal recalculate(CalculationData calculationData) throws IllegalTitleTypeException
  {
    BigDecimal rateComponent = BigDecimal.ONE
      .add(calculationData.riskFreeRate()
      .multiply(calculationData.timeToExpiry()));
    return calculationData.spotPrice().multiply(rateComponent);
  }

  @Override
  public TitleType getTitleType()
  {
    return TitleType.FORWARD;
  }
}
