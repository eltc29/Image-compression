package commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import fileSystem.FileSystem;
import jShell.Input;
import jShell.Output;
import jShell.Saveable;

public class SaveState {
  private static SaveState ss;
  private ArrayList<Saveable> filesMap;

  private SaveState() {
    loadfileMap();
  }

  /**
   * initializes hashmap of files to save
   */
  public void loadfileMap() {
    filesMap = new ArrayList<Saveable>();
    filesMap.add(FileSystem.getFileSystem());
    filesMap.add(Input.getIn());

  }

  /**
   * Saves current files to separate files
   */
  public void save(String arg) {
    // for each object that needs to be saved
    try {
      // get Filestream to write to
      String path = arg;
      File file = new File(path);
      FileOutputStream fs = new FileOutputStream(file);
      ObjectOutputStream oos1 = new ObjectOutputStream(fs);
      // write object to the file
      oos1.writeObject(filesMap);
      oos1.close();
    } catch (FileNotFoundException e) {
      Output.getOutput().toStdErr("Error in save: File not found\n");
    } catch (IOException e) {
      Output.getOutput().toStdErr("Error in save: IOException " + e.getMessage() + "\n");
    }
  }


  /**
   * This method loads the state of the JShell and sets it to the current one
   */
  @SuppressWarnings("unchecked")
  public void load(String arg) {
    // for each object that was saved
    try {
      // get file input stream from file
      String path = arg;
      File file = new File(path);
      FileInputStream fs = new FileInputStream(file);
      ObjectInputStream oos1 = new ObjectInputStream(fs);
      // set the contents of the file to be the one specified by calling the load function

      ArrayList<Saveable> arr = (ArrayList<Saveable>) oos1.readObject();
      for (int i = 0; i < arr.size(); i++) {
        filesMap.get(i).setLoad(arr.get(i));
      }
      oos1.close();
    } catch (FileNotFoundException e) {
      Output.getOutput().toStdErr("Error in load: File not found\n");
    } catch (IOException e) {
      Output.getOutput().toStdErr("Error in load: IOException " + e.getMessage() + "\n");
    } catch (ClassNotFoundException e) {
      Output.getOutput().toStdErr("Error in load: class not found " + e.getMessage() + "\n");
    }

  }

  /**
   * This returns the single instance of the savestate
   * 
   * @return Savestate object
   */
  public static SaveState getSs() {
    if (ss == null) {
      ss = new SaveState();
    }
    return ss;
  }


}
