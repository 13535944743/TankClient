package TankWar;

public class Double_Game extends Thread{
	public void run() {
		try {
			Method.double_game();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
