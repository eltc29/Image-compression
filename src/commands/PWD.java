package commands;

import fileSystem.FileSystem;

/**
 * This class represents the 'pwd' command
 */
public class PWD extends Command {

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line String of the line after the command
   */
  public PWD(String line) {
    super(line);
  }

  /**
   * This method checks if the input is a valid line input, i.e the command shouldn't take any
   * arguments.
   * 
   * @param line A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String line) {
    if (line.equals(""))
      return true;
    out.toStdErr("Error: pwd does not take in arguments\n");
    return false;
  }

  /**
   * This method returns true since no arguments to check.
   * 
   * @param arg The input argument parsed
   * @return boolean based on whether the above description is satisfied
   */
  @Override
  protected boolean validateArg(String arg) {
    return true;
  }

  /**
   * This method executes the command.
   * 
   * @param arg The argument for which the command is executing
   */
  @Override
  public void execute(String arg) {
    out.toStdOut(FileSystem.getFileSystem().path() + "\n");
  }

  /**
   * This method just returns an array of empty String since there were no input arguments.
   * 
   * @param args A String of all input arguments
   * @return ret A String array of empty string argument
   */
  @Override
  protected String[] setArgs(String args) {
    String[] ret = {""};
    return ret;
  }
}
