package objects;

import java.util.List;
import java.util.UUID;

public class Path {
	
	private String parentName;
	
	private String parentPassword;

	private String pathId;
	
	private String pathName = "";
	
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentPassword() {
		return parentPassword;
	}

	public void setParentPassword(String parentPassword) {
		this.parentPassword = parentPassword;
	}

	private List<CartesianLocation> cartesianLocations;
	
	private List<LocationObject> listOfLocationObjects;

	public Path() {
		this.pathId = UUID.randomUUID().toString();
	}

	public List<CartesianLocation> getCartesianLocations() {
		return cartesianLocations;
	}

	public void setCartesianLocations(List<CartesianLocation> cartesianLocations) {
		this.cartesianLocations = cartesianLocations;
	}

	public String getPathId() {
		return pathId;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public List<LocationObject> getListOfLocationObjects() {
		return listOfLocationObjects;
	}

	public void setListOfLocationObjects(List<LocationObject> listOfLocationObjects) {
		this.listOfLocationObjects = listOfLocationObjects;
	}
	
	
}
