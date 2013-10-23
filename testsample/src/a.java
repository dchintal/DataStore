import test.b;

public class a extends b {
	
	
	public static void main(String args[]){
	hadda had = new hadda();
	had.hello();
		try {			
		Thread.sleep(11);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Main Threas");
	}

}
	 class hadda extends b{
	public void hello()
	{
	test();		
	}
	
}
	

 