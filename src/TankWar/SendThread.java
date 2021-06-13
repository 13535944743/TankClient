package TankWar;
public class SendThread extends Thread{
	public void run() {
		try {
			sendMessage();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void sendMessage() throws InterruptedException {
		while(true) {
			if(TankFrame.data == "") {
				Thread.sleep(TankFrame.sec);
			}
			if(TankFrame.data != "") {
				Client.out.println(TankFrame.data);
				System.out.println(TankFrame.data);
				Client.out.flush();
				TankFrame.data = "";
			}
			if(TankFrame.finish) {
				Client.out.println("finish");
				Client.out.flush();
			}
		}
		
	}
}