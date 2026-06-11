import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class RoundRobin2 {
    //FIXME printing --> different for RR
    public static void main(String[] args) {
        int startTime = 0;
        int endTime = 0;
        int index = 0;
        int numJobs = 5;//FIXME
        int jobsLeft = numJobs;
        int totalTime = 0;
        String temp;
        String[] jobs = new String[numJobs];
        int[] time = new int[numJobs];
        int[] turnaroundTime = new int[numJobs];
        File file = new File("./job5.txt");

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                //
                if (index > 0) { //FIXME all but first instance
                    temp = scan.nextLine(); // Scan end of line to prevent error (\n)FIXME
                }
                jobs[index] = scan.nextLine();
                time[index] = scan.nextInt();
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        index = 0;
        System.out.println("Time:\t| Job:\t   | Remaining:");

        while (jobsLeft > 0) {
            if (time[index] >= 2) {
                endTime += 2;
                time[index] = (time[index] - 2); // Decrease remaining time by 2
                // If job is complete
                if (time[index] == 0) {
                    System.out.println(startTime + "-" + endTime + "\t|  " + jobs[index] + "\t   |  " + time[index] + " [Complete]");
                    turnaroundTime[index] = endTime;
                    totalTime += endTime;
                    jobsLeft --; // Decrease number of jobs left if complete
                }
                else {
                    System.out.println(startTime + "-" + endTime + "\t|  " + jobs[index] + "\t   |  " + time[index]);
                }
                //System.out.println(jobs[index] + "\t  |  " + startTime + "\t   |  " + endTime + "\t|  " + time[index]);
                startTime += 2;
            }
            else {
                if (time[index] == 1) {
                    endTime += 1;
                    time[index] = (time[index] - 1); // Decrease remaining time by 1
                    // If job is complete
                    if (time[index] == 0) {
                        System.out.println(startTime + "-" + endTime + "\t|  " + jobs[index] + "\t   |  " + time[index] + " [Complete]");
                        turnaroundTime[index] = endTime;
                        totalTime += endTime;
                        jobsLeft --; // Decrease number of jobs left if complete
                    }
                    else {
                        System.out.println(startTime + "-" + endTime + "\t|  " + jobs[index] + "\t   |  " + time[index]);
                    }
                    startTime += 1;
                }
            }
            index++;
            // If one iteration is done, loop again
            if (index == numJobs) {
                index = 0;
            }
        }
        System.out.print("\nAverage Turnaround Time: (" + turnaroundTime[0]);
        for (int i = 1; i < numJobs; i++) {
            System.out.print(" + " + turnaroundTime[i]);
        }
        System.out.println(") / " + numJobs + " = " + ((float)totalTime / numJobs) + " ms");

    }
    
}