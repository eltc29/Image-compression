// **********************************************************
// Assignment2:
// Student1:Victor Ko
// UTORID user_name: kovicto2
// UT Student #:1005070628
// Author: Victor Ko
//
// Student2:
// UTORID user_name: litimche
// UT Student #: 1004811251
// Author: Emile Li Tim Cheong
//
// Student3:
// UTORID user_name: wangleo3
// UT Student #: 1005250238
// Author: Leo Wang
//
// Student4:
// UTORID user_name: tankelv1
// UT Student #: 1005041278
// Author: Kelvin Tan
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import jShell.Input;
import jShell.InputHandler;
import jShell.Output;
import commands.SaveState;
import fileSystem.*;

/**
 * This class prints the full path of the current working directory and waits for the user to enter
 * a command. It takes the input from the user and gives back an output depending on the commnad. It
 * continues to do so until the user exits the program.
 * 
 * @author Emile Li
 *
 */
public class JShell {

  public static void main(String[] args) {
    init();
    Output out = Output.getOutput();
    boolean quit = false;
    while (!quit) {
      System.out.print(FileSystem.getFileSystem().path() + "# ");
      quit = InputHandler.evaluateLine(Input.getIn().getLine());
      out.print();
      out.clearSTDOut();
      out.clearSTDErr();
    }
  }
  
  public static void init() {
    SaveState.getSs().loadfileMap();
  }
}
