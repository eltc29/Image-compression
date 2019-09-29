package commands;

import fileSystem.*;
import jShell.Output;

public class FileRedirection extends Command {
  private boolean append;
  private String fileName;

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public FileRedirection(String line) {
    super(line);
    if (line == ">>") {
      append = true;
    } else {
      append = false;
    }
  }

  /**
   * Checks for empty string or more than 1 output path
   * 
   * @param line The input line from the shell
   */
  @Override
  protected boolean validateLine(String line) {
    if (line.trim().equals("") || getWhitespaceArgs(line).length != 1) {
      out.toStdErr("Error in File Redirection: 1 output file must be specified\n");
      return false;
    }
    return true;
  }

  /**
   * Takes in pathname as input and checks if it invalid or not.
   * 
   * @param arg Pathname argument of command
   */
  @Override
  protected boolean validateArg(String arg) {
    FileSystem fs = FileSystem.getFileSystem();
    Container tmp = fs.getItem(arg);
    Container file = fs.getItem(arg + "/" + fileName);
    if (tmp == null || tmp instanceof File) {
      out.toStdErr("Error in File Redirection: path to file does not exist\n");
      return false;
    } else if (file != null && file instanceof Directory) {
      out.toStdErr("Error in File Redirection: path specified is a directory\n");
      return false;
    }
    return true;
  }

  /**
   * Receives pathname as input and execute the command.
   * 
   * @param arg Pathname argument of command
   */
  @Override
  public void execute(String arg) {
    // get stdout
    FileSystem fs = FileSystem.getFileSystem();
    Directory toFile = (Directory) fs.getItem(arg);
    String contents = out.getStdOut();
    if (contents.equals("")) {
      return;
    }
    out.clearSTDOut();
    // set new file with contents
    File file = new File(fileName, contents);
    // write if possible
    if (toFile.addToDir(file)) {
      if (append) {
        ((File) toFile.getObj(fileName)).append(contents);
      } else {
        ((File) toFile.getObj(fileName)).write(contents);
      }
    } else {
      Output.getOutput().toStdErr("Error in FileRedirection: cannot write to file\n");
    }
  }

  /**
   * This method takes the argument surrounded by double quotes and return the content between the
   * double quotes in String array format.
   * 
   * @param args A String of input argument
   * @return ret A String array of the argument
   */
  @Override
  protected String[] setArgs(String args) {
    int i = args.trim().lastIndexOf("/");
    String[] ret = new String[1];
    if (i == -1) {
      fileName = args;
      ret[0] = "";
    } else {
      fileName = args.substring(i + 1);
      ret[0] = args.substring(0, i);
    }
    return ret;
  }

}
