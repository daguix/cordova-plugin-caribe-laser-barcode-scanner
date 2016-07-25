var exec = require('cordova/exec'),
  channel = require('cordova/channel');

function LaserScannerPlugin() {
  this.barCode = null;
}


LaserScannerPlugin.prototype.getLastBarCode = function (success_cb, error_cb) {
  exec(success_cb, error_cb, "LaserScannerPlugin", "getLastBarCode", []);
};

LaserScannerPlugin.prototype.setScanBeep = function (success_cb, error_cb) {
  exec(success_cb, error_cb, "LaserScannerPlugin", "setScanBeep", []);
};

LaserScannerPlugin.prototype.setScanUnBeep = function (success_cb, error_cb) {
  exec(success_cb, error_cb, "LaserScannerPlugin", "setScanUnBeep", []);
};

LaserScannerPlugin.prototype.setScanVibrate = function (success_cb, error_cb) {
  exec(success_cb, error_cb, "LaserScannerPlugin", "setScanVibrate", []);
};

LaserScannerPlugin.prototype.setScanUnVibrate = function (success_cb, error_cb) {
  exec(success_cb, error_cb, "LaserScannerPlugin", "setScanUnVibrate", []);
};

LaserScannerPlugin.prototype.getScanVibrateState = function (success_cb, error_cb) {
  exec(success_cb, error_cb, "LaserScannerPlugin", "getScanVibrateState", []);
};


var laserScannerPlugin = new LaserScannerPlugin();

channel.createSticky('onCordovaConnectionReady');
channel.waitForInitialization('onCordovaConnectionReady');

channel.onCordovaReady.subscribe(function () {
  laserScannerPlugin.getLastBarCode(function (info) {
    console.log('got info', info);
      if (info) {
        laserScannerPlugin.barCode = info;
        cordova.fireDocumentEvent("barcode");
      }
      // should only fire this once
      if (channel.onCordovaConnectionReady.state !== 2) {
        channel.onCordovaConnectionReady.fire();
      }
    },
    function (e) {
      // If we can't get the network info we should still tell Cordova
      // to fire the deviceready event.
      if (channel.onCordovaConnectionReady.state !== 2) {
        channel.onCordovaConnectionReady.fire();
      }
      console.log("Error initializing Network Connection: " + e);
    });
});

module.exports = laserScannerPlugin;
