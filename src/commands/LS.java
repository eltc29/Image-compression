package commands;

import fileSystem.*;

/**
 * This class represents the 'ls' command
 */
public class LS extends Command {
  private boolean recursiveLS;
  private String path = "";

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public LS(String line) {
    super(line);
  }

  /**
   * This method checks if the input is a valid line input. In this case, it just returns true since
   * ls doesn't take any input arguments.
   * 
   * @param line A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String line) {
    arguments = setArgs(line);
    recursiveLS = false;
    if (line.contains("-R")) {
      recursiveLS = true;
    }
    return true;
  }

  /**
   * This method checks if the arguments parsed are valid. In this case, it just returns true since
   * ls doesn't take any inout arguments.
   * 
   * @param arg The argument parsed
   * @return boolean based on whether the above description is satisfied
   */
  @Override
  protected boolean validateArg(String arg) {
    if (!arg.contains("-")) {
      path = arg;
    } else {
      if (arguments.length == 1) {
        return true;
      } else {
        return false;
      }
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
    Container c = FileSystem.getFileSystem().getCWD();
    try {
      if (FileSystem.getFileSystem().getItem(path) instanceof File) {
        out.toStdOut(path + "\n");
        return;
      }
      if (path == "" && !recursiveLS) {
        out.toStdOut(c.getStringContents().replace(" ", "\n"));
      } else if (path != "" && !recursiveLS) {
        c = FileSystem.getFileSystem().getItem(path);
        out.toStdOut(c.getStringContents().replace(" ", "\n"));
      } else if (path == "" && recursiveLS) {
        c = FileSystem.getFileSystem().getCWD();
        printRecursive((Directory) c, "");
        out.toStdOut("\n");
      } else {
        c = FileSystem.getFileSystem().getItem(path);
        printRecursive((Directory) c, path);
        out.toStdOut("\n");
      }
    } catch (NullPointerException nullPointerException) {
      out.toStdErr("Error in ls: " + path + " not found.\n");
    }
  }

  private void printRecursive(Directory currentDir, String fullPath) {

    fullPath = fullPath + "/";
    for (Container c : currentDir.getContents()) {
      out.toStdOut(c.getName() + "\n");
    }
    for (Container c : currentDir.getContents()) {
      if (c instanceof Directory) {
        fullPath += c.getName();
        out.toStdOut(fullPath + ":\n");

        printRecursive((Directory) c, fullPath);
        fullPath = fullPath.substring(0, fullPath.lastIndexOf("/") + 1);
      }
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
