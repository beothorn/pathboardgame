Path

    Here is an outdated version http://sites.google.com/site/lucass/home/projects/path 

    Path is a java board game similar to connect 4 where the goal is to make a path to the adversary side. 

    The source consists in 4 projects: 

    pathai : An ai for pathgame. All it does is build a tree with all play possibilities and find the one who scores most on the evaluator implementation. It depends on the pathboardgame project.
    pathaibattle : A simple implementation that confronts two pathboardgame ais. It depends on the pathboardgame and pathai(for now) project.
    pathengine : A java 2d engine created to be used by the path game, but will be used in other games.
    pathgui : The graphical implementation of the game, as an application and as applet. 

About the game
Goal of the game

    The main goal of the game is to make a path of pieces leading to the adversary side. A piece forms a path with every piece in a near square horizontally or vertically. 

Pieces

    The players have two kinds of pieces, the strongs and the weaks. The weak pieces are atracted to its owner player side (like a tetris piece). They can push others weak pieces but are blocked by strong pieces. Weak pieces can be pushed out of the board in the vertical direction. If a weak piece is pushed out of the board horizontally it is teleported to the other side of the line. Strong pieces aren't atracted. They can be at any position of the board. The strong pieces can push any number of weak pieces and at least one strong piece. Strong pieces are blocked from moving if there's another two or more strong pieces in the same line that it it's trying to push. (a line is a sequence of pieces horizontally or vertically connected, without any empty space between) 

The game

    All new pieces enter the game in the first line relative to the player who is putting the piece. In the first round each player puts 3 strong pieces on board. You can't put a strong piece in an already occupied square. After this, all plays follow the same sequence. The player put 3 weak pieces at the first line and move each strong one square in the horizontal or vertical direction. The sequence is always this: put the weaks, move the strong. The player can skip any time, except when he is putting the srongs on the board. If a player skip the putting weaks phase he goes to the moving strongs phase. The game goes on until someone makes a path of weak pieces to the adversary side. 

Other rules

    When a piece is pushed in the horizontal direction out of the board, it is moved to the first square on the other side of the line, this play is called a teleport. The teleport can only bem made when a piece pushes another out of the board. A piece can't teleport itself. Strong pieces cannot be pushed outside of the board. There will always be six strong pieces on the board. To win the path must have only weak pieces. If the first square of the line is blocked, the piece will not be teleported. 

---- I'm still working on the game GUI and AI, but it's already playable on command line. Just download the jar, if you pass the -help parameter it will show the basic commands.

And if you want to make an ai just implement the PathAi? interface on the path logic project. I will add a better description of what is passed to the ai later, i hope.

thanks

Hope you enjoy :)

lucass 
