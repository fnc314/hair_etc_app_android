//package com.hairetc.hairetcapp1;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.twilio.sdk.TwilioRestClient;
//import com.twilio.sdk.TwilioRestException;
//import com.twilio.sdk.TwilioRestResponse;
//import com.twilio.sdk.resource.factory.SmsFactory;
//import com.twilio.sdk.resource.instance.Account;
//import com.twilio.sdk.resource.instance.Sms;
//
//public class TwilioText {
//	public static final String ACCOUNTSID = getString(R.string.twilioSID);
//	public static final String AUTHTOKEN = getString(R.string.twilioToken);
//	
//	public static void main(String[] args) {
//		ACCOUNTSID = getString(R.string.twilioSID);
//		AUTHTOKEN = getString(R.string.twilioToken);
//
//		TwilioRestClient client = new TwilioRestClient(ACCOUNTSID, AUTHTOKEN);
//		Account account = client.getAccount();
//		SmsFactory smsFactory = account.getSmsFactory();
//		
//		// Collection of stylists
//		Map<String, String> stylists = new HashMap<String, String>();
//		stylists.put(getString(R.string.myPhone), "Franco");
//		
//		String fromNumber = getString(R.string.twilioNumber);
//		
//		for (String toNumber : stylists.keySet() ) {
//			
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("From", fromNumber);
//			params.put("To", toNumber);
//			params.put("Body", toastString);
//			
//			try {
//				Sms sms = smsFactory.create(params);
//				System.out.println("Success Sending SMS: " + sms.getSid());
//			} catch (TwilioRestException e) {
//				e.printStackTrace();
//			}
//			
//		}
//	}
//}
