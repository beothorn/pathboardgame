package gameEngine;



public class GameLoop{
	private final Thread gameLoop;
	private boolean pause = false;
	
	public GameLoop(final JGamePanel gamePanel){
		gameLoop = new Thread(){
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
					
					synchronized (gameLoop) {
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
		gameLoop.start();
	}
	
	
	public void unPause(){
		synchronized (gameLoop) {
	        if(pause){
	        	pause = false;
	        	gameLoop.notify();
	        }
		}
	}
}
