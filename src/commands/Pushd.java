package commands;

import fileSystem.*;


/**
 * This class represents the 'pushd' command
 * 
 * @author Victor Ko
 */
public class Pushd extends Command {

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public Pushd(String line) {
    super(line);
  }

  /**
   * This method checks if the input is a valid line input, i.e the command should be accepting 1 or
   * 0 argument only.
   * 
   * @param line A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String line) {

    if (getWhitespaceArgs(line).length == 1)
      return true;
    out.toStdErr("Error in pushd: Command takes 1 argument\n");
    return false;
  }

  /**
   * This method checks if the argument parsed is valid.
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
    if (arg.equals("")) {
      out.toStdErr("Error in pushd: Command takes 1 argument\n");
      return;
    }
    Container pDir = FileSystem.getFileSystem().getItem(arg);
    if (pDir == null || !(pDir instanceof Directory)) {
      out.toStdErr("Error in pushd: " + arg + " is an invalid path to directory\n");
    } else {
      FileSystem.getFileSystem().pushd(FileSystem.getFileSystem().getCWD());
      FileSystem.getFileSystem().setCWD((Directory) pDir);
    }
  }

  /**
   * This method split all arguments by whitespaces and put them in an array.
   * 
   * @param args A String of all input arguments
   * @return ret A String array of the split arguments
   */
  @Override
  protected String[] setArgs(String args) {
    String[] ret = getWhitespaceArgs(args);
    return ret;
  }

}
