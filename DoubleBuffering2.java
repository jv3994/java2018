import java.applet.*; 
import java.awt.event.*; 
import java.awt.*; 

public class DoubleBuffering2 extends Applet {
     Graphics bufferGraphics;
     Image offscreen;
     Dimension dim;

     public void init()  
     { 
    	  setSize(1000,1000);
          dim = getSize();
          offscreen = createImage(dim.width,dim.height);
          bufferGraphics = offscreen.getGraphics(); 
     } 

      public void paint(Graphics g)  
     { 
          bufferGraphics.clearRect(0,0,dim.width,dim.width);
          
          
          
          g.drawImage(offscreen,0,0,this); 
     }

      public void update(Graphics g){ 
           paint(g); 
      } 

 } 
