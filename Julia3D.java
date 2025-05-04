package source;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

public class Julia3D extends NodeObject
{
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
    float x=-1.0f,y=-1.0f,z=-1.0f,v,w;
    int count=0,fcount=0,vcount=0, ncount=2;
    float x1, y1, z1;
    float llimitx,ulimitx,llimity,ulimity,llimitz,ulimitz;
    Color ptColor=Color.blue;
    float xscale=100f, yscale=100f,zscale=100f;
    float increm=0.01f;
    String filename="Node Julia3D(v,w) n=2.obj";
    java.io.PrintWriter out;
    ArrayList<FData> face = new ArrayList<>();
    String[] map = new String[] {"scalar","e0","e1","e10","e2","e20","e12","e120","e3","e30","e31","e130","e23","e230","e123","e1230","e4","e40","e14","e140","e24","e240","e124","e1240","e34","e340","e134","e1340","e234","e2340","e1234","e12340"};
    double[] N = new double[] {0.0,1.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] Nbar = new double[] {0.0,-1.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E1 = new double[] {0.0,0.0,1.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E2 = new double[] {0.0,0.0,0.00,0.0,1.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E3 = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,0.0,0.0,1.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E4 = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E12 = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,1.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E31 = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E23 = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E123 = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E01 = new double[] {0.0,0.0,0.00,-1.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E02 = new double[] {0.0,0.0,0.00,0.0,0.00,-1.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E03 = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,-1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E10 = new double[] {0.0,0.0,0.00,1.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E14 = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] E24 = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.00,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
    double[] temp0 = new double[32], temp1 = new double[32], temp2 = new double[32];
    //double[] temp3 = new double[32], temp4 = new double[32], temp5 = new double[32];
    String label1 = "E1";    // <------------- change for filename ref: temp0
    
    public Julia3D() {}
    public Julia3D (float x, float y, float v, float w, Node node)
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
        this.x=-1.00f; this.y=-1.00f; this.z=-1.00f;
        llimitx=-1.00f; ulimitx=1.00f; llimity=-1.00f; ulimity=1.00f; llimitz=-1.00f; ulimitz=1.00f; 
        xscale=100; yscale=100; zscale=100; increm=0.0100f;
        try {
            out = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(new java.io.File("d://Wavefront OBJ//"+filename))));
            out.println("# WaveFront obj file created by the Julia3D class.");
            out.println(filename);
            out.println("# ------------------------------------------------");
            out.println(" ");
        }
        catch (IOException ex) {ex.printStackTrace(); }
    }

    @Override
    public void tick(Graphics g, float v, float w) {
        //System.out.println("x: "+x+"  y: "+y+"  z: "+z+"  v: "+v+"  w: "+w);
        double[] cmpx = vector(v,w,0.0),d,e;
        d = new double[] {0.0,0.0,0.00,0.0,0.00,0.0,z,0.0,0.00,0.0,-y,0.00,x,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        while (count <= 40 && (d[2]*d[2]+d[4]*d[4]+d[8]*d[8]<5))  //40
        {
            e = d;
            // choices are N, Nbar, E1, E2, E3, E12, E23, E31, E123, E01, E02, E03, E14
            // E1E01=1-E10, E1Nbar=E10+E14, E1N=-E10+E14
            label1="E1";
            temp0 = E1; //change String label1 above to match this choice
            temp1 = times(d,temp0);     // COMPLEX PLANE  dE1 = scalar +/- (val)e12 [+/- (val)e31]
            temp2 = times(temp1,d);     // d on COMPLEX PLANE  dE1d = (val)e1+(val)e2+[(val)e3]
            d = mvecadd(temp2,cmpx);    //(val+v)E1+(val+w)E2
            ptColor = new Color((int)Math.abs(3*count+130),(int)Math.abs(3*count+130),(int)Math.abs(1*count+0));
            g.setColor(ptColor);
            g.drawLine((int)(500+100*x),(int)(350+100*y),(int)(500+100*x),(int)(350+100*y));
            count++;
        }
        if (ptColor.getRed()>0 && ptColor.getGreen()>0 && ptColor.getBlue()>0 && count>7)
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
        if (y>ulimity) {
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
    
    public static float Clamp(float val, float min, float max) {
        if (val >=max)
            return val=max;
        else if (val<=min)
            return val=min;
        else return val;
    }
    public double[] times(double[] dims,double[] rhs) 
    {
        double[] result = new double[32];
        for (int i = 0; i < dims.length; ++i) 
        {
            if (dims[i] != 0.0) 
            {
                for (int j = 0; j < rhs.length; ++j) 
                {
                    if (rhs[j] != 0.0) 
                    {
                        double s = reorderingSign(i, j) * dims[i] * rhs[j];
                        int k = i ^ j;
                        //System.out.println("dims["+i+"]="+dims[i]+"  rhs.dims["+j+"]="+rhs.dims[j]+"  i ^ j="+k+"  result["+k+"]="+result[k]+"   s="+s);
                        result[k] += s;
                    }
                }
            }
        }
        return result;
    }
    private static int bitCount(int i)
    {
        // grade of an element (i.e. index 23 = e1230, returns 4)
        i -= ((i >> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
        i = (i + (i >> 4)) & 0x0F0F0F0F;
        i += (i >> 8);
        i += (i >> 16);
        return i & 0x0000003F;  //0-63
    }

    private static double reorderingSign(int i, int j)
    {
        // signs of element multi[plication by indices (i.e. index 2, index 1 = e1e0 = -e10, returns -1)
        int k = i >> 1;
        int sum = 0;
        while (k != 0) {
            sum += bitCount(k & j);
            k = k >> 1;
        }
        return ((sum & 1) == 0) ? 1.0 : -1.0;
    }
    public double[] reflect (double[] a, double[] r)
    {
        double[] temp = times(a,r);
        double[] temp1 = (times(r,temp));
        return vecnorm(temp1);
    }
    private String[] cga_Map (double[] dims) 
    {
        String[] map = new String[] {"scalar","e0","e1","e10","e2","e20","e12","e120","e3","e30","e31","e130","e23","e230","e123","e1230","e4","e40","e14","e140","e24","e240","e124","e1240","e34","e340","e134","e1340","e234","e2340","e1234","e12340"};
        String []gaEle= new String[32];
        double[] result = dims;
        for (int i=0; i<32; i++) {
            if(result[i]!=0) {
                gaEle[i]=(result[i]+map[i]);
            } else {
                gaEle[i]="0.0";
            }
        }        
        return gaEle;
    }
    public double[] vecnorm (double[] vec)
    {
        double[] result = new double[32];
        result[2]=vec[2]/vecmag(vec);
        result[4]=vec[4]/vecmag(vec);
        result[8]=vec[8]/vecmag(vec);
        return result;
    }
    public double[] mvecadd (double[] mvecA, double[] mvecB) 
    {
        double []result= new double[32];
        for (int i=0; i<32; i++)
        {
            result[i]=mvecA[i]+mvecB[i];
        }        
        return result;
    }
    public double[] mvecsub (double[] mvecA, double[] mvecB) 
    {
        double []result= new double[32];
        for (int i=0; i<32; i++) 
        {
            result[i]=mvecA[i]-mvecB[i];
        }        
        return result;
    }
    public void show(double[] mvec, String title)
    {
        System.out.println(title);
        String[] ampn = cga_Map(mvec);
        for (int index=0; index<32; index++)
        {
            if (!ampn[index].equals("0.0"))
            {
                System.out.print(ampn[index]+" ");
            }
        }
        System.out.println();
    }

    public double vecmag(double[] vec)
    {
        double result = Math.sqrt(vec[2]*vec[2]+vec[4]*vec[4]+vec[8]*vec[8]);
        return result;
    }
    public double[] vector(double a1, double a2, double a3)  //works
    {
        double[] result = new double[32];
        result[2]=a1;
        result[4]=a2;
        result[8]=a3;
        return result;
    }
    public double mvecmag (double[] mvec)
    {
        double magn = 0;
        for (int i=0; i<31; i++)
            if (mvec[i]!=0) magn+=mvec[i]*mvec[i];
        return Math.sqrt(magn);
    }
    public double[] point(double[] x)
    {
        //F(x) = (1/2)(xxn+2x-nbar)
        x = mvecnorm(x);
        double[] result = new double[32];
        double[] N = new double[] {0.0,1.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.000,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        double[] Nbar = new double[] {0.0,-1.0,0.00,0.0,0.00,0.0,0.0,0.0,0.00,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.000,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        double[] temp0 = times(x,x);
        double[] temp1 = times(temp0,N);
        for (int i=0; i<32; i++)
        {
            result[i] = 0.5*(temp1[i]+x[i]+x[i]-Nbar[i]);
        }
        return result;
    }    
    public double[] mvecnorm(double[] mvec)
    {
        double result[]=new double[32];
        double magn = mvecmag(mvec);
        for(int i=0; i<32; i++)
        {
            result[i]=mvec[i]/magn;
        }
        return result;
    }
    
    public final float getFloat(String msg, float def)
    {
        String str = javax.swing.JOptionPane.showInputDialog(msg);
        if (str.isEmpty()) return def;
        return Float.parseFloat(str);
    }
}