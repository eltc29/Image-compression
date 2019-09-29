package commands;

import jShell.Output;

public class Save extends Command {

  public Save(String line) {
    super(line);

  }



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

  @Override
  public void execute(String arg) {
    SaveState.getSs().save(arg);
  }

  @Override
  protected String[] setArgs(String s) {
    return getWhitespaceArgs(s);
  }

}
