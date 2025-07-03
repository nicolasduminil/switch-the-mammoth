package fr.simplex_software.switch_tower.strategy;

import fr.simplex_software.switch_tower.exceptions.*;
import fr.simplex_software.switch_tower.model.*;

import java.math.*;

public class Swap extends AbstractTitle
{
  protected Swap(TitleType titleType)
  {
    super(titleType);
  }

  // Swap value = notional * (fixedRate - floatingRate) * timeToExpiry
  @Override
  public BigDecimal recalculate(CalculationData calculationData) throws IllegalTitleTypeException
  {
    BigDecimal rateDiff = calculationData.fixedRate()
      .subtract(calculationData.floatingRate());
    return calculationData.notional()
      .multiply(rateDiff)
      .multiply(calculationData.timeToExpiry());
  }

  @Override
  public TitleType getTitleType()
  {
    return TitleType.SWAP;
  }
}
