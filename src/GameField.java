import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private Image apple;
    private Image snake;
    private final int fieldSize = 640;
    private final int CELL = 16;
    private final int totalAmountOfCells = 40;
    private int appleX;
    private int appleY;
    private int[] snakeX = new int[1600];
    private int[] snakeY = new int[1600];
    private int snakeParts = 3;
    private Direction direction;
    private boolean inGame = true;
    private Timer timer;

    public GameField(){
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(fieldSize,fieldSize));
        addImages();
        createAppleCoordinates();
        createSnake();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

        timer = new Timer(250,this);
        timer.start();
    }

    private void addImages(){
        ImageIcon img = new ImageIcon("apple.png");
        apple = img.getImage();
        ImageIcon snk = new ImageIcon("dot.png");
        snake = snk.getImage();
    }

    private void createAppleCoordinates(){
        appleX = new Random().nextInt(totalAmountOfCells)*CELL;
        appleY = new Random().nextInt(totalAmountOfCells)*CELL;
    }

    private void createSnake(){
        for (int i = 0; i < snakeParts; i++){
            snakeX[i] = CELL*3 - CELL*i;
        }

        for (int i = 0; i < snakeParts; i++){
            snakeY[i] = CELL;
        }

        direction = Direction.RIGHT;
    }

    @Override
    protected void paintComponent(Graphics g){//вызывается автоматически в самом классе JPanel для отрисовки поля
        super.paintComponent(g);
        if(inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < snakeParts; i++) {
                g.drawImage(snake, snakeX[i], snakeY[i], this);
            }
        }else {
            g.setColor(Color.pink);
            g.drawString("GameOver",310,310);
        }
    }

    private void moveSnake(){
        for(int i = snakeParts; i > 0; i--){
            snakeX[i] = snakeX[i-1] ;
            snakeY[i] = snakeY[i-1];
        }
        switch (direction){
            case UP -> snakeY[0] -= CELL;
            case DOWN -> snakeY[0] += CELL;
            case RIGHT-> snakeX[0] +=CELL;
            case LEFT-> snakeX[0] -=CELL;
        }
    }

    private void checkApple(){
        if(snakeX[0]==appleX && snakeY[0] == appleY) {
            snakeParts++;
            createAppleCoordinates();
        }
    }

    private void checkCollisions(){
        for(int i = 2; i < snakeParts; i++){
            if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                inGame = false;
                break;
            }
        }

        if(snakeX[0] > fieldSize)
            inGame = false;
        if(snakeX[0] < 0)
            inGame = false;
        if(snakeY[0] > fieldSize)
            inGame = false;
        if(snakeY[0] < 0)
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) { // вызывается объектом Timer каждые 250млс
        if(inGame){
            checkCollisions();
            checkApple();
            moveSnake();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && direction != Direction.RIGHT)
                direction = Direction.LEFT;
            if(key == KeyEvent.VK_RIGHT && direction != Direction.LEFT)
                direction = Direction.RIGHT;
            if(key == KeyEvent.VK_UP && direction != Direction.DOWN)
                direction = Direction.UP;
            if(key == KeyEvent.VK_DOWN && direction != Direction.UP)
                direction = Direction.DOWN;
        }
    }
}
