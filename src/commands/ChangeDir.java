package commands;

import fileSystem.*;
import java.lang.String;

/**
 * This class represents the 'cd' command
 */
public class ChangeDir extends Command {

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public ChangeDir(String line) {
    super(line);
  }

  /**
   * This method checks if the input is a valid line input, i.e it's not empty.
   * 
   * @param arg A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String arg) {
    if (arg.equals("")) {
      out.toStdErr("Error in cd: Empty arguments\n");
      return false;
    }
    return true;
  }

  /**
   * This method checks if the arguments parsed are valid, i.e it doesn't contain any invalid
   * characters.
   * 
   * @param arg The input argument parsed
   * @return boolean based on whether the above description is satisfied
   */
  @Override
  protected boolean validateArg(String arg) {
    // check if path is valid
    if (!arg.matches("[^ !@#$%^&*()~|<>?]*") || arg.contentEquals("")) {
      out.toStdErr("Error in cd: " + arg + " is an invalid path\n");
      return false;
    }
    return true;
  }

  /**
   * This method executes the command.
   * 
   * @param arg The argument for which the command is executing
   */
  @Override
  public void execute(String arg) {
    Container c = FileSystem.getFileSystem().getItem(arg);
    // check if dir exists in filesystem
    if (c != null && c instanceof Directory) {
      FileSystem.getFileSystem().setCWD((Directory) c);
    } else {
      out.toStdErr("Error in cd: " + arg + " is not a valid directory path\n");
    }
  }

  /**
   * This method takes the argument parsed and return it in String array form.
   * 
   * @param args A String of input argument
   * @return ret A String array of the argument
   */
  @Override
  protected String[] setArgs(String args) {
    String[] ret = {args.trim()};
    return ret;
  }

}
