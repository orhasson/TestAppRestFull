package providers;

/**
 * Created by Roman on 1/10/2015.
 */
public class GlobalParameters {

    private static GlobalParameters instance = null;

    private String ParentName;

    private String ParentPassword;

    private GlobalParameters(){}

    public static synchronized GlobalParameters getInstance() {
        if (instance == null)
            instance = new GlobalParameters();
        return instance;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

    public String getParentPassword() {
        return ParentPassword;
    }

    public void setParentPassword(String parentPassword) {
        ParentPassword = parentPassword;
    }
}
