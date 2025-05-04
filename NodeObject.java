package source;

import java.awt.Graphics;

public class NodeObject{
    protected float x,y,v,w;
    Node node;
    
    public NodeObject() {}
    public NodeObject(float x, float y, float v, float w, Node node) {
        this.x=x;
        this.y=y;
        this.v=v;
        this.w=w;
        this.node=node;
    }
    public void tick(Graphics g, float v, float w)
    {
        tick(g, v, w);
    }
    public void setV(float v){
        this.v=v;
    }
    public void setW(float w){
        this.w=w;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
