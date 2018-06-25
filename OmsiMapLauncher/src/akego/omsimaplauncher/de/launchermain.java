package akego.omsimaplauncher.de;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/*
* ------------------------------------------------
*  Base-Code written by AkEgo (akego.dev@gmail.com)
* ------------------------------------------------
*/

public class launchermain extends Application{

	BorderPane bp = new BorderPane();
	functions func = new functions();
	config conf = new config();
	ImageView iv_header, iv_socialmedia, iv_download, iv_omsiforum;
	Label lb_chooseomsidir, lb_install, lb_otherthings, lb_info, lb_output, lb_launcherakego, lb_modifiedby;
	TextField tf_chooseomsidir;
	Button btn_chooseomsidir, btn_install, btn_deinstall, btn_startomsi, btn_mapinstalled, btn_fremdaddons, btn_credits, btn_ailist, btn_parkedv, btn_repaints;
	Tooltip tt_install, tt_deinstall;
	TextArea ta_note;
	ChoiceBox<String> choice_ailist, choice_park, choice_repaints;
	VBox vmain, vfooter;
	HBox hb_chooseomsidir, hb_footer, hb_footer_buttons, hb_mapinde, hb_ailist, hb_parkedv, hb_repaints;
	ClassLoader classLoader;
	ResourceBundle bundle;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primstage) throws Exception {
		bundle = ResourceBundle.getBundle("stringlist");
		classLoader = new launchermain().getClass().getClassLoader();
		
		//header image
		iv_header = new ImageView();
		iv_header.setImage(conf.image_header);
		iv_header.setFitWidth(430);
		iv_header.setPreserveRatio(true);
		iv_header.setSmooth(true);
		
		
		//main vertical box
		vmain = new VBox();
		vmain.setSpacing(10);
		vmain.setPadding(new Insets(15));
		
		
		//Choose Omsi Directory
		lb_chooseomsidir = new Label(bundle.getString("launcher.chooseomsidirectory"));
		lb_chooseomsidir.setPadding(new Insets(10, 0, 10, 0));
		hb_chooseomsidir = new HBox();
		hb_chooseomsidir.setSpacing(10);
		tf_chooseomsidir = new TextField();
		tf_chooseomsidir.setFocusTraversable(false);
		tf_chooseomsidir.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		tf_chooseomsidir.setEditable(false);
		tf_chooseomsidir.setPrefSize(300, 30);
		tf_chooseomsidir.setText(func.loadProp());
		btn_chooseomsidir = new Button(bundle.getString("launcher.choose"));
		btn_chooseomsidir.setPrefSize(100, 30);
		btn_chooseomsidir.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		btn_chooseomsidir.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				tf_chooseomsidir.setText(func.filechooser());
				func.saveProp(tf_chooseomsidir.getText());
			}
		});
		hb_chooseomsidir.getChildren().addAll(tf_chooseomsidir, btn_chooseomsidir);
				
		
		//install - deinstall map
		//Button Install Map
		tt_install = new Tooltip(bundle.getString("launcher.tooltip.mapinstallationneed"));
		lb_install = new Label(bundle.getString("launcher.yourmap"));
		lb_install.setPadding(new Insets(10, 0, 10, 0));
		btn_install = new Button(bundle.getString("launcher.installmap"));
		btn_install.setPrefSize(138, 30);
		btn_install.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: #00C865; -fx-text-fill: FFFFFF;");
		btn_install.setTooltip(tt_install);
		btn_install.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				if(checkPathAvail() == false || !tf_chooseomsidir.getText().contains("OMSI")) {
					outputsettext("[!] "+bundle.getString("function.nodirectoryfound"));
				} else {
					if(func.dirExists(tf_chooseomsidir.getText()+"\\maps\\"+conf.mapfoldername, conf.mapfoldername).contains(bundle.getString("function.installed"))) {
						deleteMap();
					}
					//install process
					for(int i=0; i < conf.mapcontent_installmap.length; i++) {
				        String srcDir = new File(classLoader.getResource("mapcontent"+conf.mapcontent_installmap[i]).getFile()).toString();
				        srcDir = srcDir.replace("%20", " ");
				        srcDir = srcDir.replace("%5c", "\\");
						String destdir = new StringBuilder(tf_chooseomsidir.getText()).append(conf.mapcontent_installmap[i]).toString();
					
						copyDir(srcDir, destdir);
						outputsettext(conf.mapcontent_installmap[i]+" installed");
					}
							
					//after instll
					outputsettext("[#] "+bundle.getString("launcher.mapinstalled"));
				}
			}
		});
		//Button Delete Map
		tt_deinstall = new Tooltip(bundle.getString("launcher.tooltip.deinstallationinfo"));
		btn_deinstall = new Button(bundle.getString("launcher.deletemap"));
		btn_deinstall.setPrefSize(138, 30);
		btn_deinstall.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: #E13720; -fx-text-fill: FFFFFF;");
		btn_deinstall.setTooltip(tt_deinstall);
		btn_deinstall.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				if(checkPathAvail() == false) {
					outputsettext("[!] "+bundle.getString("function.nodirectoryfound"));
				} else {
					//deinstall map
					deleteMap();
				}
			}
		});
		
		//Button Start OMSI2
		btn_startomsi = new Button(bundle.getString("launcher.startomsi"));
		btn_startomsi.setPrefSize(138, 30);
		btn_startomsi.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: #FFD168; -fx-text-fill: FFFFFF;");
		btn_startomsi.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				if(checkPathAvail() == false) {
					outputsettext("[!] "+bundle.getString("function.nodirectoryfound"));
				} else if(func.fileExists(tf_chooseomsidir.getText()+"\\Omsi.exe")==false){
					outputsettext(bundle.getString("function.omsiexenotfound"));
				} else {
					//start omsi2 .exe
					outputsettext(bundle.getString("launcher.omsistart"));
					func.startOmsi(tf_chooseomsidir.getText()+"\\Omsi.exe");
				}
			}
		});
		
		hb_mapinde = new HBox();
		hb_mapinde.setSpacing(10);
		hb_mapinde.getChildren().addAll(btn_install, btn_deinstall, btn_startomsi);
		
		
		btn_mapinstalled = new Button(bundle.getString("launcher.ismapinstalled"));
		btn_mapinstalled.setMaxSize(Double.MAX_VALUE, 30);
		btn_mapinstalled.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		btn_mapinstalled.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(checkPathAvail() == false) {
					outputsettext("[!] "+bundle.getString("function.nodirectoryfound"));
				} else {
					String omsidirpath = tf_chooseomsidir.getText();
					String omsidirpath_rg = new StringBuilder(omsidirpath).append("\\maps\\"+conf.mapfoldername).toString();
					String avail = func.dirExists(omsidirpath_rg, conf.mapfoldername);
					if(avail.contains(bundle.getString("function.notinstalled"))) {
						outputsettext(avail);
					} else {
						outputsettext(avail);
					}
				}
			}
		});
		
		
		btn_fremdaddons = new Button(bundle.getString("launcher.mapready"));
		btn_fremdaddons.setMaxSize(Double.MAX_VALUE, 30);
		btn_fremdaddons.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		btn_fremdaddons.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {		
				checkAddons();
			}
		});
		
		
		btn_credits = new Button(bundle.getString("launcher.readmehelpandcredits"));
		btn_credits.setMaxSize(Double.MAX_VALUE, 30);
		btn_credits.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		btn_credits.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String pathpdf = new StringBuilder(tf_chooseomsidir.getText()).append("\\Addons\\"+conf.mapfoldername+"\\Readme.pdf").toString();
				File f = new File(pathpdf);
				if(f.exists()&&f.isFile()) {
					func.credit(pathpdf);
				} else {
					String srcDir = new File(classLoader.getResource("mapcontent\\Addons\\"+conf.mapfoldername+"\\Readme.pdf").getFile()).toString();
			        srcDir = srcDir.replace("%20", " ");
			        srcDir = srcDir.replace("%5c", "\\");
					func.credit(srcDir);
				}
			}
		});
		
		
		//other options:
		lb_otherthings = new Label(bundle.getString("launcher.otherthings"));
		lb_otherthings.setPadding(new Insets(10, 0, 10, 0));
		
		
		//Choose AiList
		hb_ailist = new HBox();
		hb_ailist.setSpacing(10);
		choice_ailist = new ChoiceBox<>();
		choice_ailist.setPrefSize(200, 30);
		choice_ailist.setId("choicebox");
		choice_ailist.getItems().addAll("MAN EN92 + Default Cars","Other Bus + Default Cars");
		choice_ailist.setValue("MAN EN92 + Default Cars");
		
		
		btn_ailist = new Button(bundle.getString("launcher.activateailist"));
		btn_ailist.setPrefSize(200, 30);
		btn_ailist.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: #00C865; -fx-text-fill: FFFFFF;");
		btn_ailist.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				if(choice_ailist.getValue().equals("MAN EN92 + Default Cars")) {
					String srcDir = new File(classLoader.getResource("mapcontent"+conf.ailist[0]).getFile()).toString();
			        srcDir = srcDir.replace("%20", " ");
			        srcDir = srcDir.replace("%5c", "\\");
					String destdir = new StringBuilder(tf_chooseomsidir.getText()).append("\\maps\\"+conf.mapfoldername).toString();
					copyDir(srcDir, destdir);
					outputsettext("MAN EN92 + Default Cars aktivated");
				} else if(choice_ailist.getValue().equals("Other Bus + Default Cars")) {
					String srcDir = new File(classLoader.getResource("mapcontent"+conf.ailist[1]).getFile()).toString();
			        srcDir = srcDir.replace("%20", " ");
			        srcDir = srcDir.replace("%5c", "\\");
					String destdir = new StringBuilder(tf_chooseomsidir.getText()).append("\\maps\\"+conf.mapfoldername).toString();
					copyDir(srcDir, destdir);
					outputsettext("Other Bus + Default Cars aktivated");
				}
			}
		});
		hb_ailist.getChildren().addAll(choice_ailist, btn_ailist);
		
		
		//choose parkedvehicles
		hb_parkedv = new HBox();
		hb_parkedv.setSpacing(10);
		choice_park = new ChoiceBox<>();
		choice_park.setPrefSize(200, 30);
		choice_park.setId("choicebox");
		choice_park.getItems().addAll("Default", "Kaiserstadt Aachen");
		choice_park.setValue("Default");
		
		
		btn_parkedv = new Button(bundle.getString("launcher.activateparkedveh"));
		btn_parkedv.setPrefSize(200, 30);
		btn_parkedv.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: #00C865; -fx-text-fill: FFFFFF;");
		btn_parkedv.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(choice_park.getValue().equals("Default")) {
					//Parkliste Standard
					String srcDir = new File(classLoader.getResource("mapcontent"+conf.parkedveh[0]).getFile()).toString();
			        srcDir = srcDir.replace("%20", " ");
			        srcDir = srcDir.replace("%5c", "\\");
					String destdir = new StringBuilder(tf_chooseomsidir.getText()).append("\\maps\\"+conf.mapfoldername).toString();
					copyDir(srcDir, destdir);
					outputsettext("Default Parking-List activated");
				} else if(choice_park.getValue().equals("Kaiserstadt Aachen")) {
					//Parkliste AC
					String srcDir = new File(classLoader.getResource("mapcontent"+conf.parkedveh[1]).getFile()).toString();
			        srcDir = srcDir.replace("%20", " ");
			        srcDir = srcDir.replace("%5c", "\\");
					String destdir = new StringBuilder(tf_chooseomsidir.getText()).append("\\maps\\"+conf.mapfoldername).toString();
					copyDir(srcDir, destdir);
					outputsettext("Kaiserstadt Aachen Parking-List activated");
				}
			}
		});
		hb_parkedv.getChildren().addAll(choice_park, btn_parkedv);
		
		
		//choose speciell repaints
		hb_repaints = new HBox();
		hb_repaints.setSpacing(10);
		choice_repaints = new ChoiceBox<>();
		choice_repaints.setPrefSize(200, 30);
		choice_repaints.setId("choicebox");
		choice_repaints.getItems().addAll("BUS 1", "BUS 2");
		choice_repaints.setValue("BUS 1");
		
		
		btn_repaints = new Button(bundle.getString("launcher.installrepaint"));
		btn_repaints.setPrefSize(200, 30);
		btn_repaints.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-color: #00C865; -fx-text-fill: FFFFFF;");
		btn_repaints.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(choice_repaints.getValue().equals("BUS 1")) {
					//Repaint MAN by V3D
					String srcDir = new File(classLoader.getResource("mapcontent"+conf.repaints[0]).getFile()).toString();
			        srcDir = srcDir.replace("%20", " ");
			        srcDir = srcDir.replace("%5c", "\\");
					String destdir = new StringBuilder(tf_chooseomsidir.getText()).append("\\Vehicles\\BUS 1").toString();
					copyDir(srcDir, destdir);
					outputsettext("BUS 1 installed");
				} else if(choice_repaints.getValue().equals("BUS 2")) {
					//Repaint AC Stadtbus
					String srcDir = new File(classLoader.getResource("mapcontent"+conf.repaints[1]).getFile()).toString();
			        srcDir = srcDir.replace("%20", " ");
			        srcDir = srcDir.replace("%5c", "\\");
					String destdir = new StringBuilder(tf_chooseomsidir.getText()).append("\\Vehicles\\BUS 2").toString();
					copyDir(srcDir, destdir);
					outputsettext("BUS 2 installed");
				}
			}
		});
		hb_repaints.getChildren().addAll(choice_repaints, btn_repaints);
		
		
		lb_output = new Label(bundle.getString("launcher.output"));
		lb_output.setPadding(new Insets(10, 0, 2, 0));
		ta_note = new TextArea();
		ta_note.setEditable(false);
		ta_note.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
		
		
		//Footer
		Separator s = new Separator();
		lb_launcherakego = new Label();
		lb_launcherakego.setStyle("-fx-opacity: 0.44;");
		lb_launcherakego.setTextAlignment(TextAlignment.CENTER);
		lb_launcherakego.setText("Launcher by AkEgo");
		lb_launcherakego.setMaxSize(138, 20);
		lb_launcherakego.setOnMousePressed(new EventHandler<MouseEvent>() {	
			@Override
			public void handle(MouseEvent event) {
				func.openweb("https://egolex.github.io/omsimaplauncher/");
			}
		});
				
		lb_modifiedby = new Label();
		lb_modifiedby.setStyle("-fx-opacity: 0.44;");
		lb_modifiedby.setTextAlignment(TextAlignment.CENTER);
		lb_modifiedby.setText("Modified by "+conf.mapcreator);
		lb_modifiedby.setMaxSize(150, 20);
		lb_modifiedby.setOnMousePressed(new EventHandler<MouseEvent>() {	
			@Override
			public void handle(MouseEvent event) {
				func.openweb(conf.link_launchermodifierprofile);
			}
		});
				
		iv_socialmedia = new ImageView();
		iv_socialmedia.setImage(conf.image_ico_socialmedia);
		iv_socialmedia.setFitHeight(30);
		iv_socialmedia.setPreserveRatio(true);
		iv_socialmedia.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				func.openweb(conf.link_socialmedia);	
			}
		});
		
		iv_download = new ImageView();
		iv_download.setImage(conf.image_ico_directdownload);
		iv_download.setFitHeight(30);
		iv_download.setPreserveRatio(true);
		iv_download.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				func.openweb(conf.link_directdownload);	
			}
		});
		
		iv_omsiforum = new ImageView();
		iv_omsiforum.setImage(conf.image_ico_omsiforum);
		iv_omsiforum.setFitHeight(30);
		iv_omsiforum.setPreserveRatio(true);
		iv_omsiforum.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				func.openweb(conf.link_omsiforum);	
			}
		});
		
		hb_footer_buttons = new HBox();
		hb_footer_buttons.setSpacing(7);
		hb_footer_buttons.setPadding(new Insets(5));
		hb_footer_buttons.setAlignment(Pos.CENTER);
		hb_footer_buttons.setMaxSize(138, Double.MAX_VALUE);
		hb_footer_buttons.getChildren().addAll(iv_socialmedia, iv_download, iv_omsiforum);
		
		hb_footer = new HBox();
		hb_footer.setSpacing(10);
		hb_footer.setPadding(new Insets(7));
		hb_footer.setAlignment(Pos.CENTER);
		hb_footer.getChildren().addAll(lb_launcherakego, lb_modifiedby, hb_footer_buttons);
		vfooter = new VBox();
	    vfooter.getChildren().addAll(s, hb_footer);
		
		
		//add content to scene 
		vmain.getChildren().addAll(lb_chooseomsidir, hb_chooseomsidir, lb_install, hb_mapinde, btn_mapinstalled, btn_fremdaddons, btn_credits, lb_otherthings, hb_ailist, hb_parkedv, hb_repaints, lb_output, ta_note);
		bp.setTop(iv_header);
		bp.setCenter(vmain);
		bp.setBottom(vfooter);
		
		
		//full-scene
		Scene scene = new Scene(bp, 420, 720);
		scene.getStylesheets().add(getClass().getResource("styles.css").toString());
		primstage.getIcons().add(conf.image_logo);
		primstage.setTitle(conf.launcher_name);
		primstage.setResizable(false);
		primstage.setScene(scene);
		primstage.show();
	}
	
	//other things
	
	private Boolean checkPathAvail() {
		boolean avail;
		
		if(tf_chooseomsidir.getText().equals("") || tf_chooseomsidir.getText().equals(bundle.getString("function.nodirectoryfound"))) {
			avail = false;
		} else {
			avail = true;
		}
		
		return avail;
	}
	
	private String checkAddons() {
		String missing_objects = "";
		//check for all addons
		for(int i = 0; i < conf.addons_path.length; i++) {
			String sb = new StringBuilder(tf_chooseomsidir.getText()).append(conf.addons_path[i]).toString();
			String check = func.dirExists(sb, conf.addons_name[i]);
			if(check.contains(bundle.getString("function.notinstalled"))) {
				missing_objects = missing_objects+check+"\n";
			}
		}
		//output
		if(missing_objects.equals("")) {
			outputsettext(bundle.getString("function.alladdonsinstalled"));
		} else {
			outputsettext(bundle.getString("function.thirdpartyaddonsmissing")+"\n"+missing_objects);
		}
		return null;
	}
	
	private void copyDir(String copydir, String destdir) {
        File srcDir = new File(copydir);
        // The destination directory to copy to.
        File destDir = new File(destdir);

        try {
            // Copy source directory into destination directory
            // including its child directories and files.
            FileUtils.copyDirectory(srcDir, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void deleteMap() {
		File mapdir = new File(tf_chooseomsidir.getText()+"\\maps\\"+conf.mapfoldername);
		try {
			FileUtils.deleteDirectory(mapdir);
			outputsettext(bundle.getString("launcher.mapdeinstalled"));
		} catch (IOException e) {
			outputsettext(bundle.getString("launcher.errordeinstall"));
			e.printStackTrace();
		}
	}
	
	private void outputsettext(String str) {
		if(ta_note.getText().equals("")) {
			ta_note.setText("*"+str+"\n");
		} else {
			String temp = ta_note.getText();
			ta_note.setText(temp+"*"+str+"\n");
			ta_note.appendText("");
			ta_note.textProperty().addListener(new ChangeListener<Object>() {
			    @Override
			    public void changed(ObservableValue<?> observable, Object oldValue,
			            Object newValue) {
			        ta_note.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
			    }
			});
		}
	}

}
