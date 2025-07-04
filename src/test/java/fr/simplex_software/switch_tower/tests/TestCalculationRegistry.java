package fr.simplex_software.switch_tower.tests;

import fr.simplex_software.mammoth.model.*;
import fr.simplex_software.mammoth.registry.*;
import org.junit.jupiter.api.*;

import java.math.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestCalculationRegistry
{
  private CalculationRegistry<TitleType, CalculationData> calculationRegistry;
  private CalculationData testData;

  @BeforeEach
  public void beforeEach()
  {
    calculationRegistry = new CalculationRegistry<>();
    testData = new CalculationData(
      new BigDecimal("100"),    // spotPrice
      new BigDecimal("95"),     // strikePrice
      new BigDecimal("0.2"),    // volatility
      new BigDecimal("0.25"),   // timeToExpiry
      new BigDecimal("0.05"),   // riskFreeRate
      new BigDecimal("0.03"),   // fixedRate
      new BigDecimal("0.02"),   // floatingRate
      new BigDecimal("1000000") // notional
    );
  }

  @Test
  void testRegisterOptionStrategy()
  {
    calculationRegistry.register(TitleType.OPTION, data ->
    {
      BigDecimal intrinsicValue = data.spotPrice()
        .subtract(data.strikePrice()).max(BigDecimal.ZERO);
      BigDecimal timeValue = data.volatility()
        .multiply(data.timeToExpiry())
        .multiply(new BigDecimal("0.1"));
      return intrinsicValue.add(timeValue);
    });
    assertNotEquals(0, calculationRegistry.getStrategies().size());
    assertTrue(calculationRegistry.getStrategies().containsKey(TitleType.OPTION));
    assertTrue(calculationRegistry.apply(TitleType.OPTION, testData).compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void testRegisterForwardStrategy()
  {
    calculationRegistry.register(TitleType.FORWARD, data ->
    {
      BigDecimal rateComponent = BigDecimal.ONE
        .add(data.riskFreeRate()
          .multiply(data.timeToExpiry()));
      return data.spotPrice().multiply(rateComponent);
    });
    assertNotEquals(0, calculationRegistry.getStrategies().size());
    assertTrue(calculationRegistry.getStrategies().containsKey(TitleType.FORWARD));
    assertTrue(calculationRegistry.apply(TitleType.FORWARD, testData).compareTo(new BigDecimal("100")) > 0);
  }

  @Test
  void testRegisterFutureStrategy()
  {
    calculationRegistry.register(TitleType.FUTURE, data ->
    {
      BigDecimal multiplier = BigDecimal.valueOf(Math.exp(data.riskFreeRate()
        .multiply(data.timeToExpiry()).doubleValue()));
      return data.spotPrice().multiply(multiplier);
    });
    assertNotEquals(0, calculationRegistry.getStrategies().size());
    assertTrue(calculationRegistry.getStrategies().containsKey(TitleType.FUTURE));
    assertTrue(calculationRegistry.apply(TitleType.FUTURE, testData).compareTo(new BigDecimal("100")) > 0);
  }

  @Test
  void testRegisterSwapStrategy()
  {
    calculationRegistry.register(TitleType.SWAP, data ->
    {
      BigDecimal rateDiff = data.fixedRate()
        .subtract(data.floatingRate());
      return data.notional()
        .multiply(rateDiff)
        .multiply(data.timeToExpiry());
    });
    assertNotEquals(0, calculationRegistry.getStrategies().size());
    assertTrue(calculationRegistry.getStrategies().containsKey(TitleType.SWAP));
    assertTrue(calculationRegistry.apply(TitleType.SWAP, testData).compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void testRegisterWarrantStrategy()
  {
    calculationRegistry.register(TitleType.WARRANT, data ->
    {
      BigDecimal optionValue = data.spotPrice()
        .subtract(data.strikePrice()).max(BigDecimal.ZERO);
      BigDecimal dilutionFactor = new BigDecimal("0.95"); // 5% dilution
      return optionValue.multiply(dilutionFactor);
    });
    assertNotEquals(0, calculationRegistry.getStrategies().size());
    assertTrue(calculationRegistry.getStrategies().containsKey(TitleType.WARRANT));
    assertTrue(calculationRegistry.apply(TitleType.WARRANT, testData).compareTo(BigDecimal.ZERO) > 0);
  }
}
