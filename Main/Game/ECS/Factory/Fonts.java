package Main.Game.ECS.Factory;

import org.jsfml.graphics.Font;

import java.io.File;
import java.nio.file.Paths;

public enum Fonts
{
    TITLE("LEMONMILK-Regular.otf"),SUBTITLE("LEMONMILK-Regular.otf"),MAIN("LEMONMILK-Regular.otf");

    public final Font f;
    public final String fileName;
    Fonts(String fileName)
    {
        this.fileName = fileName;
        this.f = new Font();
        try
        {
            f.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator+ fileName));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }


}
