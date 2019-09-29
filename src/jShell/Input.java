package jShell;

import java.util.*;

/**
 * This class keeps track of all inputs from console to a list
 * 
 * @author Emile Li
 *
 */
public class Input implements Saveable{

  private static final long serialVersionUID = 1L;
  private ArrayList<String> history = new ArrayList<String>();
  private static Scanner sc = new Scanner(System.in);
  private static Input in = new Input();
  
  public Input() {
    
  }
  
  public static Input getIn() {
    return in;
  }

  /**
   * This method receives a line from user input
   * 
   * @return String of the last line from user input
   */
  public String getLine() {
    String s = sc.nextLine();
    addHistory(s);
    return s;
  }

  /**
   * This method returns the list of previous commands
   * 
   * @return An immutable List of previous lines in order by oldest to most recent
   */
  public List<String> getHistory() {
    return Collections.unmodifiableList(history);
  }

  /**
   * This method adds user input to store into user input history by oldest to most recent
   * 
   * @param line String of the latest input
   */
  public void addHistory(String line) {
    history.add(line);
  }

  @Override
  public void setLoad(Object object) {
    Input.in = (Input) object;
  }
}
