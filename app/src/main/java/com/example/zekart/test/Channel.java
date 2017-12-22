/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zekart.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author nylaet
 */
public class Channel {

    private final String channelIp = "http://mw.wildpark.net:8888/webtv/ws/get_channel_ip.php";
    private int id;
    private String logo;
    private String name;
    private String logoUrl;
    private String streamUrl;
    private String sessionId;

    public Channel(int id, String logo, String nameUTF16,String sessionId) {
        this.id = id;
        this.logo = logo;
        this.name = convertUTF18ToString(nameUTF16);
        logoUrl = "http://mw.wildpark.net:8888/webtv/images/channels/" + logo;
        this.sessionId=sessionId;
    }

    private String convertUTF18ToString(String unicode) {
        String result = StringEscapeUtils.unescapeJava(unicode);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getStreamUrl() {
        try {
            String param = "?id=" + id;            
            URL obj = new URL(channelIp+param);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Cookie", "PHPSESSID="+sessionId);
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) con.getContent()));
            System.out.println(root.toString());
        } catch (MalformedURLException ex) {
            System.out.println(ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return streamUrl;
    }

}
