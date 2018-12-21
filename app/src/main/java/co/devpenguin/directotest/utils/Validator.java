package co.devpenguin.directotest.utils;

import android.icu.util.Calendar;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

//import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.http.entity.mime.content.StringBody;

import java.io.File;

/**
 * Created by juan on 6/12/17.
 */

public class Validator {
    static String tag = "Validator";

    public static boolean validateFile(File file){
        boolean valid = false;
        if (file == null) {
        }else if(file.exists() && !file.isDirectory()) {
            valid = true;
        }
        return valid;
    }

    public static boolean validateEditText(EditText edit_text){
        boolean valid=true;

        String  text = edit_text.getText().toString().trim();
        if(text.equals("") || text.length()==0 || text == null || text.equals(null) || text.equals("null") || text.isEmpty()){

            valid = false;
        }else{

        }
        return valid;
    }

    public static boolean validateAutoComplete(AutoCompleteTextView auto_complete){
        boolean valid=true;
        String  text = auto_complete.getText().toString().trim();
        if(text.equals("") || text.length()==0 || text == null || text.equals(null) || text.equals("null") || text.isEmpty()){
            Log.i(tag, "isEmpty=");
            valid = false;
        }else{
            Log.i(tag, "isEmptyFalse=" + text);
        }
        return valid;
    }

    public static boolean validateRadioGroup(RadioGroup rg){
        boolean valid = false;
        if (rg.getCheckedRadioButtonId() == -1) {
        }else{
            valid = true;
        }
        return valid;
    }

    public static boolean validateSpinner(Spinner sp, int minPosition){
        boolean valid = false;
        if (sp.getSelectedItemPosition() < minPosition) {
        }else{
            valid = true;
        }
        return valid;
    }
//    public static boolean validateSearchableSpinner(SearchableSpinner sp, int minPosition){
//        boolean valid = false;
//        if (sp.getSelectedItemPosition() < minPosition) {
//        }else{
//            valid = true;
//        }
//        return valid;
//    }
    public static boolean validateDatePicker(DatePicker dp){
        boolean valid = false;
        if (dp.getYear() == Calendar.YEAR) {
        }else{
            valid = true;
        }
        return valid;
    }

    public static boolean validateCheckbox(CheckBox cb) {
        boolean valid = false;
        if (cb.isChecked() == true) {
            valid = true;
        }
        return valid;
    }
}
