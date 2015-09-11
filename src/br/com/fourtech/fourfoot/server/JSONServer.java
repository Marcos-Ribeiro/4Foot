package br.com.fourtech.fourfoot.server;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class JSONServer {

    public JSONObject GetJSONObject(String url, String method,String type,String paramJson)
    {
        try {
            JSONObject json = ParserObject(ReaderBuffer(url, method, paramJson));
            return json;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray GetJSONArray(String url, String method, String type) {
        return ParserArray(ReaderBuffer(url, method, null), type);
    }

    private InputStream getStream(String url, String method, String json){
        switch (method) {
            case "GET":
                return httpGET(url);
            case "POST":
                return httpPOST(url,json);
            case "PUT":
                return httpPUT(url, json);
            case "DELETE":
                return httpDELETE(url);
            default:
                return null;
        }
    }

    private InputStream httpPUT(String url, String json){

        InputStream is;
        try{
            StringEntity entity = new StringEntity(json);
            //entity.setContentEncoding(HTTP.ISO_8859_1);
            entity.setContentEncoding(HTTP.UTF_8);
            entity.setContentType("application/json");

            HttpPut httpPut = new HttpPut(url);
            httpPut.setEntity(entity);
            httpPut.setHeader("Content-Type", "application/json");
            httpPut.setHeader("Accept", "application/json");

            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpResponse httpResponse = httpClient.execute(httpPut);

            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            return is;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private InputStream httpPOST(String url, String json){

        InputStream is;
        try{
            /*
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            //httpPost.setEntity(new UrlEncodedFormEntity(json,"UTF-8"));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            return is;*/

            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json);
            entity.setContentEncoding(HTTP.UTF_8);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");

            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            return is;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private InputStream httpDELETE(String url){

        InputStream is;
        try{

            HttpDelete httpDelete = new HttpDelete(url);

            httpDelete.setHeader("Content-Type", "application/json");
            httpDelete.setHeader("Accept", "application/json");

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpDelete);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            return is;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private InputStream httpGET(String url){

        InputStream is;
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            return is;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String ReaderBuffer(String url, String method, String paramJson){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getStream(url, method, paramJson), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return e.toString();
        }
    }

    private JSONArray ParserArray(String json, String type){
        try {
            JSONObject jObj = new JSONObject(json);
            JSONArray jArr = new JSONArray(jObj.getString(type));

            return jArr;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    private JSONObject ParserObject(String json){
        try {
            return new JSONObject(json);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
