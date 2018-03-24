import java.applet.*; 
import java.awt.event.*; 
import java.awt.*; 

public class DoubleBuffering extends Applet implements MouseMotionListener 
{ 
     // The object we will use to write with instead of the standard screen graphics 
     Graphics bufferGraphics; 
     // The image that will contain everything that has been drawn on 
     // bufferGraphics. 
     Image offscreen; 
     // To get the width and height of the applet. 
     Dimension dim; 
     int curX, curY; 

     public void init()  
     { 
    	 setSize(1000,1000);
          // We'll ask the width and height by this 
          dim = getSize(); 
          // We'll redraw the applet eacht time the mouse has moved. 
          addMouseMotionListener(this); 
          setBackground(Color.black); 
          // Create an offscreen image to draw on 
          // Make it the size of the applet, this is just perfect larger 
          // size could slow it down unnecessary. 
          offscreen = createImage(dim.width,dim.height); 
          // by doing this everything that is drawn by bufferGraphics 
          // will be written on the offscreen image. 
          bufferGraphics = offscreen.getGraphics(); 
     } 

      public void paint(Graphics g)  
     { 
          // Wipe off everything that has been drawn before 
          // Otherwise previous drawings would also be displayed. 
          bufferGraphics.clearRect(0,0,dim.width,dim.width); 
          bufferGraphics.setColor(Color.red); 
          bufferGraphics.drawString("Bad Double-buffered",10,10); 
          // draw the rect at the current mouse position 
          // to the offscreen image 
          bufferGraphics.fillRect(curX-50,curY-50,100,100); 
          // draw the offscreen image to the screen like a normal image. 
          // Since offscreen is the screen width we start at 0,0. 
          g.drawImage(offscreen,0,0,this); 
     } 

     // Always required for good double-buffering. 
     // This will cause the applet not to first wipe off 
     // previous drawings but to immediately repaint. 
     // the wiping off also causes flickering. 
     // Update is called automatically when repaint() is called. 

      public void update(Graphics g) 
      { 
           paint(g); 
      } 
  

     // Save the current mouse position to paint a rectangle there. 
     // and request a repaint() 
     public void mouseMoved(MouseEvent evt)  
     { 
          curX = evt.getX(); 
          curY = evt.getY(); 
          repaint(); 
     } 
  

     // The necessary methods. 
     public void mouseDragged(MouseEvent evt)  
     { 
     } 

 } 
