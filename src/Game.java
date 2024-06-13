import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer(5, this);
    private int timePlayed = 0;
    private int shots = 0;
    private BufferedImage image;
    private BufferedImage tempImage;
    private ArrayList<Fire> fires = new ArrayList<Fire>();
    private int fireSpeedY = 5;
    private int targetX = 0;
    private int targetY = 20;
    private int targetRadius = 20;
    private int targetSpeedX = 5;
    private int spaceshipX = 0;
    private int spaceshipY = 485;
    private int spaceshipSpeedX =20;

    // check done
    public Game(){
        try{
            image = ImageIO.read(new FileImageInputStream(new File("spaceship.png")));
        }
        catch (IOException ex){
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start();
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);

        g.setColor(Color.red);
        g.fillOval(targetX, targetY, targetRadius,targetRadius);
        g.drawImage(image, spaceshipX,spaceshipY,image.getWidth()/5, image.getHeight()/5,this);
        if (!fires.isEmpty()){
            Iterator<Fire> iterator = fires.iterator();
            while (iterator.hasNext()) {
                Fire fire = iterator.next();
                if(fire.getY()<0){
                    iterator.remove();
                }
                else{
                    try{
                        tempImage = ImageIO.read(new FileImageInputStream(new File("fire.png")));
                        g.drawImage(tempImage,fire.getX(), fire.getY(),tempImage.getWidth()/5, tempImage.getHeight()/5,this);

                    }
                    catch (IOException ex){
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            if(checkHit(tempImage.getWidth()/5)){
                timer.stop();
                String msg = "You won!\n" +
                        "Time played: " + (timePlayed/1000.0) +
                        "\nNumber of shots: "+ shots;
                JOptionPane.showMessageDialog(this, msg);
                System.exit(0);
            }
        }
    }
   public boolean checkHit(int fireWidth){

        if(!fires.isEmpty()){
            for(Fire fire : fires){
                if(fire.getY()<targetY){
                    if(fire.getX()>targetX && fire.getX()<targetX+targetRadius){
                        return true;
                    }
                    else if(fire.getX()+fireWidth>targetX && fire.getX()+fireWidth<targetX+targetRadius){
                        return true;
                    }
                }

            }

        }
       return false;
    }
    @Override
    public void repaint(){
        super.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timePlayed = timePlayed + 5;
        for(Fire fire: fires){
            fire.setY(fire.getY()-fireSpeedY);
        }
        targetX = targetX + targetSpeedX;
        if(targetX > 760){
            targetSpeedX = -targetSpeedX;
        }
        if(targetX<0){
            targetSpeedX = - targetSpeedX;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyStroke = e.getKeyCode();
        switch (keyStroke){
            case KeyEvent.VK_LEFT:
                if(spaceshipX>=1){
                    spaceshipX = spaceshipX - spaceshipSpeedX;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(spaceshipX<=710){
                    spaceshipX = spaceshipX + spaceshipSpeedX;
                }
                break;
            case KeyEvent.VK_CONTROL:
                fires.add(new Fire(spaceshipX+18,spaceshipY));
                shots++;
                break;

            default:

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
