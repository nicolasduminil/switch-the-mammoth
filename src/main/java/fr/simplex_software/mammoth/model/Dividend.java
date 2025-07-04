package fr.simplex_software.mammoth.model;

import java.math.*;
import java.time.*;

public record Dividend (LocalDateTime dateTime, BigDecimal amount)
{
}
