package TankWar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client{
	private Socket socket;
	public static PrintWriter out;
	public BufferedReader in;
	public TankFrame tf;
	public Client(JFrame f) throws UnknownHostException, IOException, InterruptedException {
		this.socket = new Socket("127.0.0.1", 5679);
		this.out = new PrintWriter(socket.getOutputStream());        //由socket对象得到输出流，并构造PrintWriter对象
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //构造BufferReader对象
		f.setVisible(false);
		Double_Game dg = new Double_Game();
		dg.start();
		SendThread st = new SendThread();
		st.start();
		while(Method.tf == null) {
			Thread.sleep(50);
		}
		tf = Method.tf;
		this.tf = Method.tf;
		receiveMessage();
	}
	private void receiveMessage() throws IOException, InterruptedException {
		String readline;
		while(true) {
			readline = in.readLine();
//			System.out.println(readline);
			String[] buff = readline.split("@");
			if(buff[0].equals("p")) {
				Direction direction = Direction.UP;
				Group group = Group.Player;
				int x = Integer.parseInt(buff[1]);
				int y = Integer.parseInt(buff[2]);
//				switch(d) {
//				case 1:
//					direction = Direction.UP;
//					break;
//				case 2:
//					direction = Direction.LEFT;
//					break;
//				case 3:
//					direction = Direction.DOWN;
//					break;
//				case 4:
//					direction = Direction.LEFT;
//					break;
//				}
				tf.player1.add(new Tank(x, y, direction, group, tf));
			}
			if(buff[0].equals("q")) {
				Direction direction = Direction.UP;
				Group group = Group.Player;
				int x = Integer.parseInt(buff[1]);
				int y = Integer.parseInt(buff[2]);
				Tank temp = new Tank(x, y, direction, group, tf);
				temp.setId(1);
				tf.player2.add(temp);
			}
			if(buff[0].equals("e")) {
				Direction direction = Direction.UP;
				Group group = Group.Enemy;
				int x = Integer.parseInt(buff[1]);
				int y = Integer.parseInt(buff[2]);
				Tank temp = new Tank(x, y, direction, group, tf);
				temp.setId(1);
				tf.enemies.add(temp);
			}
			if(buff[0].equals("b")) {
				Direction direction = Direction.UP;
				Group group = Group.Player;
				int x = Integer.parseInt(buff[1]);
				int y = Integer.parseInt(buff[2]);
				int d = Integer.parseInt(buff[3]);
				int g = Integer.parseInt(buff[4]);
				if(g == 0) {
					group = Group.Enemy;
				}
				switch(d) {
				case 1:
					direction = Direction.UP;
					break;
				case 2:
					direction = Direction.LEFT;
					break;
				case 3:
					direction = Direction.DOWN;
					break;
				case 4:
					direction = Direction.RIGHT;
					break;
				}
				tf.bullets.add(new Bullet(x, y, direction, group, tf));
			}
			if(buff[0].equals("stop")) {
				for(int j = 0; j < tf.player1.size(); j++) {
					tf.player1.get(j).setMoving(false);
				}
			}
			if(buff[0].equals("playerchange")) {
				for(int j = 0; j < tf.player1.size(); j++) {
					Direction dir = Direction.valueOf(buff[1]);
					tf.player1.get(j).setDir(dir);
					tf.player1.get(j).setMoving(true);
					continue;
				}
			}
//			if(buff[0].equals("d")) {
//				Direction direction = Direction.UP;
//				int d = Integer.parseInt(buff[1]);
//				switch(d) {
//				case 1:
//					direction = Direction.UP;
//					break;
//				case 2:
//					direction = Direction.LEFT;
//					break;
//				case 3:
//					direction = Direction.DOWN;
//					break;
//				case 4:
//					direction = Direction.LEFT;
//					break;
//				}
//			}
			Thread.sleep(30);
		}
		
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
}
