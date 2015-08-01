package objects;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Roman on 1/24/2015.
 */
public class LoginResultObject {

    private ArrayList<String> childrenNames;

    public Map<String, String> getNamesAndIDs() {
        return namesAndIDs;
    }

    public void setNamesAndIDs(Map<String, String> namesAndIDs) {
        this.namesAndIDs = namesAndIDs;
    }

    private Map<String,String> namesAndIDs;

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<String> getChildrenNames() {
        return childrenNames;
    }

    public void setChildrenNames(ArrayList<String> childrenNames) {
        this.childrenNames = childrenNames;
    }


}
