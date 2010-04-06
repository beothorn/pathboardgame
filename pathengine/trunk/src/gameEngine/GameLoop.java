package gameEngine;



public class GameLoop{
	private final Thread gameLoop;
	private boolean actionAddedOnMidLoop = false;
	private boolean pause = false;
	
	public GameLoop(final JGamePanel gamePanel){
		gameLoop = new Thread(){
			@Override
			public void run() {
				super.run();
				while(true){
					long lastLoopTime = System.currentTimeMillis();
					boolean someActionStillProcessing = false;
					while (someActionStillProcessing || actionAddedOnMidLoop) {
						actionAddedOnMidLoop = false;
						final long delta = System.currentTimeMillis() - lastLoopTime;
						lastLoopTime = System.currentTimeMillis();
						gamePanel.stepGame(delta);
						if(gamePanel.actionsStillProcessing()){
							someActionStillProcessing = true;
						}else{
							someActionStillProcessing = false;							
						}
					}
					
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
	
	
	public synchronized void loop(){
		synchronized (gameLoop) {
	        if(pause){
	        	pause = false;
	        	gameLoop.notify();
	        }
	    }
	}

	public void actionAddedOnMidLoop() {
		this.actionAddedOnMidLoop = true;
	}

}
