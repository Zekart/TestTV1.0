package com.example.zekart.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Menu_ch {

    private static Menu_ch instance = null;
    private Map<String, Categoria> categories = new HashMap<>();
    private final String mainUrl = "http://mw.wildpark.net:8888/webtv/";
    private final String authUrl = "ws/auth_stb.php";
    private final String iptvMenuUrl = "ws/get_IptvMenu.php";
    private boolean haveException;
    private Exception ex = null;
    private boolean haveConnection;
    private String sessionId="";

    private Menu_ch() {
        haveConnection = false;
        haveException = false;
    }

    public static synchronized Menu_ch getInstance() {
        if (instance == null) {
            instance = new Menu_ch();
        }
        instance.updateMenu();
        return instance;
    }

    public void updateMenu() {
        if (isAuthenticated()) {
            try {
                URL iptvMenuURL = new URL(mainUrl + iptvMenuUrl);
                HttpURLConnection request = (HttpURLConnection) iptvMenuURL.openConnection();
                request.setRequestProperty("Cookie", "PHPSESSID="+sessionId);                
//                request.setRequestMethod("GET");
//                request.setRequestProperty("User-Agent", "Mozilla/5.0");
//                request.setDoOutput(true);
//                DataOutputStream dos=new DataOutputStream(request.getOutputStream());
//                dos.writeBytes("{}");
//                dos.flush();
//                dos.close();
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                System.out.println(root.toString());
                JsonObject successObj = root.getAsJsonObject();
                if ((successObj.get("success").getAsBoolean())) {
                    parseMenu(successObj.get("cats"),sessionId);
                }
            } catch (MalformedURLException e) {
                setExeption(e);
            } catch (IOException e) {
                setExeption(e);
            }
        }
    }

    private boolean isAuthenticated() {
        if (!haveConnection) {
            try {
                URL authURL = new URL(mainUrl + authUrl);
                HttpURLConnection register = (HttpURLConnection) authURL.openConnection();
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) register.getContent()));
                JsonObject rootobj = root.getAsJsonObject();
                haveConnection = rootobj.get("isAuthenticated").getAsBoolean();
                sessionId=rootobj.get("PHPSESSID").getAsString();
                System.out.println(sessionId);
            } catch (MalformedURLException e) {
                setExeption(e);
            } catch (IOException e) {
                setExeption(e);
            }
        }
        return haveConnection;
    }

    private void setExeption(Exception e) {
        haveException = true;
        ex = e;
        System.out.println(e.getLocalizedMessage());
    }

    private void parseMenu(JsonElement cats,String sessionId) {
        categories.clear();
        JsonArray catsArray=cats.getAsJsonArray();
        for (JsonElement cat : catsArray) {
            JsonObject surrentCategoria=cat.getAsJsonObject();
            Categoria categoria=new Categoria(surrentCategoria.get("name").getAsString());
            categoria.parseChannels(surrentCategoria.get("progs").getAsJsonArray(),sessionId);
            categories.put(categoria.getName(), categoria);
        }
    }

    public Map<String, Categoria> getCategories() {
        return categories;
    }

    public boolean isHaveException() {
        return haveException;
    }

    public Exception getEx() {
        return ex;
    }

    public boolean isHaveConnection() {
        return haveConnection;
    }
    
    
}
