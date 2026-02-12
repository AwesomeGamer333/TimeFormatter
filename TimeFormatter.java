import java.util.Scanner;
public class TimeFormatter
{
    //given shamt, return the new time
    //eg. shiftTime(950, 30) returns 1020
    //precondition: time >= 100
    static int shiftTime(int time, int shamt)
    {
        int minute = time % 100 + shamt;
        int hour = time / 100;//sets the hour
        //System.out.println("Minute = " + minute + ", hour = " + hour);
            hour += minute / 60;// carries over the minute
            minute %= 60;//updates the minute
            //also works with negative numbers below (or equal to) 60
        if (minute < 0) {//negative numbers above -60
            hour -= 1;
            minute += 60;
        }

        hour %= 12;
        if(hour <= 0)
        {
            hour += 12;
        }
        return 100 * hour + minute;
    }

    static int printTimes(String input)
    {
        return printTimes(input, " ", true);
    }
    static int printTimes(String input, String enter, boolean inferPm)
    {
        String[] times = input.split(enter);
        int prevHour = 0;
        int addInterval = 0;//add interval in minutes
        int loopIterations = times.length;//number of times for array to loop
        if(times[times.length-1].contains("+") || times[times.length-1].contains("-"))
        {
            loopIterations--;
            try {addInterval = Integer.parseInt(times[times.length-1].substring(1));}//ADD TRY/CATCH
            catch (Exception NumberFormatException)
            {
                System.out.println("Could parse that last statement. Ending the program.");
                return -1;
            }
            if(times[times.length-1].contains("-"))
            {
                addInterval *= -1;
            }
        }
        for(int i = 0; i < loopIterations; i++)
            {
                //steps:
                //1: determine if pm is manually specified and/or whether any other non-numbers appear
                //2: add 00 if necessary
                //3: calculate the hour and infer whether to use am or pm
                //4: shift the time (+-)
                //5: print the hour and colon
                //6: print the minute
                //7: calculate the previous hour for the next iteration

                boolean isPm = false, manualM = false;//manualM: whether am/pm has been set manually
                if(times[i].substring(times[i].length() -1).equals("p"))//if the last charcater equals p
                {
                    isPm = true;
                    manualM = true;
                    times[i] = times[i].substring(0,times[i].length()-1);
                    //System.out.println("(debug) removing p. New string: " + times[i]);
                } if(times[i].substring(times[i].length() -1).equals("a"))//if the last charcater equals p
                {
                    manualM = true;
                    times[i] = times[i].substring(0,times[i].length()-1);
                    //System.out.println("(debug) removing a. New string: " + times[i]);
                }
                int time = 0;
                try {time = Integer.parseInt(times[i]);}
                catch (Exception NumberFormatException) {
                    System.out.println("Couldn't parse the integer. Ending the program.");
                    return -1;
                }

                if(time < 100)
                {   time *= 100;    }
                //System.out.println("Time: "+ time);
                //shift the time if necessary
                if(addInterval != 0)
                {   time = shiftTime(time, addInterval);  }
                //System.out.println("New time:" + time); 
                int hour = time/100;
                if(!manualM)
                {
                    //infer am or pm
                    if(i > 0)
                    {
                        //prevTime = 0-23
                        //hour = 0-11
                        if(hour == 12)
                        {
                            hour = 0;
                        }
                        if(prevHour - hour >= 7 && prevHour - hour < 19 && inferPm)
                        {
                            isPm = true;
                        }
                        //System.out.println("debug: prevHour = " + prevHour + ", hour = " + hour);
                    }
                    if(hour == 0)
                    {
                        hour = 12;
                    }
                }
                //print hour (and colon)
                System.out.print(hour + ":");

                //print minute
                int minute = time % 100;
                if(minute < 10)
                {
                    System.out.print("0" + minute);
                } else {
                    System.out.print(minute);
                }
                if(isPm)
                {
                    System.out.println(" PM");
                } else {
                    System.out.println(" AM");
                }
                if(hour == 12)
                {
                    hour = 0;
                }
                prevHour = hour;
                if(isPm)
                {
                    prevHour += 12;
                }
            }
        return 0;
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        int returnValue = 0;
        while(returnValue != -1)
        {
            input = scanner.nextLine();
            returnValue = printTimes(input, " ", true);
        }
    }
}