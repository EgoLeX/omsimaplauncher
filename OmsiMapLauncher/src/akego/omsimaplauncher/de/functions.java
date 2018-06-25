package akego.omsimaplauncher.de;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
* ------------------------------------------------
*  Base-Code written by AkEgo (akego.dev@gmail.com)
*  ------------------------------------------------
*/

public class functions {

	private Properties prop = new Properties();
	private FileOutputStream output = null;
	private FileInputStream input = null;
	private String propfile = "config.properties";
	private ResourceBundle bundle = ResourceBundle.getBundle("stringlist");
	
	public String filechooser(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		fc.showOpenDialog(null);
		fc.setDialogTitle(bundle.getString("launcher.chooseomsidirectory"));
		String placeoffile = fc.getSelectedFile().getAbsolutePath();
		return placeoffile;
	}
	
	public void saveProp(String path) {
		try {		
			output = new FileOutputStream(propfile);
			// set the properties value
			prop.setProperty("omsipath", path);
			// save properties to project root folder
			prop.store(output, null);		
			output.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	public String loadProp() {
		String retstr;
		try {
			input = new FileInputStream(propfile);
    		prop.load(input);
            retstr = prop.getProperty("omsipath");
            input.close();
            
    	} catch (IOException ex) {
    		ex.printStackTrace();
    		retstr = bundle.getString("function.nodirectoryfound");
        }
		return retstr;
	}
	
	//check if file exists
	public String dirExists(String path, String objstr) {
		File file = new File(path);
		if(file.exists() && file.isDirectory()) {
			objstr = objstr+" "+bundle.getString("function.installed");
		} else {
			objstr = objstr+" "+bundle.getString("function.notinstalled");
		}
		return objstr;
	}
	//check if omsi.exe exists
		public Boolean fileExists(String path) {
			Boolean exist;
			File file = new File(path);
			if(file.exists() && file.isFile()) {
				exist = true;
			} else {
				exist = false;
			}
			return exist;
		}
	
	//credit dialog
	public void credit(String path) {
		File f = new File(path);
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//browse website in browser
	public void openweb(String link) {
		try {
			java.net.URI u = new java.net.URI(link);
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(u);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void startOmsi(String _path) {
		try {
	        @SuppressWarnings("unused")
			Process prc = new ProcessBuilder(_path).start();
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	}
	
}
