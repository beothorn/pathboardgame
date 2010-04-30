package network;


public abstract class Network extends Thread{

	//	protected Game game;
	//	protected ObjectInputStream objetoEntrada;
	//	protected Socket socket;
	//
	//
	//	public void input() {
	//		while(true){
	//			try {
	//				objetoEntrada = new ObjectInputStream(socket.getInputStream());
	//				final Play input = (Play)objetoEntrada.readObject();
	//				game.play(input);
	//			} catch (final SocketException e) {
	//				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	//				e.printStackTrace();
	//			} catch (final IOException e) {
	//				e.printStackTrace();
	//			} catch (final ClassNotFoundException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//	}
	//
	//	public void play(final Play p){
	//		OutputStream outputStream;
	//		try {
	//			outputStream = socket.getOutputStream();
	//			final ObjectOutputStream objectOut = new ObjectOutputStream(outputStream);
	//			objectOut.writeObject(p);
	//			objectOut.flush();
	//		} catch (final IOException e) {
	//			JOptionPane.showMessageDialog(null,e.getMessage());
	//			e.printStackTrace();
	//		}
	//	}
}
