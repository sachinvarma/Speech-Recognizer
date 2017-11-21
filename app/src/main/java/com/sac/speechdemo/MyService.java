package com.sac.speechdemo;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.user.speechrecognizationasservice.R;
import com.sac.speech.GoogleVoiceTypingDisabledException;
import com.sac.speech.Speech;
import com.sac.speech.SpeechDelegate;
import com.sac.speech.SpeechRecognitionNotAvailable;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

public class MyService extends Service implements SpeechDelegate, Speech.stopDueToDelay {

    public static SpeechDelegate delegate;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_SYSTEM, true);
        Speech.init(this);
        delegate = this;
        Speech.getInstance().setListener(this);

        if (Speech.getInstance().isListening()) {
            Speech.getInstance().stopListening();
            muteBeepSoundOfRecorder();
        } else {
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.RECORD_AUDIO)
                    .subscribe(granted -> {
                        if (granted) { // Always true pre-M
                            try {
                                Speech.getInstance().stopTextToSpeech();
                                Speech.getInstance().startListening(null, this);

                            } catch (SpeechRecognitionNotAvailable exc) {
                                //showSpeechNotSupportedDialog();

                            } catch (GoogleVoiceTypingDisabledException exc) {
                                //showEnableGoogleVoiceTyping();
                            }
                        } else {
                            Toast.makeText(this, R.string.permission_required, Toast.LENGTH_LONG);
                        }
                    });
            muteBeepSoundOfRecorder();

        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    @Override
    public void onStartOfSpeech() {
    }

    @Override
    public void onSpeechRmsChanged(float value) {

    }

    @Override
    public void onSpeechPartialResults(List<String> results) {
        for (String partial : results) {
            Log.d("Result", partial);
        }

    }

    @Override
    public void onSpeechResult(String result) {
        Log.d("Result", result);

    }

    @Override
    public void onSpecifiedCommandPronounced(String event) {
        ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_SYSTEM, true);
        if (Speech.getInstance().isListening()) {
            muteBeepSoundOfRecorder();
            Speech.getInstance().stopListening();
        } else {
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.RECORD_AUDIO)
                    .subscribe(granted -> {
                        if (granted) { // Always true pre-M
                            try {
                                Speech.getInstance().stopTextToSpeech();
                                Speech.getInstance().startListening(null, this);

                            } catch (SpeechRecognitionNotAvailable exc) {
                                //showSpeechNotSupportedDialog();

                            } catch (GoogleVoiceTypingDisabledException exc) {
                                //showEnableGoogleVoiceTyping();
                            }
                        } else {
                            Toast.makeText(this, R.string.permission_required, Toast.LENGTH_LONG).show();
                        }
                    });
            muteBeepSoundOfRecorder();

        }
    }

    private void muteBeepSoundOfRecorder() {
        AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (amanager != null) {
            amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
            amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
            amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            amanager.setStreamMute(AudioManager.STREAM_RING, true);
            amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        }

    }


}