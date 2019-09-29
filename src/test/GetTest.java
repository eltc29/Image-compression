package test;

import java.lang.reflect.Field;
import commands.Get;
import fileSystem.FileSystem;
import jShell.Output;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetTest{

	private Get cmd;
	private FileSystem fs;
	
	@Before
  public void setUp() throws Exception {
		cmd = new Get("");
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
		Get get = new Get("");
		get.runCommand("\"abc\" > outfile");
	  
		cmd.runCommand("outfile");
		
		String actual = (fs.getCWD()).getObj("outfile").getStringContents();
		assertEquals("abc\n\n\n\n\n", actual);
	}
	
}
