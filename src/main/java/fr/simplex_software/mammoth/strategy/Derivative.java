package fr.simplex_software.mammoth.strategy;

import fr.simplex_software.mammoth.exceptions.*;
import fr.simplex_software.mammoth.model.*;

import java.math.*;

public interface Derivative
{
  BigDecimal performCalculations(CalculationData calculationData) throws IllegalTitleTypeException;
  TitleType getTitleType();
}
