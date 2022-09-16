import java.io.Console;
import java.util.Date;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.File;
import javax.sound.sampled.*;

/**
 * NoPomo --- program to run a pomodoro timer in a terminal
 * @author iLikeCamelCase
 */
public class NoPomo {
    public static void main(String[] args)throws IOException, InterruptedException,
                                                 MalformedURLException, UnsupportedAudioFileException, 
                                                 LineUnavailableException{

        Console console = System.console();
        userData user = new userData();
        if (console == null) {
            System.out.println("Console is not available to current process");
            return;
        }
        clearScreen();
        System.out.println(" Pomodoro Timer \n \n Ready to get to work? Y/N\n");

        String response = console.readLine();

        if (checkString(response, "y")){ // run pomodoro session
            
            clearScreen();
            while (true){
            // check to begin work timer (25 mins) or exit
            response = console.readLine("Begin Pomodoro? Y/N \n \n");
            if (checkString(response, "y")){
                clearScreen();
                runWorkPeriod(user.getPomoCount());
                user.addPomoCount(); 
                clearScreen();
                System.out.println(user.getPomoCount() + " Pomodoro completed.\n");
            }

            // check to begin coffee break (if pomo counter < 4) or exit
            if (user.getPomoCount() < 4){
                response = console.readLine("Begin Coffee Break? Y/N\n\n");
                if (checkString(response, "y")){
                    // pause option
                    clearScreen();
                    runCoffeeBreak(user.getPomoCount());
                    clearScreen();
                    System.out.println("Your break is over. Back to work... \n");
                        // continue option
                        // reset option
                        // exit option
                }
            }

            // if pomo counter 4 check to begin lunch break or exit
            else if (user.getPomoCount() == 4){
                System.out.println("You have completed a set. \n");
                response = console.readLine("Begin Lunch Break? Y/N\n\n");
                if (checkString(response, "y")){
                    clearScreen();
                    runLunchBreak(user.getPomoCount());
                    clearScreen();
                    System.out.println("Your lunch break is over. Back to work...");

                    user.resetPomoCount();
                }
            }
                
            // loop back to start
            }

        }

    }
    /**
     * 
     * @param n String #1
     * @param comparee String #2
     * @return True if String #1 and String #2 are same (discounting whitespace and capitilization)
     *         False if otherwise.
     */
    public static Boolean checkString(String n, String comparee){
        
        String toCheck = n.toLowerCase().trim();

        if (toCheck.equals(comparee.toLowerCase().trim())){
            return true;
        }
        return false;
    }


    /**
     * Runs 25 minute workperiod
     */
    public static void runWorkPeriod(int pomos){
        runTimer(25, 0, pomos);

    }

    /** 
     * Runs 5 minute coffee break
     */
    public static void runCoffeeBreak(int pomos){
        runTimer(5,0,pomos);

    }

    /**
     * Runs 30 minute lunch break
     */
    public static void runLunchBreak(int pomos){
        runTimer(30,0,pomos);
    }

        /**
         * Starts and runs a timer that lasts mins:secs, displays on terminal
         * @param mins 
         * @param secs
         */
        /*
    public static void runTimer(int mins, int secs, int pomos) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long minutes = 0;
        int seconds = 0;
        int lastSecond = 99;
        long goal = ((mins * 60) + secs) * 1000;

        System.out.println();
        while (elapsedTime < goal) {
            elapsedTime = (new Date()).getTime() - startTime;

            minutes = (elapsedTime / 1000) / 60;
            seconds = (int) ((elapsedTime / 1000) % 60);

            if (seconds != lastSecond) {
                System.out.print("  " + minutes + ":" + seconds + "\r");
                lastSecond = seconds;
            }
        }
        playSound();
        }
        */

        public static void runTimer(int mins, int secs, int pomos){
            int goal = (mins * 60) + secs;
            mins = 0;
            secs = 0;
            String strMins = "";
            String strSecs = "";

            for (int i = 0; i < goal; i++){
                if (mins < 10){
                    strMins = "0" + String.valueOf(mins);
                }
                else {strMins = String.valueOf(mins);}

                if (secs < 10){
                    strSecs = "0" + String.valueOf(secs);
                }
                else {strSecs = String.valueOf(secs);}
                System.out.print("  Elapsed: "+strMins+":"+strSecs+"\r");
                secs++;
                if (secs == 60){
                    secs = 0;
                    mins++;
                }
                try{Thread.sleep(1000);}
                catch(Exception interrupException){
                    System.out.println("Something went wrong...  :/");
                }
            }
        }

    /**
     * Clears terminal screen
     * @throws IOException
     * @throws InterruptedException
     */
    public static void clearScreen()throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();  
        
        
        
    }

    public static void playSound(){
        try{
            File soundFile = new File("./media/Alarm10.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());  
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audioIn);
            soundClip.start();
        }
        catch(Exception MalformedURLException){

        }
    }
}