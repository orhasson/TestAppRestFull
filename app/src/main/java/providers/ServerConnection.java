package providers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pac.roman.myapplication.StaticDataContainer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Roman on 1/10/2015.
 */
public class ServerConnection extends Thread {

    public String msgFromServer = "";

    public String msgFromClient = "";

    public static String msgFromClientHTTP = "";

    public static String API = "";

    private static int PORT = 8080;

    public ServerConnection(String msgFromClient){
        this.msgFromClient = msgFromClient;
    }

    public String getMsgFromServer() {
        return msgFromServer;
    }

    public static <T> ServerConnection getServerConnection(T givenObject) throws IOException {
        String action = "";
        String msgFromClient = "";
        ObjectMapper objectMapper = new ObjectMapper();

        switch (givenObject.getClass().toString()){
            case "class objects.LoginObject" :
                API = APIActions.LOGIN_RESOURCE;
                msgFromClient = objectMapper.writeValueAsString(givenObject);
                ServerConnection.msgFromClientHTTP = msgFromClient;
                break;
            case "class objects.WaysManagerObject" :
                API = APIActions.Get_User_Path;
                msgFromClient = objectMapper.writeValueAsString(givenObject);
                ServerConnection.msgFromClientHTTP = msgFromClient;
                break;
            case "class objects.ParentUser" :
                API = APIActions.ADD_PARENT_RESOURCE;
                msgFromClient = objectMapper.writeValueAsString(givenObject);
                ServerConnection.msgFromClientHTTP = msgFromClient;
                break;
            case "class objects.ChildUser" :
                API = APIActions.ADD_CHILD_RESOURCE;
                msgFromClient = objectMapper.writeValueAsString(givenObject);
                ServerConnection.msgFromClientHTTP = msgFromClient;
                break;
            case "class java.lang.String" :
                API = APIActions.GET_CHILD_LOCATION_RESOURCE;
                ServerConnection.msgFromClientHTTP = (String) givenObject;
                break;
            case "class objects.Path" :
                API = APIActions.ADD_NEW_PATH;
                msgFromClient = objectMapper.writeValueAsString(givenObject);
                ServerConnection.msgFromClientHTTP = msgFromClient;
                break;
        }

        //String givenObjectAsString = (action.equals(APIActions.GET_CHILD_LOCATION))? (String) givenObject : objectMapper.writeValueAsString(givenObject);
      //  commonAction.setAction(action);
       // commonAction.setMsg(givenObjectAsString);
        //final String msgFromClient = objectMapper.writeValueAsString(commonAction);
       // final String msgFromClient = objectMapper.writeValueAsString(givenObject);
      //  ServerConnection.msgFromClientHTTP = msgFromClient;


        return new ServerConnection(msgFromClient);
    }


    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    StringEntity se = null;
                    try {
                        //Send Request:
                        se = new StringEntity(ServerConnection.msgFromClientHTTP);
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost request = new HttpPost(String.format(API, StaticDataContainer.SERVER_HOST,String.valueOf(PORT)));
                        request.setHeader("Accept", "application/json");
                        request.setHeader("Content-type", "application/json");
                        request.setEntity(se);
                        HttpResponse response = httpclient.execute(request);
                        HttpEntity entity = response.getEntity();

                        //Read Data:
                        msgFromServer = EntityUtils.toString(entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }).start();

    }

   /* @Override
    public void run() {
        new Thread(new Runnable() {
            Socket client = new Socket();
            PrintWriter printwriter;
            @Override
            public void run() {
                try{
                    //connect to server
                    //establish socket connection to server
                    //write to socket using ObjectOutputStream
                    ObjectOutputStream oos = null;
                    ObjectInputStream ois = null;
                    client = new Socket(HOST, PORT);
                    oos = new ObjectOutputStream(client.getOutputStream());
                    System.out.println("Sending request to Socket Server");
                    oos.writeObject(msgFromClient);

                    //read the server response message
                    ois = new ObjectInputStream(client.getInputStream());
                    msgFromServer = (String) ois.readObject();
                   *//* runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewFromClient.setText(message);
                        }
                    });*//*
                    //close resources
                    ois.close();
                    oos.close();
                    Thread.sleep(100);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }).start();

    }*/
}
