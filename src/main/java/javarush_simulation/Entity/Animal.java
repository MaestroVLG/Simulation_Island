package javarush_simulation.Entity;

public abstract class Animal {

    protected int speed = 1;
    protected double weight;
    protected double currentSatiety;
    protected double maxSatiety;
    protected boolean isAlive = true;
    protected int maxDayLive;
    protected volatile Location = currentLocation;




    public  Animal(double weight, double maxSatiety){
        this.weight = weight;
        this.maxSatiety = maxSatiety;
        this.currentSatiety = maxSatiety;

    }


    public abstract void eat(Location location){

    }

    public void reproduce(){


    }

    public void move() {


    }



}
