import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainClass m=new MainClass();
	}
	
	public MainClass() {
		try{
			
			Socket s = new Socket("localhost",41212);
			System.out.println("hello");
			Scanner sc=new Scanner(System.in);
			//final InputStream is=s.getInputStream();
			//OutputStream os=s.getOutputStream();
			final DataInputStream is=new DataInputStream(s.getInputStream());
			DataOutputStream os=new DataOutputStream(s.getOutputStream());
			String str=sc.nextLine();
			
			Thread t=new Thread()
			{
				public void run()
				{
					String str1="";
					while(!str1.equals("bye"))
					{
						try{
						byte b[]=new byte[1000];
						is.read(b);
						System.out.println("Client sent:"+new String(b));
						}
						catch(Exception e1)
						{
							System.out.println("Error:"+e1.getMessage());
						}
					}
				}
			};
			t.start();
			
			while(!str.equals("exit"))
			{
				os.write(str.getBytes());
				str=sc.nextLine();
			}
		}
		catch(Exception e)
		{
			System.out.println("Error:"+e.getMessage());
		}
	}

}
