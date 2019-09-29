package commands;

import fileSystem.Container;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;

public class MV extends Command {
  private String oldPath = null;
  private String newPath = null;

  public MV(String command) {
    super(command);

  }

  @Override
  protected boolean validateLine(String s) {
    if (setArgs(s).length != 2) {
      out.toStdErr("Error in mv: wrong number of arguments\n");
      return false;
    }
    oldPath = setArgs(s)[0];
    newPath = setArgs(s)[1];
    return true;
  }

  @Override
  protected boolean validateArg(String s) {
    if (!s.matches("[^ !@#$%^&*()~|<>?]*") || s.contentEquals("")) {
      out.toStdErr("Error in mv: " + s + " is an invalid path\n");
      return false;
    }
    return true;

  }

  @Override
  public void execute(String arg) {
    if (arg.equals(oldPath)) {
      return;
    }
    if (oldPath != null && newPath != null) {
      if (newPath.contains(oldPath)) {
        out.toStdErr("Error in mv: old path is child of new path\n");
      } else {
        Container oldCon = FileSystem.getFileSystem().getItem(oldPath);
        Container newCon = FileSystem.getFileSystem().getItem(newPath);
        if (oldCon instanceof Directory && newCon instanceof Directory
            || oldCon instanceof File && newCon instanceof Directory) {
          Directory parent = oldCon.getParent();
          parent.removeFromDir(oldCon);
          ((Directory) newCon).addToDir(oldCon);
        } else if (oldCon instanceof File && newCon instanceof File) {
          ((File) newCon).write(oldCon.getStringContents());
          Directory parent = oldCon.getParent();
          parent.removeFromDir(oldCon);
        } else {
          out.toStdErr("Error in mv: invalid move\n");
        }
      }
    }
  }

  @Override
  protected String[] setArgs(String s) {
    return getWhitespaceArgs(s);
  }

}
