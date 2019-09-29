package commands;

import java.util.List;
import jShell.Input;

/**
 * This class represents the 'history' command
 *
 */
public class History extends Command {

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public History(String line) {
    super(line);
  }

  /**
   * This method checks if the input is a valid line input, i.e the command should not accept any
   * input argument other than type numeric.
   * 
   * @param line A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String line) {
    if (getWhitespaceArgs(line).length > 1) {
      out.toStdErr("Error in history: too many arguments\n");
      return false;
    }
    return true;
  }

  /**
   * This method checks if the arguments parsed are valid, i.e it doesn't contain any characters
   * other than numeric.
   * 
   * @param s The input argument parsed
   * @return boolean based on whether the above description is satisfied
   */
  @Override
  protected boolean validateArg(String arg) {
    // history only takes in numeric args
    if (arg.matches("[0-9]*")) {
      return true;
    }
    out.toStdErr("Error in history: Argument is not accepted\n");
    return false;
  }

  /**
   * This method executes the command.
   * 
   * @param arg The argument for which the command is executing
   */
  @Override
  public void execute(String arg) {
    // gets history from Input
    List<String> hist = Input.getIn().getHistory();
    int a = 0;
    if (!arg.equals("")) {
      a = hist.size() - Integer.parseInt(arg);
    }
    if (a < 0) {
      a = 0;
    }
    for (int i = a; i < hist.size(); i++) {
      out.toStdOut((i + 1) + ". " + hist.get(i) + "\n");
    }
  }

  /**
   * This method split all arguments by whitespaces and put them in an array.
   * 
   * @param args A String of all input arguments
   * @return String array of the split arguments
   */
  @Override
  protected String[] setArgs(String args) {
    return getWhitespaceArgs(args);
  }

}
