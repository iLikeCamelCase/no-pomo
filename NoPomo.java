import java.io.Console;
import java.util.Date;
import java.io.IOException;

/**
 * NoPomo --- program to run a pomodoro timer in a terminal
 * @author iLikeCamelCase
 */
public class NoPomo {
    public static void main(String[] args)throws IOException, InterruptedException{

        Console console = System.console();

        if (console == null) {
            System.out.println("Console is not available to current process");
            return;
        }
        clearScreen();
        System.out.println(" Pomodoro Timer \n \n Ready to get to work? Y/N\n");

        String response = console.readLine();

        if (checkString(response, "y")){ // run pomodoro session
            int pomoCounter = 0;
            clearScreen();
            while (true){
            // check to begin work timer (25 mins) or exit
            response = console.readLine("Begin Pomodoro? Y/N \n \n");
            if (checkString(response, "y")){
                clearScreen();
                runWorkPeriod();
                pomoCounter++; 
                clearScreen();
                System.out.println(pomoCounter + " Pomodoro completed.\n");
            }

            // check to begin coffee break (if pomo counter < 4) or exit
            if (pomoCounter < 4){
                response = console.readLine("Begin Coffee Break? Y/N\n\n");
                if (checkString(response, "y")){
                    // pause option
                    clearScreen();
                    runCoffeeBreak();
                    clearScreen();
                    System.out.println("Your break is over. Back to work... \n");
                        // continue option
                        // reset option
                        // exit option
                }
            }

            // if pomo counter 4 check to begin lunch break or exit
            else if (pomoCounter == 4){
                System.out.println("You have completed a set. \n");
                response = console.readLine("Begin Lunch Break? Y/N\n\n");
                if (checkString(response, "y")){
                    clearScreen();
                    runLunchBreak();
                    clearScreen();
                    System.out.println("Your lunch break is over. Back to work...");

                    pomoCounter = 0;
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
    public static void runWorkPeriod(){
        runTimer(25, 0);

    }

    /** 
     * Runs 5 minute coffee break
     */
    public static void runCoffeeBreak(){
        runTimer(5,0);

    }

    /**
     * Runs 30 minute lunch break
     */
    public static void runLunchBreak(){
        runTimer(30,0);
    }

        /**
         * Starts and runs a timer that lasts mins:secs, displays on terminal
         * @param mins 
         * @param secs
         */
        public static void runTimer(int mins, int secs){
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long minutes = 0;
        int seconds = 0;
        int lastSecond = 99;
        long goal = ((mins * 60) + secs) * 1000;


        while (elapsedTime < goal){
            elapsedTime = (new Date()).getTime() - startTime;

            minutes = (elapsedTime / 1000) / 60;
            seconds = (int)((elapsedTime / 1000) % 60);
            

            if (seconds != lastSecond){
                System.out.println(minutes+":"+seconds);
                lastSecond = seconds;
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

}