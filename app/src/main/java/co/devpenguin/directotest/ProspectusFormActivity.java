package co.devpenguin.directotest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import co.devpenguin.directotest.objects.Prospectus;
import co.devpenguin.directotest.utils.Validator;

public class ProspectusFormActivity extends Activity {
    String tag = "ProspectusFormActivity";
    Context context;
    String session_json_user, fb_email="", fb_id="", fb_image="", session_email, session_username, session_user_id, session_token, session_img_profile;
    boolean session_confirmed, session_receive_notifications;
    private static final String TOKEN_NULL = "";
    SharedPreferences settings;
    public SharedPreferences.Editor editor;
    Prospectus prospectus;
    Button btn_save;
    boolean flag_editin = false;
    TextView tv_validations;
    Spinner sp_status;
    MaterialEditText ed_name, ed_surname, ed_schProspectIdentification, ed_telephone, ed_address,
                     ed_zoneCode, ed_neighborhoodCode, ed_cityCode, ed_sectionCode, ed_roleId, ed_appointableId,
                     ed_rejectedObservation, ed_observation, ed_campaignCode, ed_userId;
    CheckBox cb_disable, cb_visited, cb_callcenter, cb_acceptSearch;
    ScrollView sv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propectus_form);
        context = this;

        settings = getSharedPreferences("USER_DATA", 0);
        editor = settings.edit();

        session_token = settings.getString("TOKEN", TOKEN_NULL);
        session_email = settings.getString("EMAIL", TOKEN_NULL);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.get("prospectus") != null) {
                prospectus = (Prospectus) extras.getSerializable("prospectus");
                if (prospectus != null) {
                    flag_editin = true;
                    if (flag_editin) {
                        prospectus = Prospectus.findById(Prospectus.class, prospectus.id);
                    } else {
                        prospectus = new Prospectus();
                    }
                }
            }

        }

        initUI();




    }

    private void initUI() {
        sv_content = (ScrollView) findViewById(R.id.sv_content);
        ed_name = (MaterialEditText) findViewById(R.id.ed_name);
        ed_surname= (MaterialEditText) findViewById(R.id.ed_surname);
        ed_schProspectIdentification= (MaterialEditText) findViewById(R.id.ed_schProspectIdentification);
        sp_status = (Spinner) findViewById(R.id.sp_status);
        tv_validations = (TextView) findViewById(R.id.tv_validations);
        ed_telephone= (MaterialEditText) findViewById(R.id.ed_telephone);
        ed_address= (MaterialEditText) findViewById(R.id.ed_address);
        ed_zoneCode= (MaterialEditText) findViewById(R.id.ed_zoneCode);
        ed_neighborhoodCode= (MaterialEditText) findViewById(R.id.ed_neighborhoodCode);
        ed_cityCode= (MaterialEditText) findViewById(R.id.ed_cityCode);
        ed_sectionCode= (MaterialEditText) findViewById(R.id.ed_sectionCode);
        ed_roleId= (MaterialEditText) findViewById(R.id.ed_roleId);
        ed_appointableId= (MaterialEditText) findViewById(R.id.ed_appointableId);
        ed_rejectedObservation= (MaterialEditText) findViewById(R.id.ed_rejectedObservation);
        ed_observation= (MaterialEditText) findViewById(R.id.ed_observation);
        ed_campaignCode= (MaterialEditText) findViewById(R.id.ed_campaignCode);
        ed_userId= (MaterialEditText) findViewById(R.id.ed_userId);
        cb_disable= (CheckBox) findViewById(R.id.cb_disable);
        cb_visited= (CheckBox) findViewById(R.id.cb_visited);
        cb_callcenter= (CheckBox) findViewById(R.id.cb_callcenter);
        cb_acceptSearch= (CheckBox) findViewById(R.id.cb_acceptSearch);
        btn_save = (Button) findViewById(R.id.btn_save);


        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 4){
                    ed_rejectedObservation.setVisibility(View.VISIBLE);
                }else{
                    ed_rejectedObservation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {

                }
            }
        });

        fillData();



    }


    private void fillData(){
        if (prospectus != null){
            if(prospectus.getName() != null) ed_name.setText(prospectus.getName());
            if(prospectus.getSurname() != null) ed_surname.setText(prospectus.getSurname());
            if(prospectus.getSchProspectIdentification() != null) ed_schProspectIdentification.setText(prospectus.getSchProspectIdentification());
            if(prospectus.getStatusCd() != 99) sp_status.setSelection(prospectus.getStatusCd()+1);

            if(prospectus.telephone != null) ed_telephone.setText(prospectus.telephone);
            if(prospectus.address != null) ed_address.setText(prospectus.address);
            if(prospectus.zoneCode != null) ed_zoneCode.setText(prospectus.zoneCode);
            if(prospectus.neighborhoodCode != null) ed_neighborhoodCode.setText(prospectus.neighborhoodCode);
            if(prospectus.cityCode != null) ed_cityCode.setText(prospectus.cityCode);
            if(prospectus.sectionCode != null) ed_sectionCode.setText(prospectus.sectionCode);
            if(prospectus.roleId > 0) {
                ed_roleId.setText(String.valueOf(prospectus.roleId));
            }
            if(prospectus.appointableId != null) ed_appointableId.setText(prospectus.appointableId);
            if(prospectus.rejectedObservation != null) ed_rejectedObservation.setText(prospectus.rejectedObservation);
            if(prospectus.observation != null) ed_observation.setText(prospectus.observation);
            if(prospectus.campaignCode != null) ed_campaignCode.setText(prospectus.campaignCode);
            if(prospectus.userId != null) ed_userId.setText(prospectus.userId);
            cb_disable.setChecked(prospectus.disable);
            cb_visited.setChecked(prospectus.visited);
            cb_callcenter.setChecked(prospectus.callcenter);
            cb_acceptSearch.setChecked(prospectus.acceptSearch);

        }
    }

    private boolean validateInputs(){
        tv_validations.setVisibility(View.GONE);
        tv_validations.setText("Revisa los siguientes campos : \n ");
//        String time_ago = null;
        boolean valid = true;

        if (!Validator.validateEditText(ed_name)) {
            valid = false;
            tv_validations.append("Nombre no puede estar en blanco \n ");
        }else{
            prospectus.name = (ed_name.getText().toString());
        }
        if (!Validator.validateEditText(ed_surname)) {
            valid = false;
            tv_validations.append("Apellido no puede estar en blanco \n ");
        }else{
            prospectus.surname = (ed_surname.getText().toString());
        }
        if (!Validator.validateSpinner(sp_status, 1)) {
            valid = false;
            tv_validations.append("Estado no puede estar en blanco \n ");
        }else{
            prospectus.statusCd = sp_status.getSelectedItemPosition()-1;
            //NOTA: Si el estado es de rechazo, se valida la descripción de este
            if (prospectus.statusCd == 3 && !Validator.validateEditText(ed_rejectedObservation)) {
                valid = false;
                tv_validations.append("Observación de rechazo no puede estar en blanco \n ");
            }else{
                prospectus.rejectedObservation = ed_rejectedObservation.getText().toString();
            }
        }

        if (!Validator.validateEditText(ed_telephone)) {
            valid = false;
            tv_validations.append("Teléfono no puede estar en blanco \n ");
        }else{
            prospectus.telephone = ed_telephone.getText().toString();
        }

        if (!Validator.validateEditText(ed_address)) {
            valid = false;
            tv_validations.append("Dirección no puede estar en blanco \n ");
        }else{
            prospectus.address = ed_address.getText().toString();
        }

        if (!Validator.validateEditText(ed_zoneCode)) {
            valid = false;
            tv_validations.append("Código de Zona no puede estar en blanco \n ");
        }else{
            prospectus.zoneCode = ed_zoneCode.getText().toString();
        }

        if (!Validator.validateEditText(ed_neighborhoodCode)) {
            valid = false;
            tv_validations.append("Código de Barrio no puede estar en blanco \n ");
        }else{
            prospectus.neighborhoodCode = ed_neighborhoodCode.getText().toString();
        }

        if (!Validator.validateEditText(ed_cityCode)) {
            valid = false;
            tv_validations.append("Código de Ciudad no puede estar en blanco \n ");
        }else{
            prospectus.cityCode = ed_cityCode.getText().toString();
        }

        if (!Validator.validateEditText(ed_sectionCode)) {
            valid = false;
            tv_validations.append("Código de Sección no puede estar en blanco \n ");
        }else{
            prospectus.sectionCode = ed_sectionCode.getText().toString();
        }

        if (!Validator.validateEditText(ed_roleId)) {
            valid = false;
            tv_validations.append("Rol no puede estar en blanco \n ");
        }else{
            prospectus.roleId = Integer.parseInt(ed_roleId.getText().toString());
        }
        if (!Validator.validateEditText(ed_appointableId)) {
            valid = false;
            tv_validations.append("Id Asignado no puede estar en blanco \n ");
        }else{
            prospectus.appointableId = ed_appointableId.getText().toString();
        }

        if (!Validator.validateEditText(ed_observation)) {
            valid = false;
            tv_validations.append("Observación no puede estar en blanco \n ");
        }else{
            prospectus.observation = ed_observation.getText().toString();
        }

        if (!Validator.validateEditText(ed_campaignCode)) {
            valid = false;
            tv_validations.append("Código de Campaña no puede estar en blanco \n ");
        }else{
            prospectus.campaignCode = ed_campaignCode.getText().toString();
        }
        if (!Validator.validateEditText(ed_userId)) {
            valid = false;
            tv_validations.append("Código de Usuario no puede estar en blanco \n ");
        }else{
            prospectus.userId = ed_userId.getText().toString();
        }

        if(cb_disable.isChecked()) prospectus.disable = true;
        if(cb_visited.isChecked()) prospectus.visited = true;
        if(cb_callcenter.isChecked()) prospectus.callcenter = true;
        if(cb_acceptSearch.isChecked()) prospectus.acceptSearch = true;

        if (valid == true) {
            prospectus.edited = true;
            Date date= new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            prospectus.updatedAt = String.valueOf(ts);

            prospectus.save();
            Intent intent = new Intent(context, MenuActivity.class);
            intent.putExtra("option", 0);
            startActivity(intent);
            //((MenuActivity)context).selectDrawerItem(((MenuActivity)context).navigationView.getMenu().findItem(R.id.nav_dashboard));

        }else{
            tv_validations.setVisibility(View.VISIBLE);
            sv_content.post(new Runnable() {
                public void run() {
                    sv_content.fullScroll(sv_content.FOCUS_UP);
                }
            });
        }

        return valid;
    }

}
