# P1000SDK
#P1000SampleApp
This is a sample app for Y1000/P1000 SDK.

Add the below line of code in your project's root level gradle.

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Add the below dependencies in the project's build.gradle

dependencies {
	        implementation 'com.github.NTDhananjay:P1000SDK:Tag'
	}


Add the below permission in the manifest file
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<uses-permission android:name="com.pos.permission.ACCESSORY_DATETIME" />
<uses-permission android:name="com.pos.permission.ACCESSORY_LED" />
<uses-permission android:name="com.pos.permission.ACCESSORY_BEEP" />
<uses-permission android:name="com.pos.permission.ACCESSORY_RFREGISTER" />
<uses-permission android:name="com.pos.permission.CARD_READER_ICC" />
<uses-permission android:name="com.pos.permission.CARD_READER_PICC" />
<uses-permission android:name="com.pos.permission.CARD_READER_MAG" />
<uses-permission android:name="com.pos.permission.COMMUNICATION" />
<uses-permission android:name="com.pos.permission.PRINTER" />
<uses-permission android:name="com.pos.permission.SECURITY" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="com.pos.permission.ACCESSORY_RFREGISTER" />
<uses-permission android:name="com.pos.permission.EMVCORE" />

Implementation

Initialize the P1000Manager with the context and your provided License-Key. e.g

P1000Manager p1000Manager = P1000Manager.getInstance(this, "LICENSE-KEY");

Create an Object of P1000Request and set the respective fields:


Implementation
Initialize the P1000Manager with the context and your provided License-Key. e.g
P1000Manager p1000Manager = P1000Manager.getInstance(this, "LICENSE-KEY");
Create an Object of P1000Request and set the respective fields:
    P1000Request p1000Request = new P1000Request();
        p1000Request.setUsername("USERNAME");
        p1000Request.setPassword("PASSWORD");
        p1000Request.setRefCompany("COMPANYNAME");
        p1000Request.setMid("MERCHANMT_ID");
        p1000Request.setTid("TERMINAL_ID");
        p1000Request.setTransactionId("TRANSACTION_ID"); //p1000Manager.getTransactionId()
        p1000Request.setImei("IMEI");
        p1000Request.setImsi("IMSI");
        p1000Request.setTxn_amount("AMOUNT");
        p1000Request.setRequestCode(TransactionType); 
Provided Transaction type are as follows:

    	TransactionType.INQUIRY 	: for balance enquiry
    	TransactionType.WITHDRAWAL	: for amount withdrawal
    	TransactionType.DEBIT		: for sale by card.
Transaction Id is your unique 12 digit Id. In case you dont have any such Unique Id , you can call the below method to generate the Unique Transaction Id.
note: The Transaction Id is returned in the Response Callback i.e successCallback and/or failureCallback as token.

p1000Manager.getTransactionId()
Once the P1000Request object is created and set proceed with the excecution. e.g

	p1000Manager.execute(uCubeRequest,new P1000CallBacks() {
            @Override
            public void successCallback(JSONObject jsonObject) {
                
            }

            @Override
            public void progressCallback(String s) {
              
            }

            @Override
            public void failureCallback(JSONObject jsonObject) {
               
            }
        });
P1000CallBacks implement the method for success, failure and progress state.

i. public void successCallback(JSONObject jsonObject);
The above method is called when the transaction is success.

ii. public void failureCallback(JSONObject jsonObject);
The above method is called when the transaction is failed due to some issues.

iii. public void progressCallback(String message)
The above method is called to show the message about the current status. This message can be displayed to the end-user refelcting the state of Device.



