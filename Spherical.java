package source;
/*  Cartesian to Spherical coordinate class
 *  From code challenge to create a Mandelbulb 09 Oct 2022
*/
import javax.vecmath.Vector3d;

public class Spherical {
    public double r;
    public double theta;
    public double phi;
    public Spherical(double r, double theta, double phi)
    {
        this.r=r;
        this.theta=theta;
        this.phi=phi;
    }
    public Vector3d spherical(double x, double y, double z)
    {
        this.r = (double)(Math.sqrt(x*x+y*y+z*z));
        this.theta = (double)(Math.atan2(Math.sqrt(x*x+y*y),z));
        this.phi = (double)(Math.atan2(y, x));
        return new Vector3d(r, theta,phi);
    }
    public Vector3d spherical(Vector3d vec)
    {
        this.r = (float)(Math.sqrt(vec.x*vec.x+vec.y*vec.y+vec.z*vec.z));
        this.theta = (float)(Math.atan2(Math.sqrt(vec.x*vec.x+vec.y*vec.y),vec.z));
        this.phi = (float)(Math.atan2(vec.y, vec.x));
        return new Vector3d(r, theta,phi);
    }
    public Vector3d cartesian(double r, double theta, double phi)
    {
        Vector3d xyz = new Vector3d();
        xyz.x = (float)(r*Math.sin(theta)*Math.cos(phi));
        xyz.y = (float)(r*Math.sin(theta)*Math.sin(phi));
        xyz.z = (float)(r*Math.cos(theta));
        return xyz;
    }
}
