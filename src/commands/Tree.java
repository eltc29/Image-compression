package commands;

import java.util.ArrayList;
import java.util.Iterator;
import fileSystem.Container;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;


public class Tree extends Command {

  public Tree(String command) {
    super(command);
  }

  /**
   * This method checks if the input is a valid line input, i.e the command shouldn't take any
   * arguments.
   * 
   * @param line A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String line) {
    if (line.equals(""))
      return true;
    out.toStdErr("Error: tree does not take in arguments\n");
    return false;
  }

  /**
   * This method returns true since no arguments to check.
   * 
   * @param arg The input argument parsed
   * @return boolean based on whether the above description is satisfied
   */
  @Override
  protected boolean validateArg(String arg) {
    return true;
  }

  /**
   * This method recursively goes through the filesystem from given root r
   * 
   * @param r The given root node
   * @param depth The depth of recursion/ current directory
   */
  protected void recursion(Directory r, int depth) {
    for (int i = 0; i < depth; i++) {
      out.toStdOut("  ");
    }
    out.toStdOut(r.getName() + "\n");
    ArrayList<Container> contents = ((Directory) r).getContents();
    if (contents == null) {
      return;
    } else {
      Iterator<Container> itr = contents.iterator();
      while (itr.hasNext()) {
        Container item = itr.next();
        if (item instanceof File) {
          for (int i = 0; i < depth + 1; i++) {
            out.toStdOut("  ");
          }
          out.toStdOut(item.getName() + "\n");
        } else {
          recursion((Directory) item, depth + 1);
        }
      }
    }
    return;
  }


  /**
   * This method executes the command.
   * 
   * @param arg The argument for which the command is executing
   */
  @Override
  public void execute(String arg) {
    Directory root = (Directory) FileSystem.getFileSystem().getItem("/");
    out.toStdOut("/");
    recursion(root, 0);

  }

  /**
   * This method just returns an array of empty String since there were no input arguments.
   * 
   * @param args A String of all input arguments
   * @return ret A String array of empty string argument
   */
  @Override
  protected String[] setArgs(String args) {
    String[] ret = {""};
    return ret;
  }

}
