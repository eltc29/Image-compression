package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.Test;
import commands.LS;
import commands.MkDir;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;
import jShell.Output;
import org.junit.After;
import org.junit.Before;

public class LSTest {

  private LS cmd;
  private FileSystem fs;

  @Before
  public void setUp() {
    cmd = new LS("");
    fs = FileSystem.getFileSystem();
    Output.getOutput().clearSTDOut();
  }

  @After
  public void tearDown()
      throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    Field f = (fs.getClass()).getDeclaredField("fs");
    f.setAccessible(true);
    f.set(null, null);
  }
  
  /*
   * Assume MkDir is correct. Tests if ls correctly prints out directories.
   */
  @Test
  public void testDir() {

    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a b c");
    cmd.runCommand("");

    String expectedOutput = "a\nb\nc\n";
    assertEquals(expectedOutput, Output.getOutput().getStdOut());

  }
  /*
   * Tests if the -R option functions correctly.
   */
  @Test
  public void testRecursiveDir() {
    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a");
    mkdir.runCommand("a/b");
    mkdir.runCommand("a/b/c");
    cmd.runCommand("-R");

    String expectedOutput = "a\n/a:\nb\n/a/b:\nc\n/a/b/c:\n\n";
    assertEquals(expectedOutput, Output.getOutput().getStdOut());
  }
  /*
   * Tests when the file path is a file, it should output the file path.
   */
  @Test
  public void testFilePath() {
    File f = new File("out", "some text");
    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a");
    mkdir.runCommand("a/b");
    mkdir.runCommand("a/b/c");
    ((Directory)fs.getItem("a/b/c")).addToDir(f);
    cmd.runCommand("a/b/c/out");
    
    String expectedOutput = "a/b/c/out\n";
    assertEquals(expectedOutput, Output.getOutput().getStdOut());
    
  }
}
