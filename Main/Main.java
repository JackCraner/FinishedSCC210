package Main;

import Main.Game.Game;
import Main.Menu.StartMenu;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;


public class Main
{


    public static void main(String[] args)
    {

        RenderWindow window = new RenderWindow(new VideoMode(1000,1000),"MainMenu");
        StartMenu newStartMenu = new StartMenu(window);

        Game game = Game.getGame();
        game.startGame();


    }


}
