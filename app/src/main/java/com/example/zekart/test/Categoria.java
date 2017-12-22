/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zekart.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author nylaet
 */
public class Categoria {
    private String name;
    private Map<String,Channel> channels=new HashMap<String, Channel>();

    public Categoria(String nameAsUTF16) {
        this.name = convertUTF18ToString(nameAsUTF16);
    }
    
    private String convertUTF18ToString(String unicode){
        String result=StringEscapeUtils.unescapeJava(unicode);
        return result;
    } 
    
    public void parseChannels(JsonArray channels,String sessionId){
        this.channels.clear();
        for (JsonElement jsonElement : channels) {
            JsonObject channelJson=jsonElement.getAsJsonObject();
            Channel channel=new Channel(channelJson.get("id").getAsInt(), channelJson.get("logo").getAsString(), channelJson.get("name").getAsString(),sessionId);
            this.channels.put(channel.getName(), channel);
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }
}
