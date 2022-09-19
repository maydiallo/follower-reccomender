package system.functions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Maimouna Diallo
 * 219010356
 * miniproject
 */
public class startPage extends StackPane {
	//Variables
	private static final int WIDTH = 800;
	private static final int HEIGHT= 700;
	
	
	/**
	 * @param help
	 * @param main
	 * @param game
	 * creates the main page for the program
	 */
	public startPage(Stage first, Stage main)
	{
		
		this.setPrefSize(WIDTH, HEIGHT);
		this.setStyle("-fx-background-color: linear-gradient(to bottom,#833AB4, #E1306C,#F77737);");
		populate(first, main);
	}
	
	/**
	 * @param help
	 * @param main
	 * Places all components formatted onto the main page
	 */
	public void populate(Stage help, Stage main)
	{
		//Title that will be displayed
		Text Title = new Text();
		Title.setText("Find A Friend");
		Title.setFill(Color.WHITE);
		setAlignment(Title, Pos.TOP_CENTER);
		Title.setFont(Font.font("Times New Roman", 120));

		//All the buttons and functionalities are made
		Button btnStart = new Button("Start");
		btnStart.setTranslateX(0);
		btnStart.setStyle("-fx-text-fill: white");
		btnStart.setTranslateY(0);
		btnStart.setStyle("-fx-background-color: white; -fx-text-fill: black");
		start(btnStart, help, main);
		
		Button btnExit = new Button("Exit");
		btnExit.setStyle("-fx-background-color: white; -fx-text-fill: black");
		btnExit.setTranslateX(0);
		btnExit.setTranslateY(100);
		exit(btnExit);
		
		//Added to the pane
		this.getChildren().add(Title);
		this.getChildren().add(btnStart);
		this.getChildren().add(btnExit);
	}
	
	
	/**
	 * @param btnExit
	 * closes program when clicked
	 */
	private void exit(Button btnExit)
	{
		
		EventHandler<ActionEvent> exitGame = new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e)
		    	{
		            System.exit(0);
		    	}
			 };
			 btnExit.setOnAction(exitGame);
	}
	

	/**
	 * @param btnStart
	 * @param close
	 * @param open
	 * starts the program
	 */
	private void start(Button btnStart, Stage close, Stage open)
	{	
		
		EventHandler<ActionEvent> gamePage = new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e)
		    	{
		    		
		    			close.close();
		    			open.show();
		   
		    		
		    	}
			 };
			 btnStart.setOnAction(gamePage);
	}
	
	
	
	
	
	
	 
}


