package com.pac.roman.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import objects.LoginObject;
import objects.LoginResultObject;
import providers.APIActions;
import providers.CommonAction;
import providers.ServerConnection;

/**
 * Created by Roman on 1/10/2015.
 */
public class LoginActivity extends Activity {

    Button loginBtn;
    Button registerBtn;
    EditText userName;
    EditText passWord;
    EditText serverHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        loginBtn = (Button) findViewById(R.id.loginLoginBtnID);
        registerBtn = (Button) findViewById(R.id.loginSignUpID);
        userName = (EditText) findViewById(R.id.loginUserNameID);
        passWord = (EditText) findViewById(R.id.loginPasswordID);
        serverHost = (EditText) findViewById(R.id.serverIPFieldID);
    }

    public void onLoginButtonClick(View view){
       // StaticDataContainer.SERVER_HOST = serverHost.getText().toString();
        LoginObject loginObject = new LoginObject();
        ObjectMapper objectMapper = new ObjectMapper();
        String msgFromServer = "";
        loginObject.setUserName(userName.getText().toString());
        loginObject.setPassword(passWord.getText().toString());

            try {
                ServerConnection serverConnection = ServerConnection.getServerConnection(loginObject);
                serverConnection.start();

                while (msgFromServer.equals("")) {
                    Thread.sleep(100);
                    msgFromServer = serverConnection.getMsgFromServer();
                }

                LoginResultObject loginResult = null;
                loginResult = objectMapper.readValue(msgFromServer, LoginResultObject.class);
                if (loginResult.getResult().toLowerCase().equals("ok")) {
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    StaticDataContainer.USER_NAME = userName.getText().toString();
                    StaticDataContainer.USER_PASS = passWord.getText().toString();
                    StaticDataContainer.CHILDREN_IDS = objectMapper.writeValueAsString(loginResult.getNamesAndIDs());

                    intent.putExtra("username", userName.getText().toString());
                    intent.putExtra("password", passWord.getText().toString());
                    intent.putExtra("childrenNamesAndIDs", objectMapper.writeValueAsString(loginResult.getNamesAndIDs()));
                    startActivity(intent);
                } else if (msgFromServer.toLowerCase().equals("not")) {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            } catch (Exception e) {
                System.out.println();
            }

    }

    public void onSignUpButtonClick(View view){
        StaticDataContainer.SERVER_HOST = serverHost.getText().toString();
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
