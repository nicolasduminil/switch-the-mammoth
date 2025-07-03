package fr.simplex_software.switch_tower.strategy;

import fr.simplex_software.switch_tower.exceptions.*;
import fr.simplex_software.switch_tower.model.*;

import java.math.*;

public class Future extends AbstractTitle
{
  protected Future(TitleType titleType)
  {
    super(titleType);
  }

  // Future value = spotPrice * e^(riskFreeRate * timeToExpiry)
  @Override
  public BigDecimal recalculate(CalculationData calculationData) throws IllegalTitleTypeException
  {
    BigDecimal multiplier = BigDecimal.valueOf(Math.exp(calculationData.riskFreeRate()
      .multiply(calculationData.timeToExpiry()).doubleValue()));
    return calculationData.spotPrice().multiply(multiplier);
  }

  @Override
  public TitleType getTitleType()
  {
    return TitleType.FUTURE;
  }
}
