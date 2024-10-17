import javax.swing.*;
import java.awt.*;

public class Field {
    private JFrame window;

    public Field(){
        window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.setSize(640,640);
        window.setResizable(false);
        window.setLocation(400,200);
        window.add(new GameField());
        window.pack();

        window.setVisible(true);
    }

}
