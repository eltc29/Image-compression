package test;

import static org.junit.Assert.*;
//import java.lang.reflect.Field;
//import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import commands.MkDir;
import commands.Man;
//import fileSystem.FileSystem;
import jShell.Output;

public class ManTest {

  private Man cmd;
//  private FileSystem fs;

  @Before
  public void setUp() throws Exception {
    cmd = new Man("");
//    fs = FileSystem.getFileSystem();
    Output.getOutput().clearSTDOut();
  }

  @Test
  public void testexecute() {
    String expect = "NAME\n man - print the manual of CMD\nUSAGE\n man CMD\nEXAMPLES"
        + "\n man cd would print out the manual for cd.\n\n\n\n";
    cmd.runCommand("man");
    assertEquals(expect, expect);
  }
  
//  @After
//  public void destroy() throws Exception {
//    Field field = (fs.getClass()).getDeclaredField("fs");
//    field.setAccessible(true);
//    field.set(null, null);
//  }

}
