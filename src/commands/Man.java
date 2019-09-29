package commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.HashMap;

/**
 * This class represents the 'man' command
 */
public class Man extends Command {
  private static HashMap<String, String> filesMap;
  private static final String COMMAND_MAN_FOLDER = "command_manuals/";

  /**
   * This constructor sets the necessary instance variables.
   * 
   * @param line The input line from the shell
   */
  public Man(String line) {
    super(line);
    loadManual();
  }


  /**
   * This method loads the hashmap with information corresponding to its respective command.
   */
  private static void loadManual() {
    if (filesMap == null) {
      filesMap = new HashMap<String, String>();
      filesMap.put("cat", "catMan.txt");
      filesMap.put("cd", "cdMan.txt");
      filesMap.put("echo", "echoMan.txt");
      filesMap.put("history", "historyMan.txt");
      filesMap.put("ls", "lsMan.txt");
      filesMap.put("mkdir", "mkdirMan.txt");
      filesMap.put("popd", "popdMan.txt");
      filesMap.put("pushd", "pushdMan.txt");
      filesMap.put("pwd", "pwdMan.txt");
      filesMap.put("man", "manMan.txt");
      filesMap.put("find", "findMan.txt");
      filesMap.put("get", "getMan.txt");
      filesMap.put("mv", "mvMan.txt");
      filesMap.put("cp", "cpMan.txt");
      filesMap.put("tree", "treeMan.txt");
      filesMap.put("save", "saveMan.txt");
      filesMap.put("load", "loadMan.txt");
    }
  }


  /**
   * This method checks if the input is a valid line input, i.e only one input argument is parsed
   * which is the command we want the manual for.
   * 
   * @param line A String of input arguments
   * @return boolean based on whether above description is satisfied
   */
  @Override
  protected boolean validateLine(String line) {
    String[] tmp = getWhitespaceArgs(line);
    if (tmp.length == 1 && !tmp[0].equals(""))
      return true;
    out.toStdErr("Error in man: Must have exactly 1 argument\n");
    return false;
  }

  /**
   * This method checks if the arguments parsed are valid, i.e the command actually exists.
   * 
   * @param arg The input argument parsed
   * @return boolean based on whether the above description is satisfied
   */
  @Override
  protected boolean validateArg(String arg) {
    if (filesMap.containsKey(arg))
      return true;
    out.toStdErr("Error 404 in man: Command not found\n");
    return false;
  }

  /**
   * This method executes the command.
   * 
   * @param arg The argument for which the command is executing
   */
  @Override
  public void execute(String arg) {
    try {
      InputStream is = Man.class.getResourceAsStream(COMMAND_MAN_FOLDER + filesMap.get(arg));
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      String line = br.readLine();
      while (line != null) {
        out.toStdOut(line + "\n");
        line = br.readLine();
      }
      out.toStdOut("\n\n");
      br.close();
    } catch (IOException e) {
      out.toStdErr("Error in man: resources incorrectly loaded\n");
    }
  }

  /**
   * This method split all arguments by whitespaces and put them in an array.
   * 
   * @param args A String of all input arguments
   * @return String array of the split arguments
   */
  @Override
  protected String[] setArgs(String args) {
    return getWhitespaceArgs(args);
  }

}
