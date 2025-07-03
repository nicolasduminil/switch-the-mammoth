package fr.simplex_software.switch_tower.strategy;

import fr.simplex_software.switch_tower.model.*;

import java.math.*;

public class Option extends AbstractTitle
{
  protected Option(TitleType titleType)
  {
    super(titleType);
  }

  // Option value: max(0, spotPrice - strikePrice) + timeValue
  @Override
  public BigDecimal recalculate(CalculationData calculationData)
  {
    BigDecimal intrinsicValue = calculationData.spotPrice()
      .subtract(calculationData.strikePrice()).max(BigDecimal.ZERO);
    BigDecimal timeValue = calculationData.volatility()
      .multiply(calculationData.timeToExpiry())
      .multiply(new BigDecimal("0.1"));
    return intrinsicValue.add(timeValue);
  }

  @Override
  public TitleType getTitleType()
  {
    return TitleType.OPTION;
  }
}
