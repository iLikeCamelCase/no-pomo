import java.io.Console;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.server.UID;
import java.io.File;
import javax.sound.sampled.*;
import java.util.ArrayList;



/**
 * NoPomo --- program to run a pomodoro timer in a terminal
 * @author iLikeCamelCase
 */
public class NoPomo {
    public static void main(String[] args)throws IOException, InterruptedException,
                                                 MalformedURLException, UnsupportedAudioFileException, 
                                                 LineUnavailableException{

        Console console = System.console();

        ArrayList<String[]> userDataList = new ArrayList<String[]>(persistentStorage.readUserData());

        userData user = new userData("temp user", 0);

        if (console == null) {
            System.out.println("Console is not available to current process");
            return;
        }
        clearScreen();

        // add login here
        
        
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
        playSound();

    }

    /** 
     * Runs 5 minute coffee break
     */
    public static void runCoffeeBreak(int pomos){
        runTimer(5,0,pomos);
        playSound();

    }

    /**
     * Runs 30 minute lunch break
     */
    public static void runLunchBreak(int pomos){
        runTimer(30,0,pomos);
        playSound();
    }


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
            File soundFile = new File("./data/Alarm10.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());  
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audioIn);
            soundClip.start();
        }
        catch(Exception MalformedURLException){

        }
    }
    /*
     * @param user name of user
     * @param userDataList list of userdata to check for user in
     * @return userData() or null if not found
     */
    public static userData returnUser(String user, ArrayList<String[]> userDataList){
        for (int i = 0; i < userDataList.size(); i++){
            if (user.trim() == userDataList.get(i)[0]){
                String tempList[] = userDataList.get(i);
                return new userData(tempList[0].trim(), Integer.parseInt(tempList[1].trim()));
            }
        }
        return null;
    }

        
}
