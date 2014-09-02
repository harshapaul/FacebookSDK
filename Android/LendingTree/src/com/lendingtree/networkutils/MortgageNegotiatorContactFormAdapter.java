package com.lendingtree.networkutils;

import java.io.IOException;
import java.io.OutputStream;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.web.client.ResourceAccessException;

import android.util.Log;

import com.lendingtree.model.ContactForm;
import com.lendingtree.model.PostResponse;
import com.lendingtree.services.BfHttpClient;
import com.lendingtree.util.Constants;

@EBean
public class MortgageNegotiatorContactFormAdapter {

	private static final String TAG = "MortgageNegotiatorContactFormAdapter";

	@Bean
	BfHttpClient connectionObj;

	public PostResponse callContactFormRequestTask(ContactForm contactForm)
	{
		PostResponse postResponse = null;
		Serializer serializer = new Persister();

		final StringBuffer xmlstr = new StringBuffer();
		//xmlstr.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		OutputStream outputstream = new OutputStream() {

			@Override
			public void write(int oneByte) throws IOException {
				// TODO Auto-generated method stub
				xmlstr.append((char) oneByte);
			}
		};

		try {

			serializer.write(contactForm, outputstream);

			Log.d(TAG,"data @@@@@"+xmlstr.toString());	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try{
			postResponse = (PostResponse)connectionObj.setClient2(Constants.CONTACT_FORM,null,/*"dataLead="+*/xmlstr.toString());
		}catch(ResourceAccessException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return postResponse;
	}
}
