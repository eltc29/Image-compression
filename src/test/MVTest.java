package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.Test;
import commands.LS;
import commands.MV;
import commands.MkDir;
import fileSystem.File;
import fileSystem.FileSystem;
import jShell.Output;
import org.junit.After;
import org.junit.Before;

public class MVTest {
  
  private MV cmd;
  private FileSystem fs;

  @Before
  public void setUp() {
    cmd = new MV("");
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
  @Test
  public void testMoveDirectory() {
    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a");
    mkdir.runCommand("a/b");
    mkdir.runCommand("a/b/c");
    cmd.runCommand("/a/b/c /a");
    LS ls = new LS("");
    ls.runCommand("-R");
    
    String expectedOutput = "a\n/a:\nb\nc\n/a/b:\n/a/c:\n\n";
    assertEquals(expectedOutput, Output.getOutput().getStdOut());
  }
  @Test
  public void testMoveFile() {
    File f = new File("out.txt", "some text");
    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a");
    mkdir.runCommand("a/b");
    fs.getCWD().addToDir(f);
    cmd.runCommand("out.txt a/b");
    LS ls = new LS("");
    ls.runCommand("-R");
    
    String expectedOutput = "a\n/a:\nb\n/a/b:\nout.txt\n\n";
    assertEquals(expectedOutput, Output.getOutput().getStdOut());
  }
}
