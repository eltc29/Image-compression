package commands;

import fileSystem.*;

/**
 * This class represents the 'cat' command
 */
public class Cat extends Command {
  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public Cat(String line) {
    super(line);
  }

  /**
   * This method checks if the input is a valid line input, i.e it's not empty.
   * 
   * @param line A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String line) {
    if (line.equals("")) {
      out.toStdErr("Error in cat: Empty arguments\n");
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
    if (arg.contains("/") && arg.length() > 1) {
      if (arg.substring(arg.lastIndexOf("/") + 1, arg.length()).matches("[/. !@#$%^&*()~|<>?]*")) {
        out.toStdErr("Error in cat: " + arg + " is an invalid path\n");
        return false;
      }
    } else if (arg.matches("[/. !@#$%^&*()~|<>?]*")) {
      out.toStdErr("Error in cat: " + arg + " is an invalid path\n");
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
    Container dir = null, obj = null;
    String contents = "";
    if (arg.contains("/")) {
      String fileName = arg.substring(arg.lastIndexOf("/") + 1, arg.length());
      String prefix = arg.substring(0, arg.lastIndexOf("/"));
      if (prefix.equals(""))
        prefix += "/"; // checks for null pointer
      if ((dir = FileSystem.getFileSystem().getItem(prefix)) != null) {
        if ((obj = ((Directory) dir).getObj(fileName)) != null
            && ((obj instanceof File) && (contents = ((File) obj).getStringContents()) != null))
          out.toStdOut(contents + "\n\n");
        else
          out.toStdErr("Error in cat: file doesn't exist or argument is not a file.\n");
      }
    } else {
      dir = FileSystem.getFileSystem().getCWD();
      if ((obj = ((Directory) dir).getObj(arg)) != null
          && ((obj instanceof File) && (contents = ((File) obj).getStringContents()) != null))
        out.toStdOut(((File) ((Directory) dir).getObj(arg)).getStringContents() + "\n\n");
      else
        out.toStdErr("Error in cat: file doesn't exist or argument is not a file.\n");
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
