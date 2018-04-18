# Speech-Recognizer

This is a sample project which is having a continuous Speech Recognizer, which will listen voice commands as a background service.

![](https://github.com/sachinvarma/Speech-Recognizer/blob/master/Art/demo.gif)

**How to Add :**

Via Gradle:

```compile 'com.github.sachinvarma:Speech-Recognizer:0.0.1' ```

Via Maven:

```
<dependency>
 <groupId>com.github.sachinvarma</groupId>
 <artifactId>Speech-Recognizer</artifactId>
 <version>0.0.1</version>
</dependency> 
```


**How it works:**

1) Create a Simple Service class, which is used to initate the Scpeech Recognizer and run contionously in background and update the listened words.

   https://github.com/sachinvarma/Speech-Recognizer/blob/master/app/src/main/java/com/sac/speechdemo/MyService.java

2) Simply start the service.

   ```startService(new Intent(MainActivity.this, MyService.class));```

**NOTE :**

Some of the Mobile companies are restricting the services by default to run on background. So we have to enable AutoStart for such device that
will allow the service to run in background.

Requesting Permission : 

```
private void enableAutoStart() {
    for (Intent intent : Constants.AUTO_START_INTENTS) {
      if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
        new Builder(this).title(R.string.enable_autostart)
          .content(R.string.ask_permission)
          .theme(Theme.LIGHT)
          .positiveText(getString(R.string.allow))
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
```  
**Credits :**

Thanks to @zagum https://github.com/zagum/SpeechRecognitionView







