package fileSystem;

import java.io.Serializable;

/**
 * This class represents objects in the file system
 * 
 * @author Victor Ko
 */
public abstract class Container implements Serializable {

  private static final long serialVersionUID = 1L;
  private String name;
  private Directory parent;

  /**
   * Constructor of Container
   * 
   * @param name String of the name
   */
  public Container(String name) {
    this.name = name;
    parent = null;
  }

  /**
   * returns the String name of the container
   * 
   * @return String of the Container's name
   */
  public String getName() {
    return name;
  }

  /**
   * Changes the name of the container
   * 
   * @param String of name to change to
   */
  public void setName(String s) {
    name = s;
  }


  /**
   * Changes the parent directory of container
   * 
   * @param par The desired parent directory to change to
   */
  public void setParent(Directory par) {
    parent = par;
  }

  /**
   * returns the parent directory
   * 
   * @return Directory of the parent
   */
  public Directory getParent() {
    return parent;
  }

  /**
   * A method that would return the contents of a Container's subclass as a string
   * 
   * @return String that is formatted for output
   */
  public abstract String getStringContents();

  /**
   * Comparing if contents in string are similar
   */
  public boolean equals(Object o) {
    if (o instanceof Container) {
      if (this.getStringContents().equals(((Container) o).getStringContents())
          && this.getName().equals(((Container) o).getName())) {
        return true;
      }
    }
    return false;
  }

}
