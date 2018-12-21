package co.devpenguin.directotest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;

import co.devpenguin.directotest.utils.APIRequest;
import co.devpenguin.directotest.utils.RestClient;
import co.devpenguin.directotest.utils.ServerAPIAsyncTask;
import co.devpenguin.directotest.utils.Validator;

public class SessionActivity extends AppCompatActivity {

    Button btn_signin, btn_signup;
    EditText ed_password;
    private String  device_emei;

    MenuActivity mainActivity;
    TextView tv_forget_password;
    Context context;
    JSONObject result;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

    private TelephonyManager mTelephonyManager;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    private TextView tv_loading_tv2;

    String tag="SessionActivity";
    Typeface rblack_italy, rregular, rmedium_italy, rlight_italy;
    ImageView iv_logo_creanovo;

    EditText ed_email, ed_pass;
    Button btn_send;
    private String device_token;
    String session_json_user, fb_email="", fb_id="", fb_image="", session_email, session_username, session_user_id, session_token, session_img_profile;
    boolean session_confirmed, session_receive_notifications;
    private static final String TOKEN_NULL = "";
    SharedPreferences settings;
    public SharedPreferences.Editor editor;
    SessionActivity sessionActivity;
    ActionBar actionBar;
    View viewActionBar;
    public View view_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(this);
        context = this;
        setContentView(R.layout.activity_session);
        view_login = findViewById(android.R.id.content);
        settings = getSharedPreferences("USER_DATA", 0);
        editor = settings.edit();


        session_token = settings.getString("TOKEN", TOKEN_NULL);
        session_email = settings.getString("EMAIL", TOKEN_NULL);
        session_username = settings.getString("USERNAME", TOKEN_NULL);
        session_receive_notifications = settings.getBoolean("NOTIFICATIONS", false);
        session_user_id = settings.getString("USERID", TOKEN_NULL);
        session_json_user = settings.getString("JSON_USER", TOKEN_NULL);

        if (!session_token.equals("")) {
            Log.v("MainActivity", "YA EXISTE LA SESSION"+session_token);
            Log.v("MainActivity", "YA EXISTE LA SESSION"+session_email);
            Log.i("SessionInfoUser", "USER===>"+session_json_user);
            Intent intent = new Intent(context, MenuActivity.class);
            Bundle bundle = new Bundle();
//            intent.putExtra("user", user);
//            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

        initializeInputs();
    }

    private void initializeInputs() {
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_pass = (EditText) findViewById(R.id.ed_pass);
        btn_send = (Button) findViewById(R.id.btn_login);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click", "clickSend");
                if(validateInputs()){
                    String username=null, name=null, email=null, password=null;

                    email = ed_email.getText().toString();
                    password = ed_pass.getText().toString();

                    //ASYNC TASK
                    APIRequest request = new APIRequest(APIRequest.LOGIN, RestClient.RequestMethod.GET);
                    ServerAPIAsyncTask api_server = new ServerAPIAsyncTask(request);
                    api_server.session_activity = SessionActivity.this;

                    api_server.init_client();
                    api_server.client.AddParam("email", email);
                    api_server.client.AddParam("password", password);
//                    api_server.client.AddParam("device_token", device_token);
//                    api_server.client.AddParam("locale", "es");
                    api_server.execute();
                }else{
                    Toast.makeText(context, "Revisa los campos del formulario", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public boolean validateInputs(){
        boolean valid = true;
        if(!Validator.validateEditText(ed_email)){
            valid = false;
        }if(!Validator.validateEditText(ed_pass)){
            valid = false;
        }

        return  valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        Log.e("result","data="+extras.toString());

    }

    public void after_login(JSONObject result) {
        Log.i(tag, "afterLogin==" + result.toString());

        try {
            Log.i(tag, "token===>"+result.getString("authToken"));
            editor.putString("EMAIL", result.getString("email"));
            editor.putString("TOKEN", result.getString("authToken"));
            editor.putString("ZONE", result.getString("zone"));
            editor.commit();

            Toast.makeText(SessionActivity.this, "Iniciaste sesión correctamente", Toast.LENGTH_LONG).show();


//            //ASYNC TASK
//            APIRequest request = new APIRequest(APIRequest.LOGIN, RestClient.RequestMethod.GET);
//            ServerAPIAsyncTask api_server = new ServerAPIAsyncTask(request);
//            api_server.session_activity = SessionActivity.this;
//            api_server.init_client();
////            api_server.client.AddParam("email", email);
////            api_server.client.AddParam("password", password);
//            api_server.execute();

            Intent intent = new Intent(context, MenuActivity.class);
            Bundle bundle = new Bundle();
//            intent.putExtra("user", user);
//            intent.putExtras(bundle);
            startActivity(intent);
            finish();

        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void after_login_error(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }






}
