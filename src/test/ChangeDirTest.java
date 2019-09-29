package test;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.Test;
import commands.ChangeDir;
import commands.LS;
import commands.MkDir;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;
import jShell.Output;
import org.junit.After;
import org.junit.Before;

public class ChangeDirTest {
  
  private ChangeDir cmd;
  private FileSystem fs;

  @Before
  public void setUp() {
    cmd = new ChangeDir("");
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
   * This test checks whether changing directories with relative paths works.
   */
  @Test
  public void testRelativePath() {
    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a");
    mkdir.runCommand("a/b");
    mkdir.runCommand("a/b/c");
    cmd.runCommand("a");
    cmd.runCommand("b");
    
    String expectedOutput = "/a/b";
    assertEquals(expectedOutput, fs.path());
  }
  /*
   * This test checks whether changing directories with absolute paths works.
   */
  @Test
  public void testAbsolutePath() {
    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a");
    mkdir.runCommand("a/b");
    mkdir.runCommand("a/b/c");
    cmd.runCommand("/a/b/c");
    
    String expectedOutput = "/a/b/c";
    assertEquals(expectedOutput, fs.path());
  }
  /*
   * This test checks whether changing directories with .. works.
   */
  @Test
  public void testDoubleDot() {
    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a");
    mkdir.runCommand("a/b");
    mkdir.runCommand("a/b/c");
    cmd.runCommand("/a/b/c");
    cmd.runCommand("..");
    
    String expectedOutput = "/a/b";
    assertEquals(expectedOutput, fs.path());
  }
  
}
