package test;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.Test;
import commands.Echo;
import commands.History;

import fileSystem.FileSystem;
import jShell.Input;
import jShell.Output;
import org.junit.After;
import org.junit.Before;

public class HistoryTest {
  private History cmd;
  private FileSystem fs;

  @Before
  public void setUp() {
    cmd = new History("");
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
  public void testHistory() {
    cmd.runCommand("");
    String expectedOutput = "";
    assertEquals(expectedOutput, Output.getOutput().getStdOut());
  }
}
