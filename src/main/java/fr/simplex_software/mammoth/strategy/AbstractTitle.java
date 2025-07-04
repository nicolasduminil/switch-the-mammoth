package fr.simplex_software.mammoth.strategy;

import fr.simplex_software.mammoth.exceptions.*;
import fr.simplex_software.mammoth.model.*;

import java.math.*;
import java.util.*;

public abstract class AbstractTitle extends Observable implements Derivative
{
  protected TitleType titleType;

  protected AbstractTitle(TitleType titleType)
  {
    this.titleType = titleType;
  }

  public BigDecimal recalculate(CalculationData calculationData) throws IllegalTitleTypeException
  {
    BigDecimal titlePrice = performCalculations(calculationData);
    this.notifyObservers();
    return titlePrice;
  }

  public abstract BigDecimal performCalculations(CalculationData calculationData) throws IllegalTitleTypeException;
}
