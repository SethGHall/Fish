/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author sehall
 */
public class FishShoal 
{
    public List<Fish> fishes;
    
    public FishShoal()
    {
        fishes = new ArrayList<>();
        
    }

    public synchronized void drawFish(Graphics g)
    {
        for(Fish fish: fishes)
        {
            fish.draw(g);
        }
    }
    
    public synchronized void add(Fish fish)
    {
        fishes.add(fish);
    }
    
    public synchronized void remove(Fish fish)
    {
        fishes.remove(fish);
    }
    
    public synchronized Fish checkIfFishEats(Fish fish)
    {
        Iterator<Fish> iterator = fishes.iterator();
        
        double xCenter = fish.getX();
        double yCenter = fish.getY();
        
        while(iterator.hasNext())
        {
            Fish other = iterator.next();
            
            if(fish.getSize()*0.75 > other.getSize())
            {
                double pxCenter = other.getX();//+(other.getSize()/2);
                double pyCenter = other.getY();//+(other.getSize()/2);

                
                int d = (int)Math.sqrt(Math.pow((pxCenter-xCenter),2)+
                        Math.pow((pyCenter-yCenter),2));
                
//                if(fish.getX() > other.getX()-d && fish.getX() < other.getX()-d &&
//                        fish.getY() > other.getY()-d && fish.getY() < other.getY()-d)
//                    fish.eat(other);
                if((fish.getSize()+other.getSize())/2 >d)
                {
                    return other;// fish.eat(other);
                }
            }  
        }
        return null;
    }
    
    
    
    
    
}
