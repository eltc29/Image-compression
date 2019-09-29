package commands;

import jShell.Output;

/*
 * Class of load command
 */
public class Load extends Command {

  /**
   * constructor
   * 
   * @param line String of input
   */
  public Load(String line) {
    super(line);
  }

  /**
   * checks if empty args
   */
  @Override
  protected boolean validateLine(String s) {
    if (getWhitespaceArgs(s).length == 1)
      return true;
    Output.getOutput().toStdErr("Error in save: arguments must be == 1");
    return false;
  }

  @Override
  protected boolean validateArg(String s) {
    return true;
  }

  /**
   * call file loader
   */
  @Override
  public void execute(String arg) {
    SaveState.getSs().load(arg);
  }

  @Override
  protected String[] setArgs(String s) {
    return getWhitespaceArgs(s);
  }

}
