package test;

import java.lang.reflect.Field;
import commands.PWD;
import fileSystem.Directory;
import fileSystem.FileSystem;
import jShell.Output;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PWDTest{

	private PWD cmd;
	private FileSystem fs;
	
	@Before
  public void setUp() throws Exception {
		cmd = new PWD("");
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
		
		Directory a = new Directory("");
	  fs.setCWD(a);
		
	  cmd.runCommand("");
		
		String expected = "/a";
		assertEquals(expected, Output.getOutput().getStdOut());
	}
	
}
