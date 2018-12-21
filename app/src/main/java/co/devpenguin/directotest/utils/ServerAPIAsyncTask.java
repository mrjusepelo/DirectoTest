package co.devpenguin.directotest.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


import co.devpenguin.directotest.SessionActivity;
import co.devpenguin.directotest.adapters.AdapterProspectus;
import co.devpenguin.directotest.utils.APIRequest;
import co.devpenguin.directotest.utils.RestClient;
import static com.orm.SugarRecord.find;


public class ServerAPIAsyncTask extends AsyncTask<String, Integer, String[]> {
    String tag = "ServerAPIAsyncTask";
    public Context context;
//    public MainActivity main_activity;
    public SessionActivity session_activity;
    public AdapterProspectus adapterProspectus;
    public String control_response = null;

    static String from_calling = null;
    APIRequest thisAPIRequest;
    public RestClient client;
    ProgressDialog progressDialog;
    public HttpClient httpClient;
    public MultipartEntity entity;
    public HttpPost httpPost;
    public HttpContext localContext;

    private static final String TOKEN_NULL = "";
    SharedPreferences settings;
    public SharedPreferences.Editor editor;
    String fb_email="", fb_id="", session_email, session_username, session_user_id, session_token,
            session_json_user, session_pin, device_token, session_credits;
    boolean session_confirmed, session_receive_notifications;

    public interface AsynResponse {
        void processFinish(Boolean output);
    }
    AsynResponse asynResponse = null;

    public ServerAPIAsyncTask(APIRequest _request) {
        thisAPIRequest = _request;
    }
    public ServerAPIAsyncTask(APIRequest _request, AsynResponse asynResponse) {
        thisAPIRequest = _request;
        this.asynResponse = asynResponse;
    }

    /**
     * En este método se evalua el paso del evento y dependiendo de éste se envían los parametros necesarios.
     */

    public void init_client(){



        if (thisAPIRequest.step == APIRequest.LOGIN){
            context = session_activity;
            client = new RestClient("application/login.json", session_activity);
            progressDialog = MyCustomProgressDialog.ctor(session_activity);

        }
        if (thisAPIRequest.step == APIRequest.PROSPECTUS){
            context = adapterProspectus.context;
            client = new RestClient("sch/prospects.json", context);
            progressDialog = MyCustomProgressDialog.ctor(context);
        }




        if (context != null) {
            settings = context.getSharedPreferences("USER_DATA", 0);
            editor = settings.edit();
            session_token = settings.getString("TOKEN", TOKEN_NULL);
            session_credits = settings.getString("CREDITS", TOKEN_NULL);
            session_email = settings.getString("EMAIL", TOKEN_NULL);
            session_username = settings.getString("USERNAME", TOKEN_NULL);
            session_confirmed = settings.getBoolean("CONFIRMED", false);
            session_receive_notifications = settings.getBoolean("NOTIFICATIONS", false);
            session_user_id = settings.getString("USERID", TOKEN_NULL);
            session_json_user = settings.getString("JSON_USER", TOKEN_NULL);
        }


        if (progressDialog != null) {
            progressDialog.show();
        }

    }

    @Override
    protected String[] doInBackground(String... params) {

        if (client != null) {
            if (client.params != null) {
                Log.e("SAPIATask", "url_request==>" +client.params.toString());
            }
        }

        Log.i("ServerAPIAsyncTask", "response_doInbackground==>");

        try {
            String[] response = null;
            try {
                response = client.Execute(thisAPIRequest.method);
                Log.i("ServerAPIAsyncTask", "response_doInbackground==>" + Arrays.toString(response));
                Log.i("ServerAPIAsyncTask", "raw response: STATUS=" + response[0] + " - RESULT=" + response[1]);
                return response;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("errorApi", "errorInesperado1="+e.toString());
                control_response = "Revisa tu conexión a internet!";
            }
            catch (Exception e) {
                Log.e("errorApi", "errorInesperado2="+e.toString());
            }
            return null;

        }
        catch (Exception e) {

            Log.e("errorApi", "errorInesperado3="+e.toString());
            error("Ha ocurrido un error inesperado");
        }



        return null;
    }

    /**
     * En este método se recibe la respuesta del servidor y dependiendo del paso del evento es pasada como parámetro a una función que ejecuta las acciones necesarios
     * de la actividad principal.
     */


    @Override
    protected void onPostExecute(String[] raw_result) {
        int request = thisAPIRequest.step;
        if (request == APIRequest.LOGIN

                ){
            if (raw_result == null){
                Log.i(tag, "response-raw_result == null");
                if (!client.isOnline()) {
                    Toast.makeText(client.context, "Revisa tu conexión a internet", Toast.LENGTH_LONG).show();
                    error("Revisa tu conexión a internet");
//                    todo: callmethod to get all records local
                    getDataLocal(request);
                }else {
                    if (control_response == null) {
                        error("Ha ocurrido un error inesperado");
                    }else{
                        error(control_response);
                    }
                }
                return;
            }
            try {

                Object aObj = raw_result;
                if (aObj instanceof String[]) {
                    JSONObject result = new JSONObject(raw_result[1]);

                    if (!raw_result[0].equals("200")){
                        error(result.getString("message"));
                        return;
                    }
                    if (request == APIRequest.LOGIN){
                        session_activity.after_login(result);
                    }

                }else{
                    error("El servidor no se encuentra disponible");
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                error("Ha ocurrido un error inesperado");
            }
            catch (Exception e){
                error("Ha ocurrido un error inesperado");
            }
        }






        if (request == APIRequest.PROSPECTUS){
            Log.i("ServerAPIAsyncTask", "response==>" + Arrays.toString(raw_result));

            try {
                if (!client.isOnline()) {
                    Toast.makeText(client.context, "Revisa tu conexión a internet", Toast.LENGTH_LONG).show();
                    error("Revisa tu conexión a internet");
//                    todo: callmethod to get all records local
                    getDataLocal(request);
                }

                Object aObj = raw_result;
                if (aObj instanceof String[]) {
                    JSONArray result = null;
                    if (raw_result[0].equals("200")){
                        if (raw_result[1] != null) {
                            result = new JSONArray(raw_result[1]);
                            if (result != null) {
                                if (request == APIRequest.PROSPECTUS){
                                    adapterProspectus.setData(result);
                                }
                            }

                        }
                    }else{
                        error(raw_result[0]);
                        return;
                    }

                }else{
                    error("El servidor no se encuentra disponible.");
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
                Log.e(tag, "error_list_exeption1==>"+e.toString());
                error("!Revisa tu conexión de Internet!");
            }
            catch (Exception e){
                error("Ha ocurrido un error inesperado");
            }
        }

        cancel_loader();

    }

    public void cancel_loader(){
        if(progressDialog != null){
            if (progressDialog.isShowing()) {
                Log.i("ServerAPIAsyntask", "sendedCloseDialog");
                progressDialog.dismiss();
            }
        }
    }


    protected void getDataLocal(int request){

    }


    protected void error(final String msg){
        if(progressDialog != null){
            if (progressDialog.isShowing()) {
                Log.i(tag, "sendedCloseDialogOnError");
                progressDialog.dismiss();
            }
        }
        if (msg != null) {
            if (!msg.equals("411")) {
                if (this.client != null) {
                    if(this.client.context != null) {
                        ((Activity)this.client.context).runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(client.context, msg, Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
            }
        }



        if (msg != null && context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }



    }
}
