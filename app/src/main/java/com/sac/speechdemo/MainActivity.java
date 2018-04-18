package com.sac.speechdemo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.user.speechrecognizationasservice.R;
import com.sac.speech.Speech;

public class MainActivity extends AppCompatActivity {

  private Button btStartService;
  private TextView tvText;

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btStartService = (Button) findViewById(R.id.btStartService);
    tvText = (TextView) findViewById(R.id.tvText);
    //Some devices will not allow background service to work, So we have to enable autoStart for the app.
    //As per now we are not having any way to check autoStart is enable or not,so better to give this in LoginArea,
    //so user will not get this popup again and again until he logout
    enableAutoStart();

    if (checkServiceRunning()) {
      btStartService.setText(getString(R.string.stop_service));
      tvText.setVisibility(View.VISIBLE);
    }

    btStartService.setOnClickListener(v -> {
      if (btStartService.getText().toString().equalsIgnoreCase(getString(R.string.start_service))) {
        startService(new Intent(MainActivity.this, MyService.class));
        Speech.init(MainActivity.this);
        btStartService.setText(getString(R.string.stop_service));
        tvText.setVisibility(View.VISIBLE);
      } else {
        stopService(new Intent(MainActivity.this, MyService.class));
        btStartService.setText(getString(R.string.start_service));
        tvText.setVisibility(View.GONE);
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private void enableAutoStart() {
    for (Intent intent : Constants.AUTO_START_INTENTS) {
      if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
        new MaterialDialog.Builder(this).title("Enable AutoStart")
          .content(
            "Please allow QuickAlert to always run in the background,else our services can't be accessed when you are in distress")
          .theme(Theme.LIGHT)
          .positiveText("ALLOW")
          .onPositive((dialog, which) -> {
            try {
              for (Intent intent1 : Constants.AUTO_START_INTENTS)
                if (getPackageManager().resolveActivity(intent1, PackageManager.MATCH_DEFAULT_ONLY)
                  != null) {
                  startActivity(intent1);
                  break;
                }
            } catch (Exception e) {
              e.printStackTrace();
            }
          })
          .show();
        break;
      }
    }
  }

  public boolean checkServiceRunning() {
    ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
      Integer.MAX_VALUE)) {
      if ("com.sac.speechdemo.MyService".equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }
}
