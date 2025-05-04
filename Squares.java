package source;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
/*
 * 21 Apr 2025  This is not an easy task to find a formula using
 *              squares, cubes, exp(), and log() that is remotely
 *              interesting to look at.
 * 25 Apr 2025  Attempt to reproduce julia is giving wacky output
 *              using Complex.
 * 28 Apr 2025  Using spherical formulas found on the web, this
 *              now displays the bottom of a JuliaBulb! Well, now
 *              it displays a 3D "something" and is generating 
 *              way too many faces, some with bad indices.
 *              vertices: 4569824 (should create 3427368 quads)
 *              faces: 685436 (exactly twice as many quads).
 * 29 Apr 2025  Problem was incrementing fcount after each face        
 *              write operation. Corrected amd working "notmal" now.
 * 01 May 2025  Seems every v,w combo produces the "same" model,
 *              which isn't the same model as ObjViewerEditor VK_W,
 *              so something isn't quite right. Still neat to watch
 *              the morphing :)
*/

public class Squares extends NodeObject
{
    public Squares() {}
     public class FData {
        int x, y, z, w;
        int red, grn, blu, alph;
        boolean isQuad;
        public FData()  {}
        public FData(int findex1, int findex2, int findex3, int findex4, boolean isQuad, int red, int grn, int blu, int alph)
        {
            x=findex1;
            y=findex2;
            z=findex3;
            w=findex4;
            this.isQuad=isQuad;
            this.red=red;
            this.grn=grn;
            this.blu=blu;
            this.alph=alph;
        }
    }
    Node node;
    Graphics g;
    float z;
    int count=0,fcount=0,vcount=0, ncount=8;
    float xscale, yscale,zscale, r, theta, phi, x1, y1, z1;
    float llimitx,ulimitx,llimity,ulimity,llimitz,ulimitz;
    float increm;
    Color ptColor;
    String filename;
    java.io.PrintWriter out;
    ArrayList<FData> face = new ArrayList<>();
   public Squares (float x, float y, float v, float w, Node node)
    {
        super(x,y,v,w,node);
        this.x=x;
        this.y=y;
        this.v=v;
        this.w=w;
        this.node=node;
        g=node.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, node.getWidth(), node.getHeight());
        ptColor=Color.DARK_GRAY;
        this.x=-1.00f; this.y=-1.00f; this.z=-1.00f;
        llimitx=-1.00f; ulimitx=1.00f; llimity=-1.00f; ulimity=1.00f; llimitz=-1.00f; ulimitz=1.00f; 
        xscale=100; yscale=100; zscale=100; increm=0.0100f;
        filename="Node JuliaBulb ("+this.v+","+this.w+") n=8.obj";
        try {
            out = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(new java.io.File("d://Wavefront OBJ//"+filename))));
            out.println("# WaveFront obj file created by the Squares class.");
            out.println(filename);
            out.println("# ------------------------------------------------");
            out.println(" ");
        }
        catch (IOException ex) {ex.printStackTrace(); }
    }

    @Override
    public void tick(Graphics g, float v, float w) {
	/*
        count=0; 
        //float r=(float)Math.sqrt(x*x+y*y+z*z);
        //float theta=(float)Math.acos(z/r);
        //float phi=(float)Math.atan2(y,x);
        //float fx=(float)Math.pow(r, 2);
        //float fy=11f*theta;
        //float fz=11f*phi;
        //float x1=fx*(float)Math.sin(fy)*(float)Math.cos(fz)+node.v;
        //float y1=fx*(float)Math.sin(fy)*(float)Math.sin(fz)+node.w;
        //float z1=fx*(float)Math.cos(fy)+0.15f;
	float r=(float)Math.sqrt(x*x+y*y+z*z);
        float theta=(float)Math.atan2((double)z,(double)Math.sqrt(x*x+y*y));
        float phi=(float)Math.atan2((double)y,(double)x);
        float fx=(float)Math.pow(r, ncount);
        float fy=ncount*theta;
        float fz=ncount*phi;
        float x1=fx*(float)Math.cos(fz)*(float)Math.cos(fy)+this.v;
        float y1=fx*(float)Math.sin(fz)*(float)Math.cos(fy)+this.w;
        float z1=fx*(float)Math.sin(fy)+0.15f;
        while (count <= 40)
        {
            //r=(float)Math.sqrt(x1*x1+y1*y1+z1*z1);
            //theta=(float)Math.acos(z1/r);
            //phi=(float)Math.atan2(y1,x1);
            //fx=(float)Math.pow(r, 2);
            //fy=11f*theta;
            //fz=11f*phi;
            //x1=fx*(float)Math.sin(fy)*(float)Math.cos(fz)+node.v;
            //y1=fx*(float)Math.sin(fy)*(float)Math.sin(fz)+node.w;
            //z1=fx*(float)Math.cos(fx)+0.15f;
            r=(float)Math.sqrt(x1*x1+y1*y1+z1*z1);
            theta=(float)Math.atan2((double)z1,(double)Math.sqrt(x1*x1+y1*y1));
            phi=(float)Math.atan2((double)y1,(double)x1);
            fx=(float)Math.pow(r, ncount);
            fy=ncount*theta;
            fz=ncount*phi;
            x1=fx*(float)Math.cos(fz)*(float)Math.cos(fy)+this.v;
            y1=fx*(float)fx*(float)Math.sin(fz)*(float)Math.cos(fy)+this.w;
            z1=fx*(float)Math.sin(fy)+0.15f;
            count++;
        }
        if(Math.abs(x1)>=1) x1=1; if(Math.abs(y1)>=1) y1=1; if(Math.abs(z1)>=1) z1=1;
        g.setColor(new Color((int)Math.abs(128*x1+127),(int)Math.abs(128*y1+127),(int)Math.abs(128*z1+127)));
        Color ptColor = new Color((int)Math.abs(128*x1+127),(int)Math.abs(128*y1+127),(int)Math.abs(128*z1+127));
        g.drawLine((int)(500+100*x),(int)(350+100*y),(int)(500+100*x),(int)(350+100*y));
        if (ptColor.getRed()>0 && ptColor.getGreen()>0 && ptColor.getBlue()>0)
        {
            out.println("v "+(x*xscale)+" "+(y*yscale)+" "+(z*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+((x+0.017)*xscale)+" "+(y*yscale)+" "+(z*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+((x+0.017)*xscale)+" "+((y+0.017)*yscale)+" "+(z*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+(x*xscale)+" "+((y+0.017)*yscale)+" "+(z*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+(x*xscale)+" "+(y*yscale)+" "+((z+0.017)*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+((x+0.017)*xscale)+" "+(y*yscale)+" "+((z+0.017)*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+((x+0.017)*xscale)+" "+((y+0.017)*yscale)+" "+((z+0.017)*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+(x*xscale)+" "+((y+0.017)*yscale)+" "+((z+0.017)*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            face.add(new FData(vcount-4,vcount-5,vcount-6,vcount-7,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-2,vcount-1,vcount-0,vcount-3,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-1,vcount-2,vcount-6,vcount-5,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-7,vcount-6,vcount-2,vcount-3,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-0,vcount-1,vcount-5,vcount-4,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-3,vcount-0,vcount-4,vcount-7,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
        }
        count=0;
        x+=increm;
        if (x>= 1.00f) {
            x=-1.00f;
            y+=increm;
        }
        if (y>=1.00f) {
            y=-1.00f;
            z+=increm;
        }
        if (z>=1.00f && ptColor.getRed()>0 && ptColor.getGreen()>0 && ptColor.getBlue()>0)
        {
            for (int idx=0; idx<face.size(); idx+=6)
            {
                out.println("f "+face.get(idx+0).x+" "+face.get(idx+0).y+" "+face.get(idx+0).z+" "+face.get(idx+0).w);
                out.println("f "+face.get(idx+1).x+" "+face.get(idx+1).y+" "+face.get(idx+1).z+" "+face.get(idx+1).w);
                out.println("f "+face.get(idx+2).x+" "+face.get(idx+2).y+" "+face.get(idx+2).z+" "+face.get(idx+2).w);
                out.println("f "+face.get(idx+3).x+" "+face.get(idx+3).y+" "+face.get(idx+3).z+" "+face.get(idx+3).w);
                out.println("f "+face.get(idx+4).x+" "+face.get(idx+4).y+" "+face.get(idx+4).z+" "+face.get(idx+4).w);
                out.println("f "+face.get(idx+5).x+" "+face.get(idx+5).y+" "+face.get(idx+5).z+" "+face.get(idx+5).w);
            }
            System.out.println(""+filename+" is finished! Ready to save (VK_F).");
            System.out.println("vertices: "+vcount+"  faces: "+fcount);
            System.out.println("fps: "+node.framesPerSecond);
            out.close();
            node.stop();
        }
    */
        //System.out.println("x: "+x+"   y: "+y+"   z: "+z+"   v: "+v+"   w: "+w);
        ncount=8;
        count=0;
        r=(float)Math.sqrt(x*x+y*y+z*z);
        theta=(float)Math.atan2(z,Math.sqrt(x*x+y*y));
        phi=(float)Math.atan2(y,x);
        double fx=Math.pow(r, ncount);  //ncount
        double fy=ncount*theta;
        double fz=ncount*phi;
        x1=(float)fx*(float)Math.cos(fz)*(float)Math.cos(fy)+v;
        y1=(float)fx*(float)Math.sin(fz)*(float)Math.cos(fy)+w;
        z1=(float)fx*(float)Math.sin(fy)+0.00f;
        while (count <= 40 && (Math.sqrt(x1*x1+y1*y1+z1*z1)<=2.0))
        {
            r=(float)Math.sqrt(x1*x1+y1*y1+z1*z1);
            theta=(float)Math.atan2(z1,(float)Math.sqrt(x1*x1+y1*y1));
            phi=(float)Math.atan2(y1,x1);
            fx=Math.pow(r, ncount);  //ncount
            fy=ncount*theta;
            fz=ncount*phi;
            x1=(float)fx*(float)Math.cos(fz)*(float)Math.cos(fy)+v;
            y1=(float)fx*(float)Math.sin(fz)*(float)Math.cos(fy)+w;
            z1=(float)fx*(float)Math.sin(fy)+0.00f;
            count++;
        }
        float temp0=x1, temp1=y1, temp2=z1;
        if(Math.abs(x1)>=1) x1=1; if(Math.abs(y1)>=1) y1=1; if(Math.abs(z1)>=1) z1=1;//if(y1<=0) y1=0; if(y1>=1) y1=1; if(z1<=0) z1=0; if(z1>=1) z1=1;
        ptColor=new Color((int)Math.abs(255*x1),(int)Math.abs(255*y1),(int)Math.abs(255*z1));
        g.setColor(ptColor);
        x1=temp0; y1=temp1; z1=temp2;
        g.drawLine((int)(500+100*x),(int)(350+100*y),(int)(500+100*x),(int)(350+100*y));
        if (ptColor.getRed()>0 && ptColor.getGreen()>0 && ptColor.getBlue()>0 && (Math.sqrt(x1*x1+y1*y1+z1*z1)<=2.10))
        {
            out.println("v "+(x*xscale)+" "+(y*yscale)+" "+(z*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+((x+0.02)*xscale)+" "+(y*yscale)+" "+(z*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+((x+0.02)*xscale)+" "+((y+0.02)*yscale)+" "+(z*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+(x*xscale)+" "+((y+0.02)*yscale)+" "+(z*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+(x*xscale)+" "+(y*yscale)+" "+((z+0.02)*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+((x+0.02)*xscale)+" "+(y*yscale)+" "+((z+0.02)*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+((x+0.02)*xscale)+" "+((y+0.02)*yscale)+" "+((z+0.02)*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            out.println("v "+(x*xscale)+" "+((y+0.02)*yscale)+" "+((z+0.02)*zscale)+" "+ptColor.getRed()+" "+ptColor.getGreen()+" "+ptColor.getBlue()+" 255"); vcount++;
            // face.add(new FData(vcount-4,vcount-5,vcount-6,vcount-7,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            // face.add(new FData(vcount-2,vcount-1,vcount-0,vcount-3,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            // face.add(new FData(vcount-1,vcount-2,vcount-6,vcount-5,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            // face.add(new FData(vcount-7,vcount-6,vcount-2,vcount-3,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            // face.add(new FData(vcount-0,vcount-1,vcount-5,vcount-4,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            // face.add(new FData(vcount-3,vcount-0,vcount-4,vcount-7,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-7,vcount-6,vcount-5,vcount-4,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-3,vcount-0,vcount-1,vcount-2,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-5,vcount-6,vcount-2,vcount-1,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-3,vcount-2,vcount-6,vcount-7,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-4,vcount-5,vcount-1,vcount-0,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
            face.add(new FData(vcount-7,vcount-4,vcount-0,vcount-3,true,ptColor.getRed(),ptColor.getGreen(),ptColor.getBlue(),255)); fcount++;
        }
        count=0;
        x+=increm;
        if (x>=ulimitx) {
            x=llimitx;
            y+=increm;
        }
        if (y>=ulimity) {
            y=llimity;
            z+=increm;
        }
        if (z>=ulimitz)
        {
            for (int idx=0; idx<face.size(); idx+=6)
            {
                out.println("f "+face.get(idx+0).x+" "+face.get(idx+0).y+" "+face.get(idx+0).z+" "+face.get(idx+0).w);
                out.println("f "+face.get(idx+1).x+" "+face.get(idx+1).y+" "+face.get(idx+1).z+" "+face.get(idx+1).w);
                out.println("f "+face.get(idx+2).x+" "+face.get(idx+2).y+" "+face.get(idx+2).z+" "+face.get(idx+2).w);
                out.println("f "+face.get(idx+3).x+" "+face.get(idx+3).y+" "+face.get(idx+3).z+" "+face.get(idx+3).w);
                out.println("f "+face.get(idx+4).x+" "+face.get(idx+4).y+" "+face.get(idx+4).z+" "+face.get(idx+4).w);
                out.println("f "+face.get(idx+5).x+" "+face.get(idx+5).y+" "+face.get(idx+5).z+" "+face.get(idx+5).w);
            }
            System.out.println(""+filename+" is finished! Ready to save (VK_F).");
            System.out.println("vertices: "+vcount+"  faces: "+fcount);
            System.out.println("fps: "+node.framesPerSecond);
            out.close();
            node.stop();
        }
    }
    public final float getFloat(String msg, float def)
    {
        String str = javax.swing.JOptionPane.showInputDialog(msg);
        if (str.isEmpty()) return def;
        return Float.parseFloat(str);
    }
}