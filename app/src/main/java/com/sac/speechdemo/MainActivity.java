package com.sac.speechdemo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.example.user.speechrecognizationasservice.R;
import com.sac.speech.Speech;

public class MainActivity extends AppCompatActivity {

  private Button startServiceButton;

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    startServiceButton = (Button) findViewById(R.id.startService);
    //Some devices will not allow background service to work, So we have to enable autoStart for the app
    //As per now we are not having any way to check autoStart is enable or not,so better to give this in LoginArea,
    //so user will not get this popup again and again until he logout
    enableAutoStart();

    if (checkServiceRunning()) {
      startServiceButton.setText("STOP SERVICE");
    }

    startServiceButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (startServiceButton.getText().toString().equalsIgnoreCase("START SERVICE")) {
          startService(new Intent(MainActivity.this, MyService.class));
          Speech.init(MainActivity.this);
          startServiceButton.setText("STOP SERVICE");
        } else {
          stopService(new Intent(MainActivity.this, MyService.class));
          startServiceButton.setText("START SERVICE");
        }
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private void enableAutoStart() {
    if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
      new MaterialDialog.Builder(MainActivity.this).title("Enable AutoStart")
        .content(
          "Please allow QuickAlert to always run in the background,else our services can't be accessed when you are in distress")
        .theme(Theme.LIGHT)
        .positiveText("ALLOW")
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.miui.securitycenter",
              "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            startActivity(intent);
          }
        })
        .show();
    } else if (Build.BRAND.equalsIgnoreCase("Letv")) {
      new MaterialDialog.Builder(MainActivity.this).title("Enable AutoStart")
        .content(
          "Please allow QuickAlert to always run in the background,else our services can't be accessed when you are in distress")
        .theme(Theme.LIGHT)
        .positiveText("ALLOW")
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.letv.android.letvsafe",
              "com.letv.android.letvsafe.AutobootManageActivity"));
            startActivity(intent);
          }
        })
        .show();
    } else if (Build.BRAND.equalsIgnoreCase("Honor")) {
      new MaterialDialog.Builder(MainActivity.this).title("Enable AutoStart")
        .content(
          "Please allow QuickAlert to always run in the background,else our services can't be accessed when you are in distress")
        .theme(Theme.LIGHT)
        .positiveText("ALLOW")
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.huawei.systemmanager",
              "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            startActivity(intent);
          }
        })
        .show();
    } else if (Build.MANUFACTURER.equalsIgnoreCase("oppo")) {
      new MaterialDialog.Builder(MainActivity.this).title("Enable AutoStart")
        .content(
          "Please allow QuickAlert to always run in the background,else our services can't be accessed when you are in distress")
        .theme(Theme.LIGHT)
        .positiveText("ALLOW")
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            try {
              Intent intent = new Intent();
              intent.setClassName("com.coloros.safecenter",
                "com.coloros.safecenter.permission.startup.StartupAppListActivity");
              startActivity(intent);
            } catch (Exception e) {
              try {
                Intent intent = new Intent();
                intent.setClassName("com.oppo.safe",
                  "com.oppo.safe.permission.startup.StartupAppListActivity");
                startActivity(intent);
              } catch (Exception ex) {
                try {
                  Intent intent = new Intent();
                  intent.setClassName("com.coloros.safecenter",
                    "com.coloros.safecenter.startupapp.StartupAppListActivity");
                  startActivity(intent);
                } catch (Exception exx) {

                }
              }
            }
          }
        })
        .show();
    } else if (Build.MANUFACTURER.contains("vivo")) {
      new MaterialDialog.Builder(MainActivity.this).title("Enable AutoStart")
        .content(
          "Please allow QuickAlert to always run in the background.Our app runs in background to detect when you are in distress.")
        .theme(Theme.LIGHT)
        .positiveText("ALLOW")
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            try {
              Intent intent = new Intent();
              intent.setComponent(new ComponentName("com.iqoo.secure",
                "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
              startActivity(intent);
            } catch (Exception e) {
              try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.vivo.permissionmanager",
                  "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                startActivity(intent);
              } catch (Exception ex) {
                try {
                  Intent intent = new Intent();
                  intent.setClassName("com.iqoo.secure",
                    "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager");
                  startActivity(intent);
                } catch (Exception exx) {
                  ex.printStackTrace();
                }
              }
            }
          }
        })
        .show();
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
