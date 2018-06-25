package akego.omsimaplauncher.de;

import javafx.scene.image.Image;

/*	
 * ------------------------------------------------
 *  Base-Code written by AkEgo (akego.dev@gmail.com)
 *  ------------------------------------------------
 * 
 *  This is the config class. This is the easiest and fastest place to change any small information like
 *  the Header Image, the launcher icon and all paths to your content.
 *  !! Important: All Files need to be placed in the "mapcontent" folder! All other images, that will be used in
 *  				combination with this launcher have to be in the "img" folder of this project 
 *  
 *  Please work concentrated and don't delete anything you're not especially allowed to!
 */

public class config {
	
	/*
	 *  Name of Map Creator (or Launcher modifier)  ||  Change only the part within the " " !!!
	 */
	public String mapcreator = "YourName";
	
	/*
	 *  Launcher Name  ||  Change only the part within the " " !!!
	 */
	public final String launcher_name = "MyOmsiMapLauncher";
	
	/*
	 *  Path to your Launcher Icon (default is: img/launchericon.png) || Change only the part within the " " !!!
	 */
	public Image image_logo = new Image(launchermain.class.getResourceAsStream("/img/launchericon.png"));
	
	/*
	 *  Path to your Header Image (default is: img/bg_header.jpg) || Change only the part within the " " !!!
	 *  Recommended Image Size:  720 x 257  (Width x Height)
	 */
	public Image image_header = new Image(launchermain.class.getResourceAsStream("/img/bg_header.jpg"));
	
	/*
	 * 	Enter the name of your map folder (right it correcty, aswell with spaces if available)  ||  Change only the part within the " " !!!
	 */
	public String mapfoldername = "your_map_directory_name (for example: Grundorf)";
	
	/*
	 * 	Add all Paths to your Objects and in the same order their names (see below)											  ||  Change only the part within the " " !!!
	 * 	Furthermore add all content from project_folder/mapcontent that will be installed by clicking on install button 	  ||  Change only the part within the " " !!!
	 * 	 ... more detailed information can be found in the wiki on github ...
	 */
	public String[] addons_path= {"\\Sceneryobjects\\ABC\\DEF","\\Sceneryobjects\\BZE\\AKZ","\\Splines\\SPL","\\Splines\\TSU","\\Vehicles\\VEHICABC"};
	public String[] addons_name= {"ABC - DEF","BZE - AKZ","SPL","TSU","VEHICABC"};
	
	public String[] mapcontent_installmap = {"\\Sceneryobjects","\\Splines","\\maps","\\Addons","\\Vehicles\\DEF","\\Vehicles\\ABC","\\Vehicles\\Announcements\\XYZ"};
	public String[] ailist = {"\\Addons\\_special\\ailist\\MAN EN92 - Standard Autos","\\_special\\ailist\\Other Bus - Default Cars"};
	public String[] parkedveh = {"\\Addons\\_special\\parklist\\Standard","\\_special\\parklist\\Kaiserstadt Aachen"};
	public String[] repaints = {"\\Addons\\_special\\repaints\\BUS 1","\\_special\\repaints\\BUS 2"};
	
	/*
	 *  Icons Footer (Social Media, Direct Download and Omsi Forum)  ||  Change only the part within the " " !!!
	 */
	public Image image_ico_socialmedia = new Image(launchermain.class.getResourceAsStream("/img/socialmedia.png"));
	public Image image_ico_directdownload = new Image(launchermain.class.getResourceAsStream("/img/download.png"));
	public Image image_ico_omsiforum = new Image(launchermain.class.getResourceAsStream("/img/omsiforum.png"));
	
	/*
	 *  Enter your links (Social Media, Direct Download and Post in the Omsi Forum)  ||  Change only the part within the " " !!!
	 */
	public String link_socialmedia = "http://socialmedia_xyz_twitter_or_facebook_or_xyz/";
	public String link_directdownload = "http://directdownload/mymap/";
	public String link_omsiforum = "http://omnibussimulator/forum/mymap/";
	public String link_launchermodifierprofile = "http://link_to_your_profile/";
}
