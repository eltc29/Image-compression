package fileSystem;

/**
 * This is the base class for a file. File is a Container that stores strings. The string can be
 * appended or overwritten
 * 
 * @author Leo Wang
 *
 */
public class File extends Container {

  private static final long serialVersionUID = 1L;
  private String contents;

  /**
   * Constructor for File
   * 
   * @param name String of filename
   * @param contents String of the file's text
   */
  public File(String name, String contents) {
    super(name);
    this.contents = contents;
  }

  /**
   * Appends a String s to the contents
   * 
   * @param s desired String to be appended
   */
  public void append(String s) {
    if (this.contents.equals("")) {
      this.contents = s;
    } else {
      this.contents = this.contents + "\n" + s;
    }

  }

  /**
   * Replaces current content with String s
   * 
   * @param s desired String to be content of file
   */
  public void write(String s) {
    this.contents = s;
  }

  /**
   * The method returns the String of the contents appended with a newline character
   * 
   * @return String of file contents with a newline
   */
  @Override
  public String getStringContents() {
    return contents + "\n";
  }

  /**
   * Comparing if contents in string are similar
   */
  public boolean equals(Object o) {
    if (o instanceof File) {
      if (this.getStringContents().equals(((File) o).getStringContents())
          && this.getName().equals(((File) o).getName())) {
        return true;
      }
    }
    return false;
  }
}
