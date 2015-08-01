package com.pac.roman.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objects.ChildUser;
import providers.ServerConnection;

public class ChildrenActivity extends Activity {
    Spinner sp;
    List<String> li;
    Button addChildBtn;
    ObjectMapper mapper;
    Button deleteChildBtn;
    EditText childNameField;
    HashMap<String,String> childrenNamesAndIDs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.children_menu);
        childrenNamesAndIDs = new HashMap<>();
        mapper = new ObjectMapper();
        li = new ArrayList<>();
        sp=(Spinner) findViewById(R.id.spinner1);
        addChildBtn =(Button) findViewById(R.id.chldrenAcAddChildBtnID);
        childNameField=(EditText)findViewById(R.id.ChildremAcchildNameFieldID);
        deleteChildBtn =(Button) findViewById(R.id.ChildrenAcDeleteChildBtnID);
        TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>(){};
        try {
            childrenNamesAndIDs = mapper.readValue(StaticDataContainer.CHILDREN_IDS, typeRef);
        } catch (IOException e) {e.printStackTrace();}
        if(!childrenNamesAndIDs.isEmpty())
            for(Map.Entry<String, String> entry : childrenNamesAndIDs.entrySet()){li.add(entry.getKey());}
        add();
    }

    public void addNewChildUser(View view) throws IOException, InterruptedException {
        String msgFromServer = "";
        String parentPassword = StaticDataContainer.USER_PASS;
        String parentName = StaticDataContainer.USER_NAME;
        ChildUser child = new ChildUser();
        child.setName(childNameField.getText().toString());
        child.setEmail("");
        child.setParentUserName(parentName);
        child.setParentPassword(parentPassword);
        childrenNamesAndIDs.put(child.getName(),child.getID());

        ServerConnection serverConnection = ServerConnection.getServerConnection(child);
        serverConnection.start();
        while(msgFromServer.equals("")){
            Thread.sleep(StaticDataContainer.DEFAULT_TIMEOUT);
            msgFromServer = serverConnection.getMsgFromServer();
        }

        if(!msgFromServer.equals(null)) {
            if(!childNameField.getText().toString().isEmpty()) {
                li.add(childNameField.getText().toString());
                childNameField.setText(null);
                add();
                sp.getSelectedItem();
            }
        }
        //todo : add else statement!!!!!
    }

    private void add() {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,li);
        sp.setAdapter(adp);
    }

    private void remove() {
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,li);
        sp.setAdapter(adp);
    }

    public void enterSelection(View view){
        Intent intent = new Intent(ChildrenActivity.this, ChildOnTheMapActivity.class);
        intent.putExtra("childID",childrenNamesAndIDs.get(sp.getSelectedItem().toString()));
        startActivity(intent);
    }

    public void showChildIP(View view){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(StaticDataContainer.CHILD_IP_TITLE);
        alertDialog.setMessage(childrenNamesAndIDs.get(sp.getSelectedItem().toString()));
        alertDialog.setButton(StaticDataContainer.CHILD_IP_BUTTON_TEXT, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which){}});
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}