package fr.simplex_software.switch_tower.model;

import fr.simplex_software.switch_tower.exceptions.*;

import java.math.*;
import java.util.*;

public class Title extends Observable
{
  private final TitleType titleType;

  public Title(TitleType titleType)
  {
    this.titleType = titleType;
  }

  public BigDecimal recalculate (CalculationData calculationData) throws IllegalTitleTypeException
  {
    BigDecimal result = performRecalculations(calculationData);
    this.notifyObservers();
    return result;
  }

  public BigDecimal performRecalculations(CalculationData calculationData) throws IllegalTitleTypeException
  {
    BigDecimal subtract = calculationData.spotPrice()
      .subtract(calculationData.strikePrice());
    BigDecimal rate = calculationData.riskFreeRate()
      .multiply(calculationData.timeToExpiry());
    switch (titleType)
    {
      case OPTION:
        // Option value: max(0, spotPrice - strikePrice) + timeValue
        BigDecimal intrinsicValue = subtract.max(BigDecimal.ZERO);
        BigDecimal timeValue = calculationData.volatility()
          .multiply(calculationData.timeToExpiry())
          .multiply(new BigDecimal("0.1"));
        return intrinsicValue.add(timeValue);
      case FUTURE:
        // Future value = spotPrice * e^(riskFreeRate * timeToExpiry)
        BigDecimal multiplier = BigDecimal.valueOf(Math.exp(rate.doubleValue()));
        return calculationData.spotPrice().multiply(multiplier);
      case FORWARD:
        // Forward value = spotPrice * (1 + riskFreeRate * timeToExpiry)
        BigDecimal rateComponent = BigDecimal.ONE.add(rate);
        return calculationData.spotPrice().multiply(rateComponent);
      case SWAP:
        // Swap value = notional * (fixedRate - floatingRate) * timeToExpiry
        BigDecimal rateDiff = calculationData.fixedRate()
          .subtract(calculationData.floatingRate());
        return calculationData.notional()
          .multiply(rateDiff)
          .multiply(calculationData.timeToExpiry());
      case WARRANT:
        // Warrant value = option value * dilution factor
        BigDecimal optionValue = subtract.max(BigDecimal.ZERO);
        BigDecimal dilutionFactor = new BigDecimal("0.95"); // 5% dilution
        return optionValue.multiply(dilutionFactor);
      default:
        throw new IllegalTitleTypeException("### Illegal title type %s".formatted(titleType.name()));
    }
  }
}
