package fileSystem;

import java.util.ArrayList;

/**
 * This class represents folders or directories in the filesystem which contain other directories or
 * files
 * 
 * @author tankelv1
 *
 */
public class Directory extends Container {

  private static final long serialVersionUID = 1L;
  // sorted Arraylist of Directory contents
  // Can have Files or Directories
  private ArrayList<Container> contents;

  /**
   * Initializes the Directory object with a given name and Parent
   * 
   * @param name String name of the directory
   */
  public Directory(String name) {
    super(name);
    contents = new ArrayList<>();
  }


  /**
   * this method returns a String of the contents of the directory separated by space and ending
   * with a newline
   * 
   * @return String of the directory contents with a newline
   */
  @Override
  public String getStringContents() {
    String s = "";
    for (int i = 0; i < contents.size(); i++) {
      s = s + " " + contents.get(i).getName();
    }
    return s.trim() + "\n";
  }

  /**
   * Adds a File or Directory to the contents of the Directory object in alphabetical ascending
   * order by String of its name and returns true. It also sets the parent Directory to the current
   * one. If the Container's name exists in contents then it will not be added and false is returned
   * 
   * @param toAdd Container object of the Directory
   * @return boolean of the success of the operation
   */
  public boolean addToDir(Container toAdd) {
    // get index of desired position
    int l = binSearch(toAdd.getName());

    // check if there are instances of the object with the same name
    if (l < contents.size() && contents.get(l).getName().equals(toAdd.getName())) {
      return false;
    }
    // insert into Contents at specified index
    contents.add(l, toAdd);
    toAdd.setParent(this);
    return true;
  }

  /**
   * Removes a File or Directory to the contents of the Directory object. Returns true if operation
   * succeeded and false otherwise.
   * 
   * @param toRemove Container object of the Directory
   * @return boolean of the success of the operation
   */
  public boolean removeFromDir(Container toRemove) {
    int l = binSearch(toRemove.getName());
    if (l != -1) {
      contents.remove(l);
      return true;
    }
    return false;
  }

  /**
   * Returns an element inside the Directory contents with the specified name. It will return null
   * if the element does not exist
   * 
   * @param name String of desired file or directory name
   * @return File or Directory with specified name or null otherwise
   */
  public Container getObj(String name) {

    // gets index of object where its name should be
    int m = binSearch(name);
    // checks if object at index is desired and returns it
    if (m < contents.size() && contents.get(m).getName().equals(name)) {
      return contents.get(m);
    }
    return null;

  }

  /**
   * This method returns the array list of the contents for the directory object.
   * 
   * @return ArrayList an array list of type Container which is the contents of the directory
   */
  public ArrayList<Container> getContents() {
    return contents;
  }

  /**
   * This method returns an int index of where the String name of the Container should be inside
   * contents
   * 
   * @param name String of desired File or Directory
   * @return int of desired index
   */
  private int binSearch(String name) {
    int left = 0;
    int right = contents.size() - 1;
    int m = 0;
    // Binary search implementation by comparison of container's String name
    while (left <= right) {
      m = (left + right) / 2;
      if (contents.get(m).getName().equals(name)) {
        return m;
      } else if (contents.get(m).getName().compareTo(name) < 0) {
        left = m + 1;
      } else {
        right = m - 1;
      }
    }
    if (left >= m) {
      return left;
    } else {
      return right;
    }
  }

  /**
   * Comparing if contents in string are similar
   */
  public boolean equals(Object o) {
    if (o instanceof Directory) {
      if (this.getStringContents().equals(((Directory) o).getStringContents())
          && this.getName().equals(((Directory) o).getName())) {
        return true;
      }
    }
    return false;
  }
}
