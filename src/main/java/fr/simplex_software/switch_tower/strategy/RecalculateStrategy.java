package fr.simplex_software.switch_tower.strategy;

import fr.simplex_software.switch_tower.exceptions.*;
import fr.simplex_software.switch_tower.model.*;

import java.math.*;

public interface RecalculateStrategy
{
  BigDecimal recalculate (CalculationData calculationData) throws IllegalTitleTypeException;
  TitleType getTitleType();
}
