# Speech-Recognizer

This is a sample project which is having a continuous Speech Recognizer, which will listen voice commands as a background service.

![](https://github.com/sachinvarma/Speech-Recognizer/blob/master/Art/demo.gif)

**How to Add :**

Via Gradle:

```
repositories {
        maven { url "https://jitpack.io" }
    }
```
```
compile 'com.github.sachinvarma:Speech-Recognizer:0.0.1'
```

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

Donations
=============

This project needs you! If you would like to support this project's further development, the creator of this project or the continuous maintenance of this project, feel free to donate. Your donation is highly appreciated (and I love food, coffee and beer). Thank you!

**PayPal**

* **[Donate $5](https://www.paypal.me/sachinvarmaraja/5USD)**: Thank's for creating this project, here's a tea (or some juice) for you!
* **[Donate $10](https://www.paypal.me/sachinvarmaraja/10USD)**: Wow, I am stunned. Let me take you to the movies!
* **[Donate $15](https://www.paypal.me/sachinvarmaraja/15USD)**: I really appreciate your work, let's grab some lunch!
* **[Donate $25](https://www.paypal.me/sachinvarmaraja/25USD)**: That's some awesome stuff you did right there, dinner is on me!
* **[Donate $50](https://www.paypal.me/sachinvarmaraja/50USD)**: I really really want to support this project, great job!
* **[Donate $100](https://www.paypal.me/sachinvarmaraja/100USD)**: You are the man! This project saved me hours (if not days) of struggle and hard work, simply awesome!
* **[Donate $2799](https://www.paypal.me/sachinvarmaraja/2799USD)**: Go buddy, buy Macbook Pro for yourself!

Of course, you can also choose what you want to donate, all donations are awesome!

**LICENSE**
```
Copyright (C) 2018 Sachin Varma

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```

**Credits :**

Thanks to @gotev https://github.com/gotev/android-speech







