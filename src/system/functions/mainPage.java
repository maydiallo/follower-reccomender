package system.functions;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import com.jwetherell.algorithms.data_structures.Graph.Edge;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 * @author Maimouna Diallo
 * 219010356
 * miniproject
 */
public class mainPage extends GridPane{ 
	
	//Variables
	private Label lblg1username;
	private Label lblg1followers;
	private Label lblg1following;
	private Label lblg1addfollower;
	private Label lblg1delfollower;
	private Label lblg1updatefollower;
	private Label lblRecommended;
	private Label lblConnect;
	private Label lbledge1;
	private Label lblVisible;
	
	private TextField txtg1username;
 	private TextField txtg1followers;
 	private TextField txtg1following;
	private TextField txtg1addfollower;
 	private TextField txtg1updatenewinfo;
	private TextArea txtRecommended;
	private TextField txtedge1;
	private TextField txtVisible;
	
 	private Button btng1Delete; 
	private Button btng1Add;
	private Button btnViewgraph;
	private Button btnRecommend;
	private Button btnLogin;
 	private Button btnAddEdge1;
 	private Button btnExit;

	private static ObservableList<String> g1options;
	private static ComboBox<String> cbg1delfollower;

 	private static List<Vertex<String>> prof1Vertices = new ArrayList<Vertex<String>>();
	private static List<Edge<String>> prof1Edges = new ArrayList<Edge<String>>();
	private static List<InstaProfile> allProfiles = new ArrayList<InstaProfile>();
	private static  Vertex<String> mainVertex ;
	private static Vertex<String> newVertice=null;
	private static LinkedList<String> mainFollowingList;
	private static int GraphIndex=1;
	private static Viewer viewer=null;
	private static boolean created=false;
	private static LinkedList<String> check= new LinkedList<String>();
	   
	
	private static final int WIDTH = 1000;
	private static final int HEIGHT= 700;


	private static int Location;

	static SingleGraph graph = new SingleGraph("Following Graph");

	
	/**
	 * @param stage
	 * Sets up the all the buttons functionality
	 */
	public mainPage(Stage stage) {
		
	
		createPoints();
		createProfiles();
		
		mainFollowingList= depthFirstSearch(mainVertex);
	
		setUI();
		Editable(created);
		
		//Changes the the main account's username and creates the graph
		 btnLogin.setOnAction(e-> { 
				
			 	if(!txtg1updatenewinfo.getText().isBlank())
				{
					updateVerticeName(txtg1updatenewinfo.getText(), prof1Vertices);
				}
					
				
			 	txtg1username.setText(String.valueOf(allProfiles.get(0).getUserName()));
				txtg1followers.setText(String.valueOf(allProfiles.get(0).getUserFollowers()));
		 	 	txtg1following.setText(String.valueOf(allProfiles.get(0).getUserFollowing()));
		 	 	txtVisible.setText(allProfiles.get(0).isVisible());
		 	 	txtg1updatenewinfo.setEditable(false);
		 	 	createGraph(mainFollowingList);
		 	 	created=true;
		 	 	Editable(created);
	    	
	        } );
		 
		
		 //Deletes a follower from the graph 
		btng1Delete.setOnAction(e-> { 
			
			if(cbg1delfollower.getValue()==null)
			{
				errorMessage("You did not select a follower");
				return;
			}else 
			{
				 delete(prof1Vertices,prof1Edges,String.valueOf(cbg1delfollower.getValue()),mainFollowingList);
				 removeNode(String.valueOf(cbg1delfollower.getValue()));
				deletefromCombobox(String.valueOf(cbg1delfollower.getValue()),cbg1delfollower); 
			}
			
			
        } );
		
		
		//Adds a follower to the graph
		btng1Add.setOnAction(e-> {  
			
			if(CheckIfExists(mainFollowingList,txtg1addfollower.getText()))
	    	{
	    		return;
	    	}else if(txtg1addfollower.getText().isBlank()) 
	    	{
	    		errorMessage("Please type in a username");
	    	}else 
	    	{
	    		check.clear();
	    		newVertice= createVertice(mainFollowingList,prof1Vertices,txtg1addfollower.getText());
	    		createNode(txtg1addfollower.getText());
	    		addtoCombobox(txtg1addfollower.getText(),cbg1delfollower);
	    		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
	   			 alert.setTitle("Message");
	   			 alert.setHeaderText("Success");
	   			 alert.setContentText(txtg1addfollower.getText()+ " has been followed");
	   			 alert.showAndWait();
	    	}
			lblConnect.setText("Follow people for " + txtg1addfollower.getText());
			
        } );
		
		//Follow people for the new account followed
		btnAddEdge1.setOnAction(e-> {  

			LinkedList<String> newList= depthFirstSearch(newVertice);
			
			if(newVertice==null)
	    	{
	    		
	    		errorMessage("Provide an account to follow");
	    	}else if(txtg1addfollower.getText().equals(txtedge1.getText()) ||txtedge1.getText().isBlank())
	    	{
	    		
	    		errorMessage("Enter a valid username");
	    	}else if(CheckIfExists(newList,txtedge1.getText()) )
	    	{
	    		return;
	    	} else
			{	
	    		createEdge(prof1Vertices,prof1Edges,newVertice, txtedge1.getText());
	    		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
	   			 alert.setTitle("Message");
	   			 alert.setHeaderText("Success");
	   			 alert.setContentText(txtedge1.getText()+ " has been followed");
	   			 alert.showAndWait();
	   			 check.add(txtedge1.getText());
	   			 
	   			 
	    	}
			
		
			
        } );
		
		//Displays the graph 
		btnViewgraph.setOnAction(e-> { 
			viewGraph();
        } );

		//Reccommends accounts that should be followed
		btnRecommend.setOnAction(e-> { 
			
			LinkedList<String> followList= reccomend(mainFollowingList,prof1Vertices);
			System.out.println(followList.toString());
			
			for(int r =0; r< allProfiles.size(); r++)
	    	{
			
				for(int j=0; j<followList.size(); j++)
	    		{
	    			if(followList.get(j).equals(allProfiles.get(r).getUserName()))
	    			{
	    				System.out.println(allProfiles.get(r).getUserName());
	    				txtRecommended.appendText("Username: "+allProfiles.get(r).getUserName()+ "\r\n");
	    				txtRecommended.appendText("Followers: "+allProfiles.get(r).getUserFollowers()+ "\r\n");
	    				txtRecommended.appendText("Following: "+allProfiles.get(r).getUserFollowing()+"\r\n");
	    				txtRecommended.appendText("AccountType: "+allProfiles.get(r).isVisible()+"\r\n");
	    				txtRecommended.appendText("\r\n");
	    				
	    			}
	    		}
    		
	    	}
			
			if(created==true)
			{
				addNewPoints(followList);
			}
			
        } );
		
		//Close the system
		btnExit.setOnAction(e-> { 
			stage.close();
			System.exit(0);
        } );

	}
	


	/**
	 * Sets up user interface
	 */
	private void setUI()
	{
		 	 setHgap(5);
	 		 setVgap(5);
			 this.setPrefSize(WIDTH, HEIGHT);
			 setAlignment(Pos.CENTER);
			 this.setStyle("-fx-background-color: white");
			 
			 //initializing the variables
	 		 g1options =  FXCollections.observableArrayList(mainFollowingList);
	 		 cbg1delfollower = new ComboBox<String>(g1options);
	 		 cbg1delfollower.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-border-color: black;");
	 	
	 		  txtg1username= new TextField();
	 	 	  txtg1followers= new TextField();
	 	 	  txtg1following= new TextField();
	 		  txtg1addfollower= new TextField();
	 	 	  txtRecommended= new TextArea();
	 	 	  txtedge1= new TextField();
	 	 	  txtg1updatenewinfo= new TextField();
	 	 	  txtVisible= new TextField();

	 	 	  
	 	 	  lblg1username = new Label("Username");
	 	 	  lblg1followers = new Label("Followers");
	 	 	  lblg1following = new Label("Following");
	 	 	  lblg1addfollower = new Label("Follow a user:");
	 	 	  lblg1delfollower = new Label("Unfollow a user");
	 	 	  lblRecommended = new Label("Recommended people to follow:");
	 	 	  lblConnect = new Label("Follow people for your new user"); 
	 	 	  lbledge1 = new Label("Provide a username :");
	 	 	  lblg1updatefollower = new Label("Type In Your Username:");
	 	 	  lblVisible= new Label("Account Type:");
	 	
	 	 	  btng1Delete= new Button("Unfollow User"); 
	 	 	  btng1Add= new Button("Follow User");
	 	 	  btnViewgraph= new Button("View Graph");
	 	 	  btnRecommend= new Button("View Recommended Followers");
	 	 	  btnAddEdge1= new Button("Follow");
	 	 	  btnLogin= new Button("Log in");
			  btnLogin.setStyle("-fx-background-color: #1DA1F2; -fx-text-fill: white;");
			  btnExit=new Button("Exit");
			  btnExit.setStyle("-fx-background-color: #1DA1F2; -fx-text-fill: white;");
	 	 	
	 	 	
	 	 	  setButtonStyle(btng1Delete);
	 	 	  setButtonStyle(btng1Add);
	 	 	  setButtonStyle(btnViewgraph);
	 	 	  setButtonStyle(btnRecommend);
	 	 	  setButtonStyle(btnAddEdge1);
 
	 	 	  //changing the editable properties 
	 	 	  txtg1username.setEditable(false);
	 	 	  txtg1followers.setEditable(false);
	 	 	  txtg1following.setEditable(false);
	 	 	  txtRecommended.setEditable(false);
	 	 	  txtVisible.setEditable(false);
	 	 	 
	 	 	  //adding the properties to the interface
	 	 	  add(lblg1updatefollower,0,0);
			  add(txtg1updatenewinfo,0,1);
			  add(btnLogin,1,1);
	 	 	  add(lblg1username,0,2);
	 	 	  add(txtg1username,3,2);
	 	 	  add(lblg1followers,0,5);
	 	 	  add(txtg1followers,3,5);
	 	 	  add(lblg1following,0,7);
	 	 	  add(txtg1following,3,7);
	 	 	  add(lblVisible,0,9);
	 	 	  add(txtVisible,3,9);
	 	 	
	 	 	  add(lblg1delfollower,0,11);
	 	 	  add(cbg1delfollower,0,13);
	 	 	  add(btng1Delete,0,15); 	
	 	 	  
	 	 	  add(lblg1addfollower,0,17);
	 	 	  add(txtg1addfollower,0,19);
	 	 	  add(btng1Add,0,21);
	 	 	  
	 	 	  add(lblConnect,0,23);
	 	 	  add(lbledge1,0,25);
	 	 	  add(txtedge1,3,25);
	 	 	  add(btnAddEdge1,5,25);	 	  
	 	 	  add(btnRecommend,0,27);
	 	 	  add(lblRecommended,0,29);
	 	 	  add(txtRecommended,0,31);
	 	 	  add(btnViewgraph,0,33);
	 	 	  add(btnExit,0,37);
	 	 	

	 	 	
	 	 
	 	}
	 	 
	 
	 /**
	 * Creates Vertices and Edges
	 */
	private static void createPoints()
		{
			
		 mainVertex = new Vertex<String>("likelihoodart");
		 Vertex<String> mainVert0 = new Vertex<String>("sonagee");
		 Vertex<String> mainVert1 = new Vertex<String>("izeichan");
		 Vertex<String> mainVert2 = new Vertex<String>("srkork");
		 Vertex<String> mainVert3 = new Vertex<String>("ctchrysler");
		 Vertex<String> mainVert4 = new Vertex<String>("nekokonut");
		 Vertex<String> mainVert5 = new Vertex<String>("bapplebubble");
		
		 Vertex<String> mainVert6 = new Vertex<String>("pentOpeach");
		 Vertex<String> mainVert7 = new Vertex<String>("oseok");
		 Vertex<String> mainVert8 = new Vertex<String>("sarucatepes");
		 Vertex<String> mainVert9 = new Vertex<String>("dokidokistart");
		 Vertex<String> mainVert10 = new Vertex<String>("ohitsujiza");
		
	    
		
		
		 Edge<String> LIKELIedge = new Edge<String>(1, mainVertex, mainVert1);
		 Edge<String> LIKELIedge0 = new Edge<String>(1, mainVertex, mainVert0);
		 Edge<String> LIKELIedge1 = new Edge<String>(1, mainVertex, mainVert2);
		 Edge<String> LIKELIedge2 = new Edge<String>(1, mainVertex, mainVert3);
		 Edge<String> LIKELIedge3 = new Edge<String>(1, mainVertex, mainVert4);
		 Edge<String> LIKELIedge4 = new Edge<String>(1, mainVertex, mainVert5);
		 Edge<String> LIKELIedge5 = new Edge<String>(1, mainVertex, mainVert6);
		 Edge<String> LIKELIedge6 = new Edge<String>(1, mainVertex, mainVert7);
		 Edge<String> LIKELIedge7 = new Edge<String>(1, mainVertex, mainVert8);
		 Edge<String> LIKELIedge8 = new Edge<String>(1, mainVertex, mainVert9);
		 Edge<String> LIKELIedge9 = new Edge<String>(1, mainVertex, mainVert10);
		
		
		 Vertex<String> izeivert1 = new Vertex<String>("sonagee");
		 Vertex<String> izeivert2 = new Vertex<String>("maicrfft");
		 Vertex<String> izeivert3 = new Vertex<String>("linarts");
		 Vertex<String> izeivert4 = new Vertex<String>("phillipedraws");
		 Vertex<String> izeivert5 = new Vertex<String>("justarting");
		
		 Edge<String> izeiedge1 = new Edge<String>(2, mainVert1, izeivert1);
		 Edge<String> izeiedge2 = new Edge<String>(2, mainVert1, izeivert2);
		 Edge<String> izeiedge3 = new Edge<String>(2, mainVert1, izeivert3);
		 Edge<String> izeiedge4 = new Edge<String>(2, mainVert1, izeivert4);
		 Edge<String> izeiedge5 = new Edge<String>(2, mainVert1, izeivert5);
		
		
		 Vertex<String> srkorkvert1 = new Vertex<String>("sonagee");
		 Vertex<String> srkorkvert2 = new Vertex<String>("goldenclosetart");
		 Vertex<String> srkorkvert3 = new Vertex<String>("iwashere");
		Vertex<String> srkorkvert4 = new Vertex<String>("lookart");
		 Vertex<String> srkorkvert5 = new Vertex<String>("linmai");
		
		Edge<String> srkorkedge1 = new Edge<String>(2, mainVert2, srkorkvert1);
		Edge<String> srkorkedge2 = new Edge<String>(2, mainVert2, srkorkvert2);
		Edge<String> srkorkedge3 = new Edge<String>(2, mainVert2, srkorkvert3);
		Edge<String> srkorkedge4 = new Edge<String>(2, mainVert2, srkorkvert4);
		Edge<String> srkorkedge5 = new Edge<String>(2, mainVert2, srkorkvert5);
		
		Vertex<String> sylervert1 = new Vertex<String>("sonagee");
		Vertex<String> sylervert2 = new Vertex<String>("sonagee");
		Vertex<String> sylervert3 = new Vertex<String>("linarts");
		Vertex<String> sylervert4 = new Vertex<String>("artbaddie");
		Vertex<String> sylervert5 = new Vertex<String>("lostincraft");
		
		Edge<String> syleredge1 = new Edge<String>(2, mainVert3, sylervert1);
		Edge<String> syleredge2 = new Edge<String>(2, mainVert3, sylervert1);
		Edge<String> syleredge3 = new Edge<String>(2, mainVert3, sylervert1);
		Edge<String> syleredge4 = new Edge<String>(2, mainVert3, sylervert1);
		Edge<String> syleredge5 = new Edge<String>(2, mainVert3, sylervert1);
		
		Vertex<String> Nekovert1= new Vertex<String>("sonagee");
		Vertex<String> Nekovert2 = new Vertex<String>("leapart");
		Vertex<String> Nekovert3 = new Vertex<String>("yawzzn");
		Vertex<String> Nekovert4 = new Vertex<String>("yippie");
		Vertex<String> Nekovert5 = new Vertex<String>("goldenclosetart");
		
		Edge<String> Nekoedge1 = new Edge<String>(2, mainVert4, Nekovert1);
		Edge<String> Nekoedge2 = new Edge<String>(2, mainVert4, Nekovert2);
		Edge<String> Nekoedge3 = new Edge<String>(2, mainVert4, Nekovert3);
		Edge<String> Nekoedge4 = new Edge<String>(2, mainVert4, Nekovert4);
		Edge<String> Nekoedge5 = new Edge<String>(2, mainVert4, Nekovert5);
		
		
		Vertex<String> Bapplevert1 = new Vertex<String>("sonagee");
		Vertex<String> Bapplevert2 = new Vertex<String>("pjma");
		Vertex<String> Bapplevert3 = new Vertex<String>("agustV");
		Vertex<String> Bapplevert4 = new Vertex<String>("vante");
		Vertex<String> Bapplevert5 = new Vertex<String>("runchranda");
		
		Edge<String> Bappleedge1 = new Edge<String>(2, mainVert5, Bapplevert1);
		Edge<String> Bappleedge2 = new Edge<String>(2, mainVert5, Bapplevert2);
		Edge<String> Bappleedge3 = new Edge<String>(2, mainVert5, Bapplevert3);
		Edge<String> Bappleedge4 = new Edge<String>(2, mainVert5, Bapplevert4);
		Edge<String> Bappleedge5 = new Edge<String>(2, mainVert5, Bapplevert5);
		
		 Vertex<String> Sonvert1 = new Vertex<String>("goldenclosetart");
		 Vertex<String> Sonvert2 = new Vertex<String>("yuppee");
		 Vertex<String> Sonvert3 = new Vertex<String>("linarts");
		 Vertex<String> Sonvert4 = new Vertex<String>("LYtear");
		 Vertex<String> Sonvert5 = new Vertex<String>("maicrfft");
		
		
		 Edge<String> Sonedge1 = new Edge<String>(1, mainVert0, Sonvert1);
		 Edge<String> Sonedge2 = new Edge<String>(1, mainVert0, Sonvert2);
		 Edge<String> Sonedge3 = new Edge<String>(1, mainVert0, Sonvert3);
		 Edge<String> Sonedge4 = new Edge<String>(1, mainVert0, Sonvert4);
		 Edge<String> Sonedge5 =new Edge<String>(1, mainVert0, Sonvert5);


			prof1Vertices.add(mainVertex);
			prof1Vertices.add(mainVert0);
			prof1Vertices.add(mainVert1);
			prof1Vertices.add(mainVert2);
			prof1Vertices.add(mainVert3);
			prof1Vertices.add(mainVert4);
			prof1Vertices.add(mainVert5);
			prof1Vertices.add(mainVert6);
			prof1Vertices.add(mainVert7);
			prof1Vertices.add(mainVert8);
			prof1Vertices.add(mainVert9);
			prof1Vertices.add(mainVert10);
			prof1Vertices.add(izeivert1);
			prof1Vertices.add(izeivert2);
			prof1Vertices.add(izeivert3);
			prof1Vertices.add(izeivert4);
			prof1Vertices.add(izeivert5);
			prof1Vertices.add(srkorkvert1);
			prof1Vertices.add(srkorkvert2);
			prof1Vertices.add(srkorkvert3);
			prof1Vertices.add(srkorkvert4);
			prof1Vertices.add(srkorkvert5);
			prof1Vertices.add(sylervert1);
			prof1Vertices.add(sylervert2);
			prof1Vertices.add(sylervert3);
			prof1Vertices.add(sylervert4);
			prof1Vertices.add(sylervert5);
			prof1Vertices.add(Bapplevert1);
			prof1Vertices.add(Bapplevert2);
			prof1Vertices.add(Bapplevert3);
			prof1Vertices.add(Bapplevert4);
			prof1Vertices.add(Bapplevert5);
			prof1Vertices.add(Nekovert1);
			prof1Vertices.add(Nekovert2);
			prof1Vertices.add(Nekovert3);
			prof1Vertices.add(Nekovert4);
			prof1Vertices.add(Nekovert5);
			
			//adding edges
			prof1Edges.add(LIKELIedge);
			prof1Edges.add(LIKELIedge0);
			prof1Edges.add(LIKELIedge1);
			prof1Edges.add(LIKELIedge2);
			prof1Edges.add(LIKELIedge3);
			prof1Edges.add(LIKELIedge4);
			prof1Edges.add(LIKELIedge5);
			prof1Edges.add(LIKELIedge6);
			prof1Edges.add(LIKELIedge7);
			prof1Edges.add(LIKELIedge8);
			prof1Edges.add(LIKELIedge9);
			prof1Edges.add(izeiedge1);
			prof1Edges.add(izeiedge2);
			prof1Edges.add(izeiedge3);
			prof1Edges.add(izeiedge4);
			prof1Edges.add(izeiedge5);
			prof1Edges.add(srkorkedge1);
			prof1Edges.add(srkorkedge2);
			prof1Edges.add(srkorkedge3);
			prof1Edges.add(srkorkedge4);
			prof1Edges.add(srkorkedge5);
			prof1Edges.add(syleredge1);
			prof1Edges.add(syleredge2);
			prof1Edges.add(syleredge3);
			prof1Edges.add(syleredge4);
			prof1Edges.add(syleredge5);
			prof1Edges.add(Nekoedge1);
			prof1Edges.add(Nekoedge2);
			prof1Edges.add(Nekoedge3);
			prof1Edges.add(Nekoedge4);
			prof1Edges.add(Nekoedge5);
			prof1Edges.add(Bappleedge1);
			prof1Edges.add(Bappleedge2);
			prof1Edges.add(Bappleedge3);
			prof1Edges.add(Bappleedge4);
			prof1Edges.add(Bappleedge5);
			
			prof1Vertices.add(Sonvert1);
			prof1Vertices.add(Sonvert2);
			prof1Vertices.add(Sonvert3);
			prof1Vertices.add(Sonvert4);
			prof1Vertices.add(Sonvert5);
			
			prof1Edges.add(Sonedge1);
			prof1Edges.add(Sonedge2);
			prof1Edges.add(Sonedge3);
			prof1Edges.add(Sonedge4);
			prof1Edges.add(Sonedge5);
			
			//sets up an index for the vertices list
			Location=prof1Vertices.size();
			
						
		}
	
		//Profiles and Vertices Functions
	
		/**
		 * @return List of profiles made
		 * Creates profiles based on vertices
		 */
		private static List<InstaProfile> createProfiles()
		{
			LinkedList<String> tempList=new LinkedList<String>();
			for(int p=0; p<prof1Vertices.size(); p++) 
			{
						if(!tempList.contains(prof1Vertices.get(p).getValue())) 
						{
							tempList.add(prof1Vertices.get(p).getValue());
						}
			}
			
		
			for(int i =0; i<tempList.size(); i++)
			{
				
					allProfiles.add(new InstaProfile(tempList.get(i), genRand(), genRand(), genVisibility()));	
			}
			
			//System.out.println(tempList.toString());
			return allProfiles;			
		}
	

	    /**
	     * @param root
	     * @return depth first search list
	     * Performs a depth first search and returns a list 
	     */
	    private static LinkedList<String> depthFirstSearch(Vertex<String> root)
	    {       
	      
	        if(root == null)
	        {
	        	System.out.println("Value given is null");
	        	return null;
	        }else if(root.getVisit()==true)
	        {
	        	System.out.println("You have aready visited this node");
	        	return null;
	        }
	        
	        LinkedList<String> list = new LinkedList<>();
	    
	        for (Edge<?> e:prof1Edges)
	        {
	        	if(e.getFromVertex().getValue()==root.getValue() && e.getVisit()==false)
	        	{
	        			e.setVisit(true);
	        			list.addLast(String.valueOf(e.getToVertex().getValue()));
	        			
	        	}
	        	
	        }
	        
	        return list;
	       
	    }
	    
	    /**
	     * @param list
	     * @param compare
	     * @return list of people to follow
	     * Reccomends a list of people to follow
	     */
	    private LinkedList<String> reccomend(LinkedList<String> list, List <Vertex<String>> compare)
	    {  
	    	LinkedList<String> temp = new LinkedList<>();
	    	
	    	
	    	for(int i = 0; i <compare.size(); i++)
	    	{
	    		for(int c = 0; c <list.size();  c++)
	        	{
	    			if(list.get(c)==compare.get(i).getValue())
	    	    	{
	    	    			temp.addAll(depthFirstSearch(compare.get(i)));
	    	    		
	    	    	}
	        		
	        	}
	    	
	    	}
	    	
	    
	    	temp = identifyDuplicates(temp);
	       return temp;
	    }
	    

	    /**
	     * @param delVertice
	     * @param strVert
	     * @param delList
	     * Deletes vertice
	     */
	    private void delete(List<Vertex<String>> delVertice,List<Edge<String>> delEdges, String strVert, LinkedList<String> delList)
	    {  
	    	Location--;
	    	int tempIndex = 0;
	    	
	    	for(int i = 0; i <delVertice.size(); i++)
	    	{
	    		if(delVertice.get(i).getValue()== strVert)
	    		{
	    			tempIndex=i;
	    			
	    			delVertice.remove(i);
	    		}
	    	}
	    	
	    	for(int r = 0; r <delList.size(); r++)
	    	{
	    		if(delList.get(r)== strVert)
	    		{
	    			delList.remove(delList.get(r));
	    		}
	    	}
	    
	    	List<Edge<String>> tempList= delVertice.remove(tempIndex).getEdges();
	    	for(int p=0; p<delEdges.size();p++) 
	    	{
	    		for(int k=0; k<tempList.size();k++) 
		    	{
		    		if(tempList.get(k)==delEdges.get(p))
		    		{
		    			delEdges.remove(p);
		    			
		    		}
		    	}
	    	}
	    	
	    	
	    }
	    
	    
	     /**
	     * @param strUpdate
	     * @param updateList
	     * Updates the mainVertex in all the relevant places
	     */
	    private void updateVerticeName(String strUpdate,  List<Vertex<String>> updateList)
	    {  
	    		
	    	if(strUpdate!=null)
	    	{
	    		for(int i=0; i<updateList.size(); i++)
		    	{
		    		if(mainVertex==updateList.get(i))
		    		{
		    			updateList.get(i).setValue(strUpdate);
		    			
		    		}
		    	}
	    		
				
		    	allProfiles.get(0).setUserName(strUpdate);	
		    	mainVertex.setValue(strUpdate);
	    	}
	    		
	    }
	    
	    
	    /**
	     * @param createList
	     * @param createVertice
	     * @param strCreate
	     * @return Vertex
	     * Creates a Vertice that will follow the main account
	     */
	    private Vertex<String> createVertice(LinkedList<String> createList, List<Vertex<String>> createVertice, String strCreate)
	    {  
		    	Location++;
		    	createVertice.add(new Vertex<String>(strCreate));
		    	createList.add(strCreate);

			    return createVertice.get(Location-1);
		    	

	    }
	    
	    /**
	     * @param createList
	     * @param createEdge
	     * @param createVertice
	     * @param strCreate
	     * Creates an edge connected to the newly connected vertice 
	     */
	    private void createEdge(List<Vertex<String>> createList, List<Edge<String>> createEdge, Vertex<String> createVertice, String strCreate)
	    {  
	    		Location++;
	    		createList.add(new Vertex<String>(strCreate));
	    		createEdge.add(new Edge<String>(2, createVertice,prof1Vertices.get(Location-1)));
	    		newProfile(strCreate);
	    	
	    }
	    
	   
	  //Graph Functions
		
		/**
		 * @param list
		 * Creates the graph based on vertices
		 */
		private void createGraph(LinkedList<String> list)
		{
			
			graph.addNode(mainVertex.getValue());
			org.graphstream.graph.Node e1=graph.getNode(mainVertex.getValue());
			e1.setAttribute("ui.style", "shape:circle;fill-color: #405DE6;size: 90px; text-alignment: center;");
	    	e1.setAttribute("ui.label",mainVertex.getValue());
	    
			for(int i=0; i<list.size(); i++)
			{
				graph.addNode(list.get(i));
				org.graphstream.graph.Node node =graph.getNode(list.get(i));
				node.setAttribute("ui.style", "shape:circle;fill-color: #C13584;size: 90px; text-alignment: center;");
				node.setAttribute("ui.label",list.get(i));
				graph.addEdge("Vertice1-"+ i+ list.get(i), mainVertex.getValue(), list.get(i));
				GraphIndex+=1;
				
			}
	    	
		}
		
		
		/**
		 * @param newList
		 * Adds the reccomended people to follow to the graph
		 */
		private void addNewPoints(LinkedList<String> newList)
		{
			
			for(int j=0; j<mainFollowingList.size(); j++)
			{
				for(int i=0; i<newList.size(); i++)
				if(newList.get(i)== mainFollowingList.get(j))
				{
					newList.remove(i);
				}

			}
			
			for(int i=0; i<newList.size(); i++)
			{
				graph.addNode(newList.get(i));
				org.graphstream.graph.Node node =graph.getNode(newList.get(i));
				node.setAttribute("ui.style", "shape:circle;fill-color: #E1306C;size: 90px; text-alignment: center;");
				node.setAttribute("ui.label",newList.get(i));
				graph.addEdge("Vertice2-"+ i+ newList.get(i), mainVertex.getValue(), newList.get(i));
				
				
				
			}
			
			
	    	
		}
		
	    /**
	     * @param str
	     * deletes node from graph
	     */
	    private void removeNode(String str)
		{
			 	GraphIndex-=1;
				int check= graph.getNode(str).getIndex();
				graph.removeNode(check);
				org.graphstream.graph.Edge edge = graph.getEdge(str);
				graph.removeEdge(edge);
				
				for(int i =0; i<allProfiles.size(); i++)
				{
					if(allProfiles.get(i).getUserName()==str)
					{
						allProfiles.remove(i);
					}
				}
				
				
		}
	    
	    /**
	     * @param str
	     * Creates a node in the graph
	     */
	    private static void createNode(String str)
		{
			GraphIndex+=1;
			graph.addNode(str);
			org.graphstream.graph.Node node =graph.getNode(str);
			node.setAttribute("ui.style", "shape:circle;fill-color: #FCAF45;size: 90px; text-alignment: center;");
			node.setAttribute("ui.label",str);
			graph.addEdge("Vertice1-"+ GraphIndex + str, mainVertex.getValue(), str);
			
			newProfile(str);
			
			
		}
	    
	    private static void newProfile(String str)
		{
	    	LinkedList<String> tempList = new LinkedList<String>();
	    	for(int i=0; i<allProfiles.size(); i++) 
			{
				tempList.add(allProfiles.get(i).getUserName());
			}
			if (!tempList.contains(str)) {
				allProfiles.add(new InstaProfile(str, genRand(), genRand(), genVisibility()));
			}
			
		}
	    
	    /**
	     * Displays the graph
	     */
	    private void viewGraph()
	    { 
	    	System.setProperty("org.graphstream.ui", "swing");
	    	viewer=graph.display();
	    	 viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
			
	    }
		
	    
	  //Helper Functions
		
		/**
		 * @return random boolean 
		 * Determines whether a user is public or private
		 */
		private static boolean genVisibility()
		{
			 
			 Random rand = new Random();
			 boolean Visibility = rand.nextBoolean();
		     
			return Visibility;			
		}
		
		/**
		 * @return random integer
		 * Determines the amount of followers/people following
		 */
		private static int genRand()
		{
			 int min = 25;
			 int max =50;
			 int range = max-min+1;
			 Random rand = new Random();
		     int randNum = min + rand.nextInt(range);
		    
			return randNum;					
		}
		
		 /**
	     * @param strERR
	     * Shows an error message
	     */
	    private static void errorMessage(String strERR)
	    {  
	    	
	    			 Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    			 alert.setTitle("Error");
	    			 alert.setHeaderText("Try Again!");
	    			 alert.setContentText(strERR);
	    			 alert.showAndWait();
	   
	    }
	    
	    /**
	     * @param edit
	     * Sets text field to either be editable or not
	     */
	    private void Editable(boolean edit) {
	    	cbg1delfollower.setEditable(edit);
	    	txtg1addfollower.setEditable(edit);
	    	txtedge1.setEditable(edit);
	    }
	    
	    /**
	     * @param strDelete
	     * @param cbDel
	     * Deletes value from combo box
	     */
	    private void deletefromCombobox(String strDelete,ComboBox<String> cbDel)
	    {  
	    	
	    	cbDel.getItems().remove(strDelete);
	    }
	    
	    /**
	     * @param strAdd
	     * @param cbAdd
	     * Adds value to combo box
	     */
	    private void addtoCombobox(String strAdd,ComboBox<String> cbAdd)
	    {  
	    	
	    	cbAdd.getItems().add(strAdd);
	    }
	    
	    /**
	     * @param checkList
	     * @param strCheck
	     * @return boolean
	     * Checks if a value already exists in a list if so display a message
	     */
	    private boolean CheckIfExists(LinkedList<String> checkList, String strCheck)
	    {  
	    	boolean check = false;
	    	if(mainVertex.getValue().equals(strCheck)) {
	    		check= true;
	    		errorMessage("This is your own account, provide another username");
    		}else 
    		{
    			for(int i = 0; i <checkList.size(); i++)
    	    	{
    	    		if(checkList.get(i).equals(strCheck))
    	    		{
    	    			check= true;
    	    			errorMessage("You are already following this account");
    	    			
    	    		} 
    	    	
    	    	}
    		}

	    	return check;
	    }
	    
	    /**
	     * @param list
	     * @return a list of only duplicates
	     * Identifies duplicates and makes a list of them 
	     */
	    private static LinkedList<String> identifyDuplicates(LinkedList<String> list)
	    {  
	    	LinkedList<String> duplicates = new LinkedList<>();
	    	for(int i = 0; i <list.size(); i++)
	    	{
	    		for(int c = i+1; c <list.size();  c++)
	        	{
	        		if(list.get(i).equals(list.get(c)))
	        		{
	        			if(!(duplicates.contains(list.get(i))))
	        			{
	        				duplicates.add(list.get(c));
	        			}
	        			
	        		}
	        	}
	    	}
	    	
	       return duplicates;
	    }
	    
	    /**
	 	 * @param btn
	 	 * Sets a button's style
	 	 */
	 	private void setButtonStyle(Button btn)
	 	 {
	 		btn.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-border-color: black;");
	 	 }
	 
	    
	    

}
