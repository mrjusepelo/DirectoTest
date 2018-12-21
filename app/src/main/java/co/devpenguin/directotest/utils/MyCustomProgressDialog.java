package co.devpenguin.directotest.utils;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.devpenguin.directotest.R;


public class MyCustomProgressDialog extends ProgressDialog {
  private AnimationDrawable animation;
  public int show_percent;
  ProgressBar pb_dialog;

  public static ProgressDialog ctor(Context context) {
    MyCustomProgressDialog dialog = new MyCustomProgressDialog(context);
    dialog.setIndeterminate(true);
    dialog.setCancelable(false);
    dialog.show_percent = View.GONE;
    return dialog;
  }

    //Method to future use
    public static ProgressDialog ctor(Context context, int show_percent) {
        MyCustomProgressDialog dialog = new MyCustomProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show_percent = show_percent;
        return dialog;
    }

  public MyCustomProgressDialog(Context context) {
    super(context);
  }

  public MyCustomProgressDialog(Context context, int theme) {
    super(context, theme);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_custom_progress_dialog);

      getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
      getWindow().getDecorView().setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                      | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

      pb_dialog = findViewById(R.id.pb_dialog);

  }

  @Override
  public void show() {
    super.show();
    pb_dialog.setVisibility(View.VISIBLE);
  }

  @Override
  public void dismiss() {
    super.dismiss();
    pb_dialog.setVisibility(View.GONE);
  }
}
