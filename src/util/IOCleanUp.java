package util;

import java.util.function.IntPredicate;

/**
 * Personally verifies user input based on specified parameters
 * 
 * <p> This class was made in conjunction with the Deepseek LLM Software
 */
public final class IOCleanUp { 
  // Empty Private Constructor, meant to do absolutely nothing
  private IOCleanUp() {}

  /**
   * Private Method to begin lexically scanning a string
   * provided a specified condition to check from L->R
   * 
   * @param s The string to parse
   * @param condition boolean comparison to use based on primitive types
   * @return index of character that satisfies condition, or -1
   */
  private static int indexOfFirst(String s, IntPredicate condition) {
    for (int i = 0; i < s.length(); i++) {
      if (condition.test(s.charAt(i))) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Private Method to begin lexically scanning a string
   * provided a specified condition to check from R->L
   * 
   * @param s The string to parse
   * @param condition boolean comparison to use based on primitive types
   * @return index of character that satisfies condition, or -1
   */
  private static int indexOfLast(String s, IntPredicate condition) {
    for (int i = s.length() - 1; i > -1; i--) {
      if (condition.test(s.charAt(i))) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Removes all non-desired chars of a given string
   * going L->R (prefix characters) until first desired char is hit
   * 
   * @param input 
   * @return Trimmed String or Empty String
   */
  private static String dropNonCharPre(String input, ValidType type) {
    int firstChar = 0;
    switch (type) {
      case ALPHA, ALPHASPACE -> firstChar = indexOfFirst(input, Character::isLetter);
      case NUM -> firstChar = indexOfFirst(input, Character::isDigit);
      case DOUBLE -> {}
      case FULLDOUBLE -> {}
      case ALPHAXORNUM, ALPHAORNUM -> firstChar = indexOfFirst(input,
              ch -> (!Character.isDigit(ch) && !Character.isLetter(ch)));
    }
    return (firstChar == -1) ? "" : input.substring(firstChar);
  }

  /**
   * Removes all non-desired chars of a given string
   * going R->L (suffix characters) until first desired char is hit
   * 
   * @param input
   * @return Trimmed String or Empty String
   */
  private static String dropNonCharSuf(String input, ValidType type) {
    int lastChar = 0;
    switch (type) {
      case ALPHA, ALPHASPACE -> lastChar = indexOfLast(input, Character::isLetter);
      case NUM -> lastChar = indexOfLast(input, Character::isDigit);
      case DOUBLE -> {}
      case FULLDOUBLE -> {}
      case ALPHAXORNUM, ALPHAORNUM -> lastChar = indexOfLast(input,
              ch -> (!Character.isDigit(ch) && !Character.isLetter(ch)));
    }
    return (lastChar == -1) ? "" : input.substring(0, lastChar + 1);
  }
  
  private static boolean simpleDouble(int ch) {
    return Character.isDigit(ch) || ch == '.';
  }

  /**
   * Checks if the provided string contains any use of non-specified characters
   * 
   * @param input
   * @return Original String or Empty String
   */
  private static String onlySpecifiedChars(String input, ValidType type) {
    int firstNonSpecedChar = 0;
    switch (type) {
      case ALPHA -> firstNonSpecedChar = indexOfFirst(input,
              ch -> !Character.isLetter(ch));
      case ALPHASPACE -> firstNonSpecedChar = indexOfFirst(input,
              ch -> (!Character.isLetter(ch) && !Character.isSpaceChar(ch)));
      case NUM -> firstNonSpecedChar = indexOfFirst(input,
              ch -> (!Character.isDigit(ch)));
      case DOUBLE -> firstNonSpecedChar = indexOfFirst(input,
              ch -> !simpleDouble(ch));
      case FULLDOUBLE -> firstNonSpecedChar = indexOfFirst(input,
              ch -> !(simpleDouble(ch) ||
                      ch == '+' ||
                      ch == '-' ||
                      ch == 'e' ||
                      ch == 'E'));
      case ALPHAORNUM, ALPHAXORNUM -> firstNonSpecedChar = indexOfFirst(input,
              ch -> (!Character.isDigit(ch) && !Character.isLetter(ch)));
    }
    return (firstNonSpecedChar == -1) ? input : "";
  }

  /**
   * Returns the input string after searching for
   * any occurrence of a non-specified character in said string
   * 
   * @param input String value being validated
   * @param type Permitted character type(s) of the string
   * @return input
   */
  private static String search(String input, ValidType type) {
    return onlySpecifiedChars(input, type);
  }

  /**
   * Returns the input string after removing
   * any occurrence of non-specified character(s) at the ends of said string
   * 
   * @param input String value being validated
   * @param type Permitted character type(s) of the string
   * @return input
   */
  private static String drop(String input, ValidType type) {
    input = dropNonCharPre(input, type);
    input = dropNonCharSuf(input, type);
    return input;
  }

  /**
   * Outward facing method that evaluates a given string based off the type of operation to conduct
   * and the permitted data types allowed in the string
   * 
   * @param input String to be validated
   * @param option Enum values to specify type of validation
   * @param type Enum values specifying accepted char(s) in {@code String input}
   * @return Validated String or Empty String
   */
  public static String searchOrDrop(String input, Options option, ValidType type) {
    switch (option) {
      case SEARCH -> input = search(input, type);
      case DROP -> input = drop(input, type);
    }
    return input;
  }
}
