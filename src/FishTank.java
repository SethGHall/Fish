/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author sehall
 */
public class FishTank extends JPanel implements ActionListener{
    
    private Timer timer;
    private JButton addButton;
    private DrawPanel drawPanel;
    private FishShoal shoal;
    
    
    
    public FishTank()
    {   super(new BorderLayout());
        try
        {  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){}

        
        timer = new Timer(20,this);
        shoal = new FishShoal();
        
        addButton = new JButton("Add Fish");
        addButton.addActionListener(this);
        drawPanel = new DrawPanel();
        
        add(drawPanel,BorderLayout.CENTER);
        add(addButton,BorderLayout.SOUTH);
        
       
        
        timer.start();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if(source == addButton)
        {
            Fish fish = new Fish(shoal);
            Thread t = new Thread(fish);
            t.start();
            shoal.add(fish);
        
        }
        drawPanel.repaint();
    }
    
    private class DrawPanel extends JPanel
    {
        public DrawPanel()
        {   super();
            setBackground(Color.white);
            setPreferredSize(new Dimension(Fish.PANEL_W,Fish.PANEL_H));
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Fish.PANEL_H = getHeight();
            Fish.PANEL_W = getWidth();
            shoal.drawFish(g);
            
        }
    }
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Fish Bowl");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new FishTank());
        frame.pack();                                      //pack frame
        frame.setVisible(true);                                      //show the frame
    }

}
