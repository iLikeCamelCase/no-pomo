public class userData {
    int pomoCounter;

    public userData(){
        pomoCounter =0;
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