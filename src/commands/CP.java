package commands;

import java.util.ArrayList;
import java.util.Iterator;
import fileSystem.Container;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;

public class CP extends Command {
  private String NEWPATH = null, OLDPATH = null;

  public CP(String command) {
    super(command);
  }

  /**
   * This method checks if the input is a valid line input arguments.
   * 
   * @param line A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String s) {
    if (setArgs(s).length != 2) {
      out.toStdErr("Error in cp: wrong number of arguments\n");
      return false;
    }
    OLDPATH = setArgs(s)[0];
    NEWPATH = setArgs(s)[1];
    return true;
  }

  /**
   * This method returns a boolean for whether the given arg is a valid path
   * 
   * @param arg The input argument parsed
   * @return boolean based on whether the above description is satisfied
   */
  @Override
  protected boolean validateArg(String s) {
    if (!s.matches("[^ !@#$%^&*()~|<>?]*") || s.contentEquals("")) {
      out.toStdErr("Error in cp: " + s + " is an invalid path\n");
      return false;
    }
    return true;
  }

  @Override
  protected String[] setArgs(String s) {
    return getWhitespaceArgs(s);
  }


  /**
   * Recursively copy contents of directory old into directory new
   * 
   * @param old Directory to copy from
   * @param New Directory to copy to
   */
  protected void Copy(Directory old, Directory New) {
    // Directory cp = new Directory(old.getName());
    // New.addToDir(cp);
    if (old.getContents() == null) {
      return;
    } else {
      ArrayList<Container> contents = ((Directory) old).getContents();
      Iterator<Container> itr = contents.iterator();
      while (itr.hasNext()) {
        Container item = itr.next();
        if (item instanceof File) {
          File cpf = new File(old.getName(), old.getStringContents());
          New.addToDir(cpf);
        } else {
          Directory cpd = new Directory(item.getName());
          New.addToDir(cpd);
          Copy((Directory) item, cpd);
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
  public void execute(String arg) {
    if (arg.equals(OLDPATH)) {
      return;
    }
    if (OLDPATH != null && NEWPATH != null) {
      if (NEWPATH.contains(OLDPATH)) {
        out.toStdErr("Error in cp: old path is child of new path\n");
      } else {
        Container oldCon = FileSystem.getFileSystem().getItem(OLDPATH);
        Container newCon = FileSystem.getFileSystem().getItem(NEWPATH);
        if (oldCon instanceof Directory && newCon instanceof Directory) {
          // directory to directory
          Directory cp = new Directory(oldCon.getName());
          if (((Directory) newCon).addToDir(cp)) {
            Copy((Directory) oldCon, (Directory) cp);
          } else {
            out.toStdErr("Error: directory " + oldCon.getName() + "already exists in target\n");
          }
        } else if (oldCon instanceof File && newCon instanceof Directory) {
          // file to directory
          File cpf = new File(oldCon.getName(), oldCon.getStringContents());
          ((Directory) newCon).addToDir(cpf);
        } else if (oldCon instanceof File && newCon instanceof File) {
          // file into file
          ((File)newCon).write(oldCon.getStringContents());
          //out.toStdErr("Error in cp: cannot copy file into file\n");
        } else {
          // directory into file
          out.toStdErr("Error in cp: cannot copy directory to file\n");
        }
      }
    }
  }

}
