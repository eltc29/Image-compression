package test;

import java.lang.reflect.Field;
import commands.Echo;
import commands.MkDir;
import commands.Find;
import fileSystem.FileSystem;
import jShell.Output;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FindTest{

	private Find cmd;
	private FileSystem fs;
	
	@Before
  public void setUp() throws Exception {
		cmd = new Find("");
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
	public void testExecuteOnFile() throws Exception {
		Echo echo = new Echo("");
		echo.runCommand("\"test\" > outfile");
	  
		cmd.runCommand("/ -type f -name \"outfile\"");
		
		String actual = Output.getOutput().getStdOut();
		String expected = "File a found in /\n";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testExecuteOnDir() throws Exception {
		Echo echo = new Echo("");
		echo.runCommand("\"test\" > outfile");
	  
		cmd.runCommand("/ -type f -name \"outfile\"");
		
		String actual = Output.getOutput().getStdOut();
		String expected = "Directory a found in /\n";
		assertEquals(expected, actual);
	}
	
}
