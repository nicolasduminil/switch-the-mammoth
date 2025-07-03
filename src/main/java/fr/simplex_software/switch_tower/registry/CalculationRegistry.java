package fr.simplex_software.switch_tower.registry;

import java.math.*;
import java.util.*;
import java.util.function.*;

public class CalculationRegistry<T, R>
{
  private final Map<T, Function<R, BigDecimal>> strategies = new HashMap<>();

  public void register(T key, Function<R, BigDecimal> strategyFunc)
  {
    strategies.put(key, strategyFunc);
  }

  public BigDecimal apply(T key, R request)
  {
    return strategies.getOrDefault(key, r -> BigDecimal.ZERO).apply(request);
  }

  public void unregister(T key)
  {
    strategies.remove(key);
  }

  public void clear()
  {
    strategies.clear();
  }

  public Map<T, Function<R, BigDecimal>> getStrategies()
  {
    return strategies;
  }
}
