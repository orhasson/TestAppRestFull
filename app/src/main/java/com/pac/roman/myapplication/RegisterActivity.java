package com.pac.roman.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import objects.ParentUser;
import providers.APIActions;
import providers.CommonAction;
import providers.ServerConnection;

public class RegisterActivity extends Activity{
    Button register;

    EditText userName;

    EditText password;

    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        register = (Button) findViewById(R.id.registerSingUPID);
        userName = (EditText) findViewById(R.id.registerUsernameID);
        password = (EditText) findViewById(R.id.registerPasswordID);
        email = (EditText) findViewById(R.id.registerEmailID);
    }

    public void onRegister(View view) throws IOException, InterruptedException {
        String msgFromServer = "";
        ParentUser parentUser = new ParentUser();
        ArrayList<String> childrenIDs = new ArrayList<String>();
        childrenIDs.add(StaticDataContainer.DEFAULT_ID);

        parentUser.setName(userName.getText().toString());
        parentUser.setPassword(password.getText().toString());
        parentUser.setEmail(email.getText().toString());
        parentUser.setChildrenIDs(childrenIDs);

        //Server connection:
        ServerConnection serverConnection = ServerConnection.getServerConnection(parentUser);
        serverConnection.start();
        while(msgFromServer.equals("")){
            Thread.sleep(StaticDataContainer.DEFAULT_TIMEOUT);
            msgFromServer = serverConnection.getMsgFromServer();
        }
        if(msgFromServer.toLowerCase().equals("ok")) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.putExtra("username", userName.getText().toString());
            intent.putExtra("password", password.getText().toString());
            startActivity(intent);
        }
        //todo : add else statement!!!!!
    }
}

