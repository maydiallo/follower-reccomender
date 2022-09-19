package system.functions;
/**
 * @author Maimouna Diallo
 * 219010356
 * miniproject
 */
public class InstaProfile implements Comparable<InstaProfile> {
	private  String userName;
	private  int userFollowing; 
	private  int userFollowers; 
	private boolean Visibility;	


	public InstaProfile() {  
		
    }
	
	
	/**
	 * @param name
	 * @param following
	 * @param followers
	 * @param visibility
	 * constructor
	 */
	public InstaProfile(String name, int following, int followers, boolean visibility) {
        this.userName= name;
        this.userFollowing= following;
        this.userFollowers= followers;
        this.Visibility= visibility;
    }
	
	/**
	 * @return username
	 * gets the usernames
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 * sets the username 
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return userFollowing 
	 * gets who the user is following
	 */
	public int getUserFollowing() {
		return userFollowing;
	}

	/**
	 * @param userFollowing
	 */
	public void setUserFollowing(int userFollowing) {
		this.userFollowing = userFollowing;
	}

	/**
	 * @return userFollowers
	 * gets who the is following the users 
	 */
	public int getUserFollowers() {
		return userFollowers;
	}

	/**
	 * @param userFollowers
	 * sets the user is followers
	 */
	public void setUserFollowers(int userFollowers) {
		this.userFollowers = userFollowers;
	}
	
	/**
	 * @return
	 * gets the visibility
	 */
	public boolean getVisibility() {
		return Visibility;
	}


	/**
	 * @param visibility
	 * sets the visibility
	 */
	public void setVisibility(boolean visibility) {
		Visibility = visibility;
	}

	
	/**
	 * @return Visible
	 * based on @Visibility determines user is account type
	 */
	public String isVisible() {
		String Visible;
		String tempPrivate="Private";
		String tempPublic="Public";
		if(Visibility==true)
		{
			Visible=tempPublic;
		}else
		{
			Visible=tempPrivate;
		}
		return Visible;
	}

	
	@Override
	public int compareTo(InstaProfile o) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	

}
