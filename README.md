Cordova Caribe Laser Barcode Scanner Plugin
===========================================

Caribe PL-40L Honeywell N4313 - N7313 plugin for Cordova / PhoneGap.

## Supported Platforms

- Android

## How to use

### Install

	cordova plugin add cordova-plugin-caribe-laser-barcode-scanner

### Listen to scans

	document.addEventListener('barcode', function () {
	  console.log('last barcode', navigator.laser_scanner_plugin.barCode);
	  //Do something
	  
	},false)
