package commands;

import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;
import java.util.*;

/**
 * This class represents the 'find' command
 */
public class Find extends Command {
  private String option;
  private String toFind;

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public Find(String line) {
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
      out.toStdErr("Error in find: Empty arguments\n");
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
      if (!arg.substring(arg.lastIndexOf("/") + 1, arg.length()).matches("[. !@#$%^&*()~|<>?]*")) {
        out.toStdErr("Error in find: " + arg + " is an invalid path\n");
        return false;
      }
    } else if (!arg.matches("[/. !@#$%^&*()~|<>?]*")) {
      out.toStdErr("Error in find: " + arg + " is an invalid path\n");
      return false;
    }
    return true;
  }

  /**
   * This method executes the command.
   * 
   * @param arg The argument for which the command is executing
   * @return
   */
  @Override
  public void execute(String arg) {
    Directory toSearch = (Directory) (FileSystem.getFileSystem().getItem(arg));
    if (this.option.equals("f")) {
      if (toSearch.getObj(toFind) != null && toSearch.getObj(toFind) instanceof File)
        out.toStdOut("File " + toSearch.getObj(toFind).getName() + " found in " + arg + "\n");
      else
        out.toStdErr("Error in find: File " + toFind + " not found in " + arg + "\n");
    } else if (this.option.equals("d")) {
      if (toSearch.getObj(toFind) != null && toSearch.getObj(toFind) instanceof Directory)
        out.toStdOut("Directory " + toSearch.getObj(toFind).getName() + " found in " + arg + "\n");
      else
        out.toStdErr("Error in find: Directory " + toFind + " not found in " + arg + "\n");
    }
  }


  /**
   * This method takes only the paths(directories) to be searched and put them in an array of
   * Strings.
   * 
   * @param args String of all input arguments
   * @return ret String array of the split arguments
   */
  @Override
  protected String[] setArgs(String args) {
    // TODO Auto-generated method stub
    String[] arr = args.trim().split(" ");
    ArrayList<String> lst = new ArrayList<String>(); // turned into array list for easier
                                                     // manipulation on indexes.
    int j;
    for (int i = 0; i < arr.length; i += j) {
      if (arr[i].equals("-type")) {
        this.option = arr[i + 1];
        j = 2;
      } else if (arr[i].equals("-name")) {
        this.toFind = arr[i + 1].substring(1, arr[i + 1].length() - 1);
        j = 2;
      } else {
        lst.add(arr[i]);
        j = 1;
      }
    }
    String x = "";
    for (int i = 0; i < lst.size(); i++) {
      x += lst.get(i) + " ";
    }
    String[] ret = x.split(" ");

    return ret;
  }

}
