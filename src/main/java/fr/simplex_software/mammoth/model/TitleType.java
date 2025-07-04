package fr.simplex_software.mammoth.model;

public enum TitleType
{
  OPTION, FUTURE, FORWARD, SWAP, WARRANT;

  public static TitleType fromString (String value)
  {
    return TitleType.valueOf (value.toUpperCase());
  }
}
