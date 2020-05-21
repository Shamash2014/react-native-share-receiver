package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class ShareReceiverModule
    extends ReactContextBaseJavaModule implements ActivityEventListener {
  private final ReactApplicationContext reactContext;

  public ShareReceiverModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    reactContext.addActivityEventListener(this);
  }

  public void setIntent(Intent intent) {
    String action = intent.getAction();
    String type = intent.getType();

    String text = intent.getStringExtra(Intent.EXTRA_TEXT);

    WritableMap params = Arguments.createMap();
    params.putString("type", type);
    params.putString("action", action);
    params.putString("content", text);
    reactContext
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit("ShareReceiver", params);
  }

  @Override
  public String getName() {
    return "ShareReceiver";
  }

  @Override
  public void onActivityResult(Activity activity, int requestCode,
                               int resultCode, Intent data) {
    Intent intent = activity.getIntent();
    setIntent(intent);
  }

  @Override
  public void onNewIntent(Intent intent) {
    setIntent(intent);
  }
}
