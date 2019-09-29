package commands;

import java.io.*;
import java.net.*;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;

/**
 * This class represents the 'get' command
 */
public class Get extends Command {

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public Get(String line) {
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
      out.toStdErr("Error in get: Empty arguments\n");
      return false;
    }
    return true;
  }

  /**
   * This method checks if the arguments parsed are valid. In this command's case, this method does
   * not need to.
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
    String contents;
    if ((contents = getUrlContents(arg)) != null) {
      File f = new File(arg.substring(arg.lastIndexOf("/") + 1), contents);
      Directory currDir = FileSystem.getFileSystem().getCWD();
      if (currDir.addToDir(f))
        out.toStdErr(f.getName() + " successfully created in: " + currDir.getName() + "\n");
      else
        out.toStdErr("Error in get: file already exists.");
    }
  }

  /**
   * This method gets the contents from the URL page.
   * 
   * @param arg The URL argument
   * @return String of the contents from the URL
   */
  protected String getUrlContents(String arg) {
    StringBuilder content = new StringBuilder();
    try {
      // create a url object
      URL url = new URL(arg);
      // wrap the urlconnection in a bufferedreader
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String line;
      // read from the urlconnection via the bufferedreader
      while ((line = in.readLine()) != null) {
        content.append(line + "\n");
      }
      in.close();
    } catch (Exception e) {
      out.toStdErr("Error in get: invalid URL argument\n");
      return null;
    }
    return content.toString();
  }

  /**
   * This method split all arguments by whitespaces and put them in an array.
   * 
   * @param args A String of all input arguments
   * @return String array of the split arguments
   */
  @Override
  protected String[] setArgs(String args) {
    // TODO Auto-generated method stub
    return args.split(" ");
  }
}
