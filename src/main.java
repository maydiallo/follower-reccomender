
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import system.functions.mainPage;
import system.functions.startPage;
/**
 * @author Maimouna Diallo
 * 219010356
 * miniproject
 */
public class main extends Application{
	private mainPage mainPane = null;
	private Stage mainStage = new Stage();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		launch(args);
		
		
	}
	
	/**
	 * Sets up the the user interface 
	 *param primaryStage
	 */
	@SuppressWarnings("unused")
	public void start(Stage primaryStage) throws Exception {
		//Set up the Application
		startPage root = new startPage(primaryStage, mainStage);
		ScrollPane sp = new ScrollPane(root);
		Scene scene = new Scene(sp,800,700);
		primaryStage.setTitle("Navigation");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		//Setting up the the next stage
		mainPane = new mainPage(mainStage);
		try {
			ScrollPane sp1 = new ScrollPane(mainPane);
			Scene mainScene = new Scene(sp1, 1000, 700); // Placing the pane in the Scene.
			mainStage.setTitle("Main");
			mainStage.setScene(mainScene); // Adding the Scene to the Stage.
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		

        
	}
	
	
      
}
