package objects;

import java.util.ArrayList;
import java.util.UUID;

public class ChildUser {

//	public ArrayList<Double> getLocationObject() {
//		return this.locationObject;
//	}
//
//	public void setLocationObject(ArrayList<Double> childrenIDs) {
//		this.locationObject = new ArrayList<Double>();
//		this.locationObject = childrenIDs;
//	}

	//private ArrayList<Double> locationObject;
	
	private LocationObject locationObject;

	public LocationObject getLocationObject() {
		return locationObject;
	}



	public void setLocationObject(LocationObject locationObject) {
		this.locationObject = locationObject;
	}

	private String parentUserName;

	private String parentPassword;

	private String name;

	private String email;

	private String ID;

	public ChildUser() {
		this.ID = UUID.randomUUID().toString();
		this.locationObject = new LocationObject();
		/*locationObject = new ArrayList<>();
		locationObject.add(0.0);
		locationObject.add(0.0);*/
	}

	
	
	public void setID(String ID) {
		this.ID = ID;
	}

	private ArrayList<String> parentIDs = new ArrayList<String>();

	public String getParentUserName() {
		return parentUserName;
	}

	public void setParentUserName(String parentUserName) {
		this.parentUserName = parentUserName;
	}

	public String getParentPassword() {
		return parentPassword;
	}

	public void setParentPassword(String parentPassword) {
		this.parentPassword = parentPassword;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<String> getParentIDs() {
		return this.parentIDs;
	}

	public void setParentIDs(ArrayList<String> parentIDs) {
		this.parentIDs = new ArrayList<String>();
		for (String id : parentIDs)
			this.parentIDs.add(id);
        }

    public String getID() {return ID;}
}
