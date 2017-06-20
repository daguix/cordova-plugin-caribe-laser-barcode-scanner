You can find the SDK at this address:
https://www.dropbox.com/s/t9qge3bqn4tftxb/TestScan.zip?dl=0

The cordova plugin is a wrapper around the SDK.

List of java functions:

public boolean openScan()；
Function：Open scan。

public boolean closeScan()；
Function：Close scan。

public boolean isScanOpened()；
function：get scan condition。
Return Value：True Scan is on；False Scan is off。

public boolean setOutScanMode(int mode)；
Function：Enable display scan result；
Parameter：1 Display scan result mode ；0 broadcast mode output。

public int getOutScanMode()；
Function：Obtain Scan mode；
Return Value： 1 keyboard output；0 Broadcast mode。

public boolean setScanVibrate()；
Function：set vibration when scan succeed。

public boolean setScanUnVibrate()
Function：cancel vibration when scan succeed。

public boolean getScanVibrateState()；
Function：Whether vibration on or off after scan succeed；
Return Value：True vibration on；False Vibration off。

public boolean setScanCodeEnterKey()；
Function：Attach the enter key after scan result。

	public boolean setScanCodeNoEnterKey()；
	Function：Do not attach the enter key after scan result。
	
	public boolean setScanBeep()；
Function：Play warning tone after scan succeed。

	public boolean setScanUnBeep()；
Function：No warning tone after scan succeed。

