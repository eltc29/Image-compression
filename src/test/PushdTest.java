package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.Test;
import commands.ChangeDir;
import commands.LS;
import commands.MkDir;
import commands.Pushd;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;
import jShell.Output;
import org.junit.After;
import org.junit.Before;


public class PushdTest {

  private Pushd cmd;
  private FileSystem fs;

  @Before
  public void setUp() {
    cmd = new Pushd("");
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
  public void testPushD() {
    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a");
    mkdir.runCommand("a/b");
    mkdir.runCommand("a/b/c");
    ChangeDir cd = new ChangeDir("");
    cd.runCommand("a/b");
    cmd.runCommand("a/b/c");
    
    String expectedOutput = "/a/b";
    assertEquals(expectedOutput, fs.path());
  }
  
}
