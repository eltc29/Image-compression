package test;

import java.lang.reflect.Field;
import commands.MkDir;
import fileSystem.FileSystem;
import jShell.Output;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MkDirTest{

	private MkDir cmd;
	private FileSystem fs;
	
	@Before
  public void setUp() throws Exception {
		cmd = new MkDir("");
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
	  
		cmd.runCommand("a b c");
		
		String[] lst = {"a", "b", "c"};
		boolean val = true;
		for (int i=0; i<3 ; i++) {
			if (fs.getItem(lst[i]) == null) {
				val = false;
			}
		}
		
		assertSame(true, val);
	}
	
}
