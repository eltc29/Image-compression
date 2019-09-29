package test;

import static org.junit.Assert.*;
import java.lang.reflect.*;
import org.junit.*;
import commands.SaveState;
import fileSystem.*;
import jShell.Input;

public class SaveStateTest {
  private FileSystem fs;
  private Input in;
  private SaveState ss;

  @Before
  public void setup() {
    fs = FileSystem.getFileSystem();
    in = Input.getIn();
    ss = SaveState.getSs();
    ss.loadfileMap();
  }

  @Test
  public void testSaveFileSystemWithCwd() {
    Directory newCWD = new Directory("hi");
    fs.getCWD().addToDir(newCWD);
    ss.save("a.txt");
    ss.load("a.txt");
    FileSystem newFS = FileSystem.getFileSystem();
    assertEquals(fs, newFS);
  }

  @Test
  public void testSaveFileSystemWithStack() {
    Directory newCWD = new Directory("hi");
    fs.pushd(newCWD);
    ss.save("a.txt");
    ss.load("a.txt");
    FileSystem newFS = FileSystem.getFileSystem();
    assertEquals(fs, newFS);
  }

  @Test
  public void testSaveFileSystemEmpty() {
    ss.save("a.txt");
    ss.load("a.txt");
    FileSystem newFS = FileSystem.getFileSystem();
    assertEquals(fs, newFS);
  }


  @Test
  public void testSaveHistory() {
    in = Input.getIn();
    ss = SaveState.getSs();
    in.addHistory("hi");
    ss.save("a.txt");
    ss.load("a.txt");
    Input in2 = Input.getIn();
    assertEquals(in.getHistory(), in2.getHistory());
  }

  @After
  public void destroy() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);
    field.set(null, null);
  }

}
