package objects;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.UUID;

public class ParentUser {

    private String name;

    private String password;

    private String email;

    private String id = UUID.randomUUID().toString();

    private ArrayList<String> childrenIDs;// = new ArrayList<String>();

    public void setName(String name){this.name = name;}

    public String getPassword(){return this.password;}

    public void setPassword(String password){this.password = password;}

    public String getEmail(){return this.email;}

    public void setEmail(String email){this.email = email;}

    public ArrayList<String> getChildrenIDs(){return this.childrenIDs;}

    public void setChildrenIDs(ArrayList<String> childrenIDs){
        this.childrenIDs = new ArrayList<String>();
        StringTokenizer stringTokenizer = new StringTokenizer(childrenIDs.get(0)," ,[]");
        while (stringTokenizer.hasMoreTokens()) this.childrenIDs.add(stringTokenizer.nextToken());

    }

    public String getName(){return this.name;}

    public String getID() {return this.id;}
}
