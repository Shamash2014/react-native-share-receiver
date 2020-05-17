package com.reactlibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;


public class ShareReceiverModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext reactContext;
  private final Receiver receiver;
  private Callback callback;

  private class Receiver extends BroadcastReceiver {

    private final ShareReceiverModule instance;

    public Receiver(ShareReceiverModule shareReceiverModule) {
      this.instance = shareReceiverModule;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      instance.setIntent(intent);
    }
  }

  public ShareReceiverModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.receiver = new Receiver(this);
    IntentFilter intentFilter = new IntentFilter();
    this.reactContext.registerReceiver(this.receiver, intentFilter);
  }

  private void setIntent(Intent intent) {
    String action = intent.getAction();
    String type = intent.getType();

   String text =  intent.getStringExtra(Intent.EXTRA_TEXT);

     WritableMap params = Arguments.createMap();
     params.putString("type", type);
     params.putString("action", action);
     params.putString("content", text);
     reactContext
             .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
             .emit("intent", params);
  }

  @Override
  public String getName() {
    return "ShareReceiver";
  }

 @ReactMethod
  public void removeEventListener() {
    this.reactContext.unregisterReceiver(this.receiver);
  }
}
