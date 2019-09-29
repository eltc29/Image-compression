package jShell;

import java.util.ArrayList;
import java.util.HashMap;
import commands.*;

/**
 * This class contains methods that operate on given lines to execute user commands
 * 
 * @author Victor Ko
 *
 */
public class InputHandler {

  private static HashMap<String, Command> commands = setCommands();

  /**
   * This method executes the line from user input and returns a whether the command exit is called
   * 
   * @param s String of the line from user input
   * @return boolean of whether program terminates or continues
   */
  public static boolean evaluateLine(String input) {
    if (input.equals("exit")) {
      Output.getOutput().toStdOut("Program terminated. Thanks for using JBuntu!\n");
      return true;
    }
    ArrayList<String> sep = getRedirections(input);
    for (int i = 0; i < sep.size(); i++) {
      evaluateCommand(sep.get(i));
    }
    return false;
  }

  private static void evaluateCommand(String input) {
    Command command = null;
    String cmd = extractCmd(input);
    String arg = suffix(input);

    command = commands.get(cmd);

    if (command != null) {
      command.runCommand(arg);
    } else {
      Output.getOutput().toStdOut("\"" + cmd + "\" is not a recognized command\n");
    }
  }



  /**
   * Sets the HashMap table of the key to call the command and its respective command object
   * 
   * @return HashMap<String, Command> of the table with String command and its Command object
   */
  private static HashMap<String, Command> setCommands() {
    HashMap<String, Command> cmds = new HashMap<String, Command>();
    cmds.put("mkdir", new MkDir(""));
    cmds.put("echo", new Echo(""));
    cmds.put("cd", new ChangeDir(""));
    cmds.put("ls", new LS(""));
    cmds.put("pwd", new PWD(""));
    cmds.put("pushd", new Pushd(""));
    cmds.put("popd", new Popd(""));
    cmds.put("history", new History(""));
    cmds.put("cat", new Cat(""));
    cmds.put("man", new Man(""));
    cmds.put("get", new Get(""));
    cmds.put("mv", new MV(""));
    cmds.put(">", new FileRedirection(">"));
    cmds.put(">>", new FileRedirection(">>"));
    cmds.put("save", new Save(""));
    cmds.put("load", new Load(""));
    cmds.put("find", new Find(""));
    cmds.put("cp", new CP(""));
    cmds.put("tree", new Tree(""));
    return cmds;
  }


  /**
   * This method takes in the input String s and returns the first space separated element in the
   * String or the empty string otherwise
   * 
   * @param s String of user input
   * @return String of where the command word should be
   */
  private static String extractCmd(String s) {
    return s.trim().split(" ")[0];
  }

  /**
   * This method takes in the input String s and returns the subset after the first space in the
   * String or the empty string otherwise
   * 
   * @param s String of user input
   * @return String of where the arguments should be
   */
  private static String suffix(String s) {
    int j = s.indexOf(" ");
    if (j == -1) {
      return "";
    }
    return s.substring(j + 1, s.length());
  }

  /**
   * This method takes in an input string and separates it by redirection
   * 
   * @param s String of input
   * @return ArrayList of strings separated by valid redirections
   */
  private static ArrayList<String> getRedirections(String s) {
    ArrayList<String> splitted = new ArrayList<String>();
    String[] specialChars = {">>", ">"};
    boolean inString = false;
    int prev = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '\"') {
        inString = !inString;
      }
      if (!inString) {
        for (int j = 0; j < specialChars.length; j++) {
          String tmp = s.substring(i, s.length());
          if (tmp.startsWith(specialChars[j])) {
            splitted.add(s.substring(prev, i));
            prev = i;
            i = s.length();
          }
        }
      }
    }
    splitted.add(s.substring(prev, s.length()));
    return splitted;
  }

}
