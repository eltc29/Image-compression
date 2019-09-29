package test;

import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.Stack;
import org.junit.*;
import commands.Popd;
import fileSystem.*;
import jShell.Output;

public class PopdTest {
  FileSystem fs;
  
  @Before
  public void setup() {
    fs = FileSystem.getFileSystem();
  }

  @Test
  public void testEmptyPopd() {
    Popd popd = new Popd("");
    popd.execute("");
    assertNotEquals("", Output.getOutput().getStdErr());
  }
  
  @Test
  public void testPopdCall() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("stack");
    field.setAccessible(true);
    Stack<Directory> tmp = new Stack<Directory>();
    Directory initial = new Directory("hi");
    tmp.add(initial);
    field.set(fs, tmp);
    Popd popd = new Popd("");
    popd.execute("");
    assertEquals(initial,fs.getCWD());
  }

  @Test
  public void testPopdOne() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("stack");
    field.setAccessible(true);
    Stack<Directory> tmp = new Stack<Directory>();
    Directory initial = new Directory("hi");
    tmp.add(initial);
    field.set(fs, tmp);
    Directory tocheck = fs.popd();
    assertEquals(initial,tocheck);
  }
  
  @Test
  public void testPopdFIFO() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("stack");
    field.setAccessible(true);
    Stack<Directory> tmp = new Stack<Directory>();
    tmp.add(new Directory("last"));
    Directory initial = new Directory("hi");
    tmp.add(initial);
    field.set(fs, tmp);
    Directory tocheck = fs.popd();
    assertEquals(initial,tocheck);
  }

  @After
  public void teardown() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);
    field.set(null, null);
  }


}
