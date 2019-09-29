package commands;


/**
 * This class represents the 'echo' command.
 */
public class Echo extends Command {


  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public Echo(String line) {
    super(line);
  }

  /**
   * This method will take the arguments of the echo command and print them into standard output or
   * write to file otherwise
   * 
   * @param arg The argument of the command
   */
  @Override
  public void execute(String arg) {
    out.toStdOut(arg + "\n");
  }

  /**
   * This method checks the line for valid arguments and checks if a file needs to be written or
   * appended
   * 
   * @param line String of the args
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String line) {
    line = line.trim();
    if (line.startsWith("\"") && line.endsWith("\"")) {
      return true;
    }
    out.toStdErr("Error in echo: Invalid string\n");
    return false;
  }


  /**
   * This method checks if the arguments parsed are valid. In this case, it's by default true since
   * echo can accept any string content.
   * 
   * @param arg The input argument parsed
   * @return boolean based on whether the above description is satisfied
   */
  @Override
  protected boolean validateArg(String arg) {
    return true;
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
    int begin = args.indexOf("\"");
    int end = args.lastIndexOf("\"");
    String[] ret = {args.substring(begin + 1, end)};
    return ret;
  }

}
