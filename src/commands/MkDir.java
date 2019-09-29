package commands;

import fileSystem.*;

/**
 * This class represents the 'mkdir' command.
 */
public class MkDir extends Command {

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public MkDir(String command) {
    super(command);
  }

  /**
   * This method checks if the input is a valid line input, i.e it's not an empty line.
   * 
   * @param line A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String line) {
    if (line.trim().equals("")) {
      out.toStdErr("Error in mkdir: Empty arguments\n");
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
    return true;
  }

  /**
   * This method executes the command.
   * 
   * @param arg The argument for which the command is executing
   */
  @Override
  public void execute(String arg) {
    String path = getPath(arg);
    String name = getName(arg);
    if (arg.contains("/") && path.equals(""))
      path += "/";
    Container pathDir = FileSystem.getFileSystem().getItem(path);
    // checks if directory specified is a valid directory with valid name
    if (pathDir == null || !(pathDir instanceof Directory)
        || name.matches(".*[. !@#$%^&*()~|<>\"?]{1,}.*")) {
      out.toStdErr("Error in mkdir: " + arg + " is an invalid path or name\n");
    } else {
      Directory d = new Directory(name);
      // adds to directory and check if successfully added to FileSystem
      if (!((Directory) pathDir).addToDir(d)) {
        out.toStdErr("Error in mkdir: Directory \"" + arg + "\" already exists\n");
      }
    }
  }

  /**
   * This method split all arguments by whitespaces and put them in an array.
   * 
   * @param args A String of all input arguments
   * @return a String array of the split arguments
   */
  @Override
  protected String[] setArgs(String args) {
    return getWhitespaceArgs(args);
  }

  /**
   * Returns the String path to the parent directory of the one that is to be created
   * 
   * @param s String of the entire new path
   * @return path The String of the valid path
   */
  private String getPath(String s) {
    int i = s.lastIndexOf("/");
    String path;
    if (i == -1) {
      path = "";
    } else {
      path = s.substring(0, i);
    }
    return path;
  }

  /**
   * Returns the name of the new Directory to be created
   * 
   * @param s String of full path to the directory
   * @return name The String of the name
   */
  private String getName(String s) {
    int i = s.lastIndexOf("/");
    String name;
    if (i == -1) {
      name = s;
    } else {
      name = s.substring(i + 1);
    }
    return name;
  }
}
