package co.devpenguin.directotest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import co.devpenguin.directotest.R;

public class RestClient {

	public enum RequestMethod {
		GET, POST, PUT, DELETE
	}
	
	public ArrayList <NameValuePair> params;
	private ArrayList <NameValuePair> headers;
	
	private String url;
	 
	public static int responseCode;
	private String message;
	
	public String response;
	
	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Context context;
	
	public RequestMethod method;

	public RequestMethod getMethod() {
		return method;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}
	

    public String getErrorMessage() {
        return message;
    }
    
    public RestClient(String url, Context c)
    {
    	setContext(c);
    	String host_name;
    	String api;
    	
    	// Revisamos si estamos en development o release
    	//if(BuildConfig.DEBUG) {
        Log.e("AsyncTaskLoadWinners", "restClientcontext="+c.toString());
    		host_name = c.getString(R.string.local);
        Log.e("AsyncTaskLoadWinners", "restClienthostname="+host_name);
        api = c.getString(R.string.api);
        Log.e("AsyncTaskLoadWinners", "restClientApi="+api);
    	/*} else {
    		host_name = c.getString(R.string.external);
    	}*/
    	
        this.url = host_name + api + url;
        Log.e("URL_SERVER===>", this.url);
        params = new ArrayList<NameValuePair>();
        headers = new ArrayList<NameValuePair>();
    }
    
    public void AddParam(String name, String value)
    {
        params.add(new BasicNameValuePair(name, value));
    }

    public void AddHeader(String name, String value)
    {
        headers.add(new BasicNameValuePair(name, value));
    }
    
    public boolean isOnline() {
    	ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    	
    	if (!(activeNetworkInfo != null && activeNetworkInfo.isConnected())){
    		
    		Log.e("Rest Client", "No hay conexion a Internet.");
    		
        	return false;
    	}
    	else{
    		return true;
    	} //return true;
    }
    
    public String[] Execute(RequestMethod method) throws Exception {
    	
    	SharedPreferences settings = getContext().getSharedPreferences("USER_DATA", 0);
        String token = settings.getString("TOKEN", "");
        String email = settings.getString("EMAIL", "");
        
        if (!token.equals("")) {
        	this.AddParam("token", token);
//        	this.AddParam("admin_user_email", email);
            this.AddHeader("token", token);

        }
  
        switch(method) {
        case GET:
        {
            //add parameters
            String combinedParams = "";
            if(!params.isEmpty()){
                combinedParams += "?";
                for(NameValuePair p : params)
                {
                    String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),"UTF-8");
                    if(combinedParams.length() > 1)
                    {
                        combinedParams  +=  "&" + paramString;
                    }
                    else
                    {
                        combinedParams += paramString;
                    }
                }
            }

            HttpGet request = new HttpGet(url + combinedParams);

            //add headers
            for(NameValuePair h : headers)
            {
                request.addHeader(h.getName(), h.getValue());
            }

            return executeRequest(request, url);
        }
        case POST:
        {
            HttpPost request = new HttpPost(url);

            //add headers
            for(NameValuePair h : headers)
            {
                request.addHeader(h.getName(), h.getValue());
            }
            
            this.AddParam("_method", "post");

            if(!params.isEmpty()){
                request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }

            return executeRequest(request, url);
        }
        case PUT:
        {
            HttpPost request = new HttpPost(url);

            //add headers
            for(NameValuePair h : headers)
            {
                request.addHeader(h.getName(), h.getValue());
            }

            this.AddParam("_method", "put");
            
            if(!params.isEmpty()){
                request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }

            return executeRequest(request, url);
        }
        case DELETE:
        {
            HttpPost request = new HttpPost(url);

            //add headers
            for(NameValuePair h : headers)
            {
                request.addHeader(h.getName(), h.getValue());
            }

            this.AddParam("_method", "delete");
            
            if(!params.isEmpty()){
                request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }

            return executeRequest(request, url);
        }
    }
    
    return null;        
    	
    }
    
    //new mwthod
    private String[] executeRequest(HttpUriRequest request, String url) throws IOException
    {
    	Log.i("RestClient", "Iniciando peticion - : " + url);
    	
        HttpClient client = new DefaultHttpClient();
        HttpParams httpParameters = client.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
        HttpConnectionParams.setSoTimeout(httpParameters, 15000);
        HttpConnectionParams.setTcpNoDelay(httpParameters, true);

        HttpResponse httpResponse;

        httpResponse = client.execute(request);
        responseCode = httpResponse.getStatusLine().getStatusCode();
        message = httpResponse.getStatusLine().getReasonPhrase();

        HttpEntity entity = httpResponse.getEntity();

        Log.i("RestClient", "Respuesta del servidor: " + responseCode);
        Log.i("RestClient","=======Activity"+context.getClass().getSimpleName());
        
        if (entity != null) {

            InputStream instream = entity.getContent();
            response = convertStreamToString(instream);

            // Closing the input stream will trigger connection release
            instream.close();

            String[] array = new String[2];

            array[0] = responseCode+"";
            array[1] = response;

            return array;
        }

        return null;
    }


    
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    
    
	
}
