package application;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import databasePart1.CurrentUser;

import databasePart1.DatabaseHelper;




public class StartCSE360 extends Application {

	private static final DatabaseHelper databaseHelper = new DatabaseHelper();
	
	public static Image w5;
    public static ImageView iv = new ImageView();

	
	
	public static Font vr = new Font(20);
	public static Font vrs = new Font(20);
	
	
	public static CurrentUser currentUser = new CurrentUser();
	
	public static void main( String[] args )
	{
		System.out.print(System.getProperty("user.dir"));
		
		try {
			w5 = new Image(new FileInputStream("./src/application/W5.png"));
			iv = new ImageView(w5);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			vr = Font.loadFont(new FileInputStream("./src/application/VarelaRound.ttf"), 40);
			vrs = Font.loadFont(new FileInputStream("./src/application/VarelaRound.ttf"), 18);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 launch(args);
	}
	
	@Override
    public void start(Stage primaryStage) {
        try {
            databaseHelper.connectToDatabase(); // Connect to the database
            
            if (databaseHelper.isDatabaseEmpty()) {
            	
            	new FirstPage(databaseHelper).show(primaryStage);
            } else {
            	new SetupLoginSelectionPage(databaseHelper).show(primaryStage);
                
            }
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
    }
	

}
