public class userData {
    int pomoCounter;
    String userName;

    public userData(String name){
        this(name, 0);
    }
    public userData(String name, int numPomos){
        pomoCounter = numPomos;
        userName = name;
    }

    public int getPomoCount(){
        return pomoCounter;
    }
    public void addPomoCount(){
        pomoCounter++;
    }
    public void resetPomoCount(){
        pomoCounter = 0;
    }
}