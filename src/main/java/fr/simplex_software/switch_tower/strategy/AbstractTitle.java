package fr.simplex_software.switch_tower.strategy;

import fr.simplex_software.switch_tower.exceptions.*;
import fr.simplex_software.switch_tower.model.*;

import java.math.*;

public abstract class AbstractTitle implements RecalculateStrategy
{
  protected TitleType titleType;

  protected AbstractTitle(TitleType titleType)
  {
    this.titleType = titleType;
  }

  public abstract BigDecimal recalculate(CalculationData calculationData) throws IllegalTitleTypeException;
}
