package fileSystem;

import java.util.EmptyStackException;
import java.util.Stack;
import jShell.Saveable;

/**
 * This is singleton class for the FileSystem. It has the root directory and a current directory and
 * will allow other classes to navigate the file system.
 * 
 * @author Victor Ko
 *
 */
public class FileSystem implements Saveable {

  private static final long serialVersionUID = 1L;
  private static FileSystem fs;
  private Stack<Directory> stack = new Stack<Directory>();
  private Directory root;
  private Directory cwd;

  /**
   * Constructor of new filesystem
   */
  private FileSystem() {
    root = new Directory("");
    cwd = root;
  }

  public static FileSystem getFileSystem() {
    if (fs == null) {
      fs = new FileSystem();
    }
    return fs;
  }

  /**
   * This method returns the object in the file system that is specified by the absolute or relative
   * path. null is returned when path is not found
   * 
   * @param path String of the relative or absolute path in fileseystem
   * @return Container which is File or Directory object specified by path or null otherwise
   */
  public Container getItem(String path) {
    String[] elements = path.split("/");
    Container counter;
    int s = 0;
    if (path.equals(""))
      return cwd;
    // Absolute Path
    if (path.startsWith("/")) {
      counter = root;
      s = 1;
    } else {
      // relative Path
      counter = cwd;
    }
    // Loops through names of directories or files in the path to get the object at the end
    for (int i = s; i < elements.length; i++) {
      // checks if next path can be found
      if (!(counter instanceof Directory)) {
        counter = null;
        break;
      }
      // Checks for the changing to parent directory or pathname specified
      if (elements[i].equals("..")) {
        counter = ((Directory) counter).getParent();
      } else if (elements[i].equals(".")) {

      } else {
        counter = ((Directory) counter).getObj(elements[i]);
      }
    }
    return counter;

  }

  /**
   * Changes working directory to the Directory object
   * 
   * @param toCWD Directory of the new current working directory
   */
  public void setCWD(Directory toCWD) {
    if (toCWD != null) {
      cwd = toCWD;
    }
  }

  /**
   * getter for the current working Directory
   * 
   * @return Directory of current working Directory
   */
  public Directory getCWD() {
    return cwd;
  }

  /**
   * Returns a String of the current working directory path
   * 
   * @return String of cwd
   */
  public String path() {
    String s = "";
    Directory i = cwd;
    // check if in root directory
    if (i.equals(root)) {
      s = "/";
    }
    // appends current directory name to path String
    while (i.getParent() != null) {
      s = "/" + i.getName() + s;
      i = i.getParent();

    }
    return s;
  }

  /**
   * Returns the most recent Directory that was pushed onto the stack, or null otherwise. That
   * directory is then removed from the stack
   * 
   * @return Directory of the most recent one on the stack
   */
  public Directory popd() {
    try {
      return stack.pop();
    } catch (EmptyStackException e) {
      return null;
    }
  }

  /**
   * Adds Directory dir to the Directory stack
   * 
   * @param dir Directory to be added to the stack
   */
  public void pushd(Directory dir) {
    stack.push(dir);
  }


  @Override
  public void setLoad(Object toLoad) {
    FileSystem.fs = (FileSystem) toLoad;
  }

  /**
   * override equals to compare filesystem
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof FileSystem) {
      boolean field1 = cwd.equals(((FileSystem) o).getCWD());
      boolean field2 = root.equals(((FileSystem) o).getRoot());
      boolean field3 = stack.equals(((FileSystem) o).stack);
      if (field1 && field2 && field3) {
        return true;
      }
    }
    return false;
  }
  
  private Directory getRoot() {
    return this.root;
  }

}
