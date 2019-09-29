package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.MkDir;
import commands.Tree;
import fileSystem.FileSystem;
import jShell.Output;

public class TreeTest {

  private Tree cmd;
  private FileSystem fs;

  @Before
  public void setUp() throws Exception {
    cmd = new Tree("");
    fs = FileSystem.getFileSystem();
    Output.getOutput().clearSTDOut();
  }

  @Test
  public void testexecute() {
    String expect = "/\n  a\n    c\n  b\n";
    MkDir mkdir = new MkDir("");
    mkdir.runCommand("a b");
    mkdir.runCommand("a/c");
    cmd.runCommand("");
    assertEquals(expect, Output.getOutput().getStdOut());
  }
  
  @After
  public void destroy() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);
    field.set(null, null);
  }

}
