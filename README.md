# Fractals in Java using Netbeans
Create 3D fractal OBJ models using a timing loop
The Node class contains the timing loop. It is "set" to run at 60 fps (frames per second).
The actual speed varies according to it's load. Lighlty loaded, it may run well over 100fps.
Heavily loaded, it may run 20-30fps. The Node class calls a tick() method at 1000 times per
frame, and the tick method is used to increment x,y, and z within the fractal classes.

The NodeObject class is the parent class for all the fractal classes (and whatever else
the timing loop could be used for). It functions as an abstract class (but isn't abstract)
to specify the method arguments required by the Node class. Hence, all inherited classes
of NodeObject will need to @Override the tick method.

I am curious to see what improvements can be made and what new classes can utilize the timing 
loop.

![Node Julia3D (-0 6,-0 5) n=2  03 May 2025](https://github.com/user-attachments/assets/bda359d6-0a99-434d-82e7-8f187a894e3b)
![Node JuliaBulb (0 777,0 333) n=8  03 May 2025](https://github.com/user-attachments/assets/36c60731-e17e-4176-b485-47422af6f16e)
![Node JuliaBulb (-1 00,1 00) n=8  03 May 2025](https://github.com/user-attachments/assets/687cdbd8-779a-419b-8443-aa20e1c081a2)
![FractalCreator JuliaBulb(0 15,0 9,-0 01) n=8 28 Apr 2025](https://github.com/user-attachments/assets/b8b2a59e-5293-420d-bfe3-5865eae55196)
