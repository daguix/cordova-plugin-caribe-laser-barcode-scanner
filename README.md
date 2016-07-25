Cordova Caribe Barcode Scanner Plugin
=====================================

Caribe PL-40L Honeywell N4313 - N7313 plugin for Cordova / PhoneGap.

## Supported Platforms

- Android

## How to use

### Install

	cordova plugin add 

### Listen to scans

	document.addEventListener('barcode', function () {
	  console.log('last barcode', navigator.honeywell_scanner_plugin.barCode);
	  //Do something
	  
	},false)
