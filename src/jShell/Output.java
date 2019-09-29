package jShell;

/**
 * This class handles outputting results of commands to the shell
 */
public class Output {

  private String stdOut = "";
  private String stdErr = "";
  private static Output out;

  private Output() {

  }

  public static Output getOutput() {
    if (out == null) {
      out = new Output();
    }
    return out;

  }

  /**
   * This method prints the passed string into the terminal without newlines
   */
  public void print() {
    System.out.print(stdErr);
    System.out.print(stdOut);
  }

  /**
   * This method gets stdOut
   * 
   * @return String stdOut
   */
  public String getStdOut() {
    return stdOut;
  }

  /**
   * This method gets stdErr
   * 
   * @return String stdErr
   */
  public String getStdErr() {
    return stdErr;
  }

  /**
   * This method adds String str to stdOut without any newline characters
   * 
   * @param str String to be appended
   */
  public void toStdOut(String str) {
    stdOut = stdOut + str;
  }

  /**
   * This method adds String str to stdErr without any newline characters
   * 
   * @param str String to be appended
   */
  public void toStdErr(String str) {
    stdErr = stdErr + str;
  }

  /**
   * This method clears stdOut
   */
  public void clearSTDOut() {
    stdOut = "";
  }

  /**
   * This method clears stdErr
   */
  public void clearSTDErr() {
    stdErr = "";
  }
}
