package org.instafret;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Locale;

import android.device.ScanDevice;

public class LaserScannerPlugin extends CordovaPlugin {

  ScanDevice sm = null;

  private final static String SCAN_ACTION = "scan.rcv.message";
  private static final String LOG_TAG = "LaserScannerPlugin";

  private CallbackContext connectionCallbackContext;

  BroadcastReceiver receiver;
  private String lastInfo = null;

  /**
   * Sets the context of the Command. This can then be used to do things like
   * get file paths associated with the Activity.
   *
   * @param cordova The context of the main Activity.
   * @param webView The CordovaWebView Cordova is running in.
   */
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    this.connectionCallbackContext = null;
    sm = new ScanDevice();
    lastInfo = null;

    // We need to listen to connectivity events to update navigator.connection
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(SCAN_ACTION);
    if (this.receiver == null) {
      this.receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          // (The null check is for the ARM Emulator, please use Intel Emulator for better results)
          if(LaserScannerPlugin.this.webView != null) {
            Log.v("Amar", "received info");
            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            Log.v("Amar", "----codetype--" + temp);
            String barcodeStr = new String(barocode, 0, barocodelen);
            //       showScanResult.setText(barcodeStr);
            Log.v("Amar", barcodeStr);
            updateLastBarCode(barcodeStr);
          }
        }
      };
      webView.getContext().registerReceiver(this.receiver, intentFilter);
    }

  }

  /**
   * Executes the request and returns PluginResult.
   *
   * @param action            The action to execute.
   * @param args              JSONArry of arguments for the plugin.
   * @param callbackContext   The callback id used when calling back into JavaScript.
   * @return                  True if the action was valid, false otherwise.
   */
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    Log.v("Amar", "exec");
    if (action.equals("getLastBarCode")) {
      Log.v("Amar", "exec getLastBarCode");
      this.connectionCallbackContext = callbackContext;
      PluginResult result = new PluginResult(PluginResult.Status.OK, lastInfo);
      result.setKeepCallback(true);
      connectionCallbackContext.sendPluginResult(result);
      return true;
    }
    else if (action.equals("setScanBeep")) {
      sm.setScanBeep();
      return true;
    }
    else if (action.equals("setScanUnBeep")) {
      sm.setScanUnBeep();
      return true;
    }
    else if (action.equals("setScanVibrate")) {
      sm.setScanVibrate();
      return true;
    }
    else if (action.equals("setScanUnVibrate")) {
      sm.setScanUnVibrate();
      return true;
    }
    else if (action.equals("getScanVibrateState")) {
      Boolean result = sm.getScanVibrateState();
      callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
      return true;
    }

    return false;
  }

  /**
   * Stop network receiver.
   */
  public void onDestroy() {
    if (this.receiver != null) {
      try {
        webView.getContext().unregisterReceiver(this.receiver);
      } catch (Exception e) {
        Log.e(LOG_TAG, "Error unregistering network receiver: " + e.getMessage(), e);
      } finally {
        receiver = null;
      }
    }
  }

  //--------------------------------------------------------------------------
  // LOCAL METHODS
  //--------------------------------------------------------------------------

  /**
   * Updates the JavaScript side whenever the connection changes
   *
   * @param info the current active network info
   * @return
   */
  private void updateLastBarCode(String barcode) {
    // send update to javascript "navigator.network.connection"
    // Jellybean sends its own info
    if(barcode != lastInfo)
    {
      sendUpdate(barcode);
      lastInfo = barcode;
    }
  }

  /**
   * Create a new plugin result and send it back to JavaScript
   *
   * @param connection the network info to set as navigator.connection
   */
  private void sendUpdate(String barcode) {
    if (connectionCallbackContext != null) {
      PluginResult result = new PluginResult(PluginResult.Status.OK, barcode);
      result.setKeepCallback(true);
      connectionCallbackContext.sendPluginResult(result);
    }
    //webView.postMessage("networkconnection", type);
  }

}
