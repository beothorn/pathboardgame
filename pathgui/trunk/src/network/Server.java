package network;

import gameLogic.Game;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Network{

	public static void start(final Game game) throws IOException {
		new Server(game);
	}

	public Server(final Game game) throws IOException{
		System.out.println("Waiting connection");
		socket = new ServerSocket(4242).accept();
		//		game.setPlayListener(this);
		System.out.println("Conexao de " + socket.getInetAddress());
		setDaemon(true);
		start();
	}

	@Override
	public void run(){
		input();
	}

}
