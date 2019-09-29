package test;

import static org.junit.Assert.*;
import org.junit.Test;
import commands.Echo;
import jShell.Output;
import org.junit.Before;

public class EchoTest {
  
  private Echo cmd;

  @Before
  public void setUp() {
    cmd = new Echo("");
    Output.getOutput().clearSTDOut();
  }
  /*
   * Tests the basic functionality of echo.
   */
  @Test
  public void testExecute() {
    cmd.runCommand("\"Hello World\"");
    String expectedOutput = "Hello World\n";
    assertEquals(expectedOutput, Output.getOutput().getStdOut());
  }
  /*
   * Tests if known commands can be echoed.
   */
  @Test
  public void testKnownCommands() {
    cmd.runCommand("\"ls mv echo mkdir\"");
    String expectedOutput = "ls mv echo mkdir\n";
    assertEquals(expectedOutput, Output.getOutput().getStdOut());
  }

}
