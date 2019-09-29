package test;

import java.lang.reflect.Field;
import commands.Cat;
import commands.Echo;
import fileSystem.FileSystem;
import jShell.Output;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CatTest{

	private Cat cmd;
	private FileSystem fs;
	
	@Before
  public void setUp() throws Exception {
		cmd = new Cat("");
    fs = FileSystem.getFileSystem();
    Output.getOutput().clearSTDOut();
  }
	
	@After
	public void tearDown() throws Exception {
		Field f = (fs.getClass()).getDeclaredField("fs");
    f.setAccessible(true);
    f.set(null, null);
  }
	
	@Test
	public void testExecute() throws Exception {
		Echo echo = new Echo("");
		echo.runCommand("\"test1\"");
		echo.runCommand("\"test2\" > outfile1");
	  
		String expected1 = "test1";
		assertEquals(expected1, Output.getOutput().getStdOut());
		
		cmd.runCommand("outfile1");
		String expected2 = "test1\n\n\n";
		assertEquals(expected2, Output.getOutput().getStdOut());
	}
	
}
