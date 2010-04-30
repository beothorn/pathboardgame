package gameEngine;



public class GameLoop{
	private final Thread gameLoopingThread;
	private boolean pause = false;
	
	public GameLoop(final JGamePanel gamePanel){
		gameLoopingThread = new Thread(){
			@Override
			public void run() {
				super.run();
				while(true){
					long lastLoopTime = System.currentTimeMillis();
					do{
						final long delta = System.currentTimeMillis() - lastLoopTime;
						lastLoopTime = System.currentTimeMillis();
						gamePanel.stepGame(delta);
					}while (gamePanel.actionsStillProcessing());
					
					synchronized (gameLoopingThread) {
						pause = true;
						while (pause) {
							try {
								wait();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		};
		gameLoopingThread.start();
	}
	
	
	public void unPause(){
		synchronized (gameLoopingThread) {
	        if(pause){
	        	pause = false;
	        	gameLoopingThread.notify();
	        }
		}
	}
}
