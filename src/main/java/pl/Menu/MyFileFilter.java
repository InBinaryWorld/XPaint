package pl.Menu;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class MyFileFilter extends FileFilter {
  @Override
  public boolean accept(File f) {
    if (f.isDirectory())
      return true;
    return (f.getName().toLowerCase().endsWith(".txt"));
  }

  @Override
  public String getDescription() {
    return "txt";
  }

}
