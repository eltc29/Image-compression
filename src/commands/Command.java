package commands;

import jShell.Output;

/**
 * This is an abstract class that is inherited by every command, and includes the methods and
 * properties for every command to function properly.
 */
public abstract class Command {
  private String line;
  protected String[] arguments;
  protected Output out = Output.getOutput();

  /**
   * Constructor for command
   * 
   * @param line String of the lines after the command
   */
  public Command(String line) {
    this.line = line;
  }

  /**
   * Setter for the String after the command and up to newline
   * 
   * @param input String of contents after the command
   */
  public void setLine(String input) {
    line = input;
  }

  /**
   * This method validates the input line and if working, then will split the arguments and will
   * execute each argument separately with while checking its argument validator
   */
  public final void runCommand(String input) {
    line = input;
    if (!validateLine(line)) {
      return;
    }
    arguments = setArgs(line);
    for (int i = 0; i < arguments.length; i++) {
      if (validateArg(arguments[i])) {
        execute(arguments[i]);
      }
    }
  }

  /**
   * This method checks whether input string is is valid and will return true if so and false
   * otherwise. The method will print error statements if invalid.
   * 
   * @param s String of input
   * @return boolean of whether string is valid
   */
  protected abstract boolean validateLine(String s);

  /**
   * This method checks whether a single argument string is is valid and will return true if so and
   * false otherwise. The method will print error statements if invalid.
   * 
   * @param s String of argument
   * @return boolean of whether string argument is valid
   */
  protected abstract boolean validateArg(String s);

  /**
   * This method executes the command under assumption that the argument is valid
   * 
   * @param arg String of valid argument
   */
  public abstract void execute(String arg);

  /**
   * This method sets the arguments of the command
   * 
   * @param s A String of input arguments
   * @return
   */
  protected abstract String[] setArgs(String s);

  /**
   * returns an array of arguments from String s that is separated by whitespace if string is empty,
   * will return array with empty string
   * 
   * @param s String of lines containing arguments separated by whitespace
   * @return String array of arguments
   */
  protected String[] getWhitespaceArgs(String s) {

    String[] split = s.trim().split("\\s+");
    return split;
  }

}
