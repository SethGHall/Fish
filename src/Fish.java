/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sehall
 */
public class Fish implements Runnable{
    private double fishSize;
    private double xPos, yPos;
    private double velX, velY;
    public static int PANEL_W = 800;
    public static int PANEL_H = 800;
    public Random generator;
    private Color[] colour;
    private FishShoal fishShoal;
    private boolean isAlive;
  
    
    public Fish(FishShoal fishes)
    {   generator = new Random();
        this.fishShoal = fishes;
        colour = new Color[3];
        colour[0] = new Color(generator.nextFloat(),generator.nextFloat(),generator.nextFloat());
        colour[1] = new Color(generator.nextFloat(),generator.nextFloat(),generator.nextFloat());
        colour[2] = new Color(generator.nextFloat(),generator.nextFloat(),generator.nextFloat());
       
        xPos = PANEL_W/2;
        yPos = PANEL_H/2;
        
        fishSize = generator.nextInt(20)+5;
        
        velX = generator.nextInt(4)-2;
        velY = generator.nextInt(4)-2;
        
        isAlive = true;
    }
    
    
    @Override
    public void run() {
        
        double maxV = 5;
        double minV = -5;
        int count = 0;
        int threshold = generator.nextInt(25)+5;
        
        while(isAlive)
        {
            count++;
            if(count >=threshold)
            {    count= 0;
                 threshold = generator.nextInt(25)+5;
                 velX += (generator.nextFloat()*2.0) - 1.0;
                 velY += (generator.nextFloat()*2.0) - 1.0;
                 
                 if(velX > maxV)
                     velX = velX;
                 if(velX < minV)
                     velX = minV;
                 if(velY > maxV)
                     velY = maxV;
                 if(velY < minV)
                     velY = minV;
                 
            } 
            
            if((int)xPos <= 1.0 || (int)xPos >= PANEL_W-1)
                velX = -velX;
            if((int)yPos <= 1.0 || (int)yPos >= PANEL_H-1) 
                velY = -velY;   
            xPos += velX;
            yPos += velY;   
            
            Fish fish= fishShoal.checkIfFishEats(this);
            if(fish != null)
                eat(fish);
            
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Fish.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fishShoal.remove(this);
       
    }
    public synchronized void eat(Fish fish)
    {
        fish.isAlive = false;
        fishSize += fish.getSize()/5.0;
    }
    public double getSize()
    {
        return fishSize;
    }

    public double getX()
    {
        return xPos;
    }
    public double getY()
    {
        return yPos;
    }
    public void draw(Graphics g)
    { Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(3));
      double speed = Math.sqrt((velX*velX)+(velY*velY));
      if (speed < 0.01f)
         speed = 0.01f;
      // calculate direction of boid normalised to length BOID_LENGTH/2
      double vx_dir = fishSize*velX/(2.0f*speed);
      double vy_dir = fishSize*velY/(2.0f*speed);
      g.setColor(colour[0]);
      g.drawLine((int)(xPos-2.0f*vx_dir),(int)(yPos-2.0f*vy_dir),(int)xPos,(int)yPos);
      g.setColor(colour[1]);
      g.drawLine((int)xPos,(int)yPos,(int)(xPos-vx_dir+vy_dir),(int)(yPos-vx_dir-vy_dir));
      g.setColor(colour[2]);
      g.drawLine((int)xPos,(int)yPos,(int)(xPos-vx_dir-vy_dir),(int)(yPos+vx_dir-vy_dir));
    }
}
