package fr.simplex_software.switch_tower.model;

import java.math.*;

public record CalculationData(
  BigDecimal spotPrice,
  BigDecimal strikePrice,
  BigDecimal volatility,
  BigDecimal timeToExpiry,
  BigDecimal riskFreeRate,
  BigDecimal fixedRate,
  BigDecimal floatingRate,
  BigDecimal notional
)
{}
