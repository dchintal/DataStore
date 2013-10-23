package com.test.samplecheck;

import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new MainActivity();
		Button b1;
		final TextView tv1;
		b1= (Button)findViewById(R.id.bd1);
		tv1 = (TextView)findViewById(R.id.textView1);
	b1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			tv1.setText("SampleCheck");
		}
	});
	}
	
	public void MainActivity(){
		try{
		FileInputStream fs=new FileInputStream("test.mp4");
		int ch=0;
		DatagramSocket ds=new DatagramSocket();
		byte[] b = new byte[1000]; 
		DatagramPacket dgram; 
		ch=fs.read(b, 0, b.length);
		while(ch!=-1){
			dgram = new DatagramPacket(b, b.length, InetAddress.getByName("224.1.2.3"), 5123);
			ds.send(dgram);
			Thread.sleep(1000);
			ch=fs.read(b, 0, b.length);			
		}
		fs.close();
		}
		catch(Exception e)
		{
			System.out.println("Error:"+e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
