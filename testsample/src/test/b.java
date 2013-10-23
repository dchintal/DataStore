package test;



public class b implements Runnable{

	Thread dc;
	Thread dc2;
	protected void test(){
		dc = new Thread(this, "sa");
		dc2 = new Thread(this,"ddd");
		dc2.setPriority(2);
		dc2.setName("111");
		
		dc.setPriority(1);
		dc.setName("11133");
		
			dc.start();
			try {
				Thread.sleep(500);
				dc2.start();
				Thread.sleep(500);
				dc2.stop();
				Thread.sleep(500);
				dc.stop();
				dc2=null;
				dc=null;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
		
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
			
	 
			while(dc.isAlive()){
				System.out.println("printing from child"  + Thread.currentThread().getName());	
				///dc2.start();
			}
		
	}
	
}

