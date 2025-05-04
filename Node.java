// * 19 Apr 2025 Modified Game class into Node class and generated Julia
// *             fractal! This could be quite useful as a procedural node
// *             timing.
// * 20 Apr 2025 Finally, after several years of wishing, I created a 
// *             NodeObject class and a (NodeObject) Julia class and with
// *             much head-scratching finally got it to work!!! This is 
// *             huge for procedural programs.  <takes a bow> Running at
// *             15fps minimum. Latest run was 33705 to 48700 tps!!
// ^ 21 Apr 2025 Created a (NodeObject) Squares class that "worked" but
// *             didn't produce but one pattern that was interesting.
// *             Clearly, though, tick(g) can be used to call any class
// *             that extends NodeObject, giving that class updates at
// *             the tps rate. Should create a 3DJulia version :-)
// *
package source;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Node extends Canvas implements Runnable{
    private final int WIDTH=1024, HEIGHT=768;
    int mx,my, count;
    private Thread thread;
    private boolean running  = false;
    public float framesPerSecond;
    MouseAdapter mousy;
    Julia3D julia3d;
//    Squares squares;
    float x=-1.0f, y=-1.0f,v,w;
    String filename = "Node Julia3D(v,w).obj";

    Graphics g;

    public Node() {
        this.setFocusable(true);
        new Window(WIDTH, HEIGHT, "Node Julia3D",this);
        this.addMouseListener(mousy);
        g = this.getGraphics();
        julia3d =new Julia3D(x,y,v,w,this);
        //squares =new Squares(x,y,v,w,this);
        
    }
    public void mousePressed(MouseEvent e) {
        mx = (int)(e.getX() ); //+ camera.getX());
        my = (int)(e.getY() ); //+ camera.getY());
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public synchronized void stop() {
        try {
        thread.join();
        running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        v = getFloat("Enter the constant real value: ",-0.150f);
        w = getFloat("Enter the constant imag value: ",-0.800f);
        System.out.println("v: "+v+"   w: "+w);
        double amountOfTicks=60;
        double ns = 1000000000/amountOfTicks;  //166,666.66666666
        double delta=0;
        long timer = System.currentTimeMillis();
        int frames=0,avg=0; count=0;
        while(running) {
            long now = System.nanoTime();
            delta+=(now - lastTime)/ns;
            lastTime=now;
            while(delta>=1) {
                delta--;
            }
            tick(g);
            frames++;
            avg=0;
            if (System.currentTimeMillis()-timer>1000) {
                timer+=1000;
                //count+=1;
                avg+=frames;
                framesPerSecond=avg/1000;
                frames=0;
               // System.out.println("fps: "+framesPerSecond+"  tick calls per second: "+avg);
            }
        }
        stop();
    }
    public void tick(Graphics g) {
        if (g == null) g = this.getGraphics();
        julia3d.tick(g,v,w);
        //squares.v=this.v;
        //squares.w=this.w;
        //squares.tick(g);
    }

    public static float Clamp(float val, float min, float max) {
        if (val >=max)
            return val=max;
        else if (val<=min)
            return val=min;
        else return val;
    }

    public final float getFloat(String msg, float def)
    {
        String str = javax.swing.JOptionPane.showInputDialog(msg);
        if (str.isEmpty()) return def;
        return Float.parseFloat(str);
    }
    @Override
    public int getWidth()
    {
        return WIDTH;
    }
    @Override
    public int getHeight()
    {
        return HEIGHT;
    }
    
    public static void main(String[] args) {
        new Node();
    }
}
