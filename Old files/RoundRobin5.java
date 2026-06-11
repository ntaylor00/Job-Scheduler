import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class RoundRobin5 {
    //FIXME printing --> different for RR
    public static void main(String[] args) {
        int startTime = 0;
        int endTime = 0;
        int index = 0;
        int numJobs;//FIXME
        int jobsLeft;
        int totalTime = 0;
        String temp;
        String[] jobs;
        int[] time;
        int[] turnaroundTime;
        Scanner sc = new Scanner(System.in);
        File file = new File("./job.txt");

        System.out.println("Choose a test length:\n[5] 5 Jobs\n[10] 10 Jobs\n[15] 15 Jobs");
        numJobs = sc.nextInt();
        while (numJobs != 5 && numJobs != 10 && numJobs != 15) {
            System.out.println("Invalid response. Please enter 5, 10, or 15.");
            numJobs = sc.nextInt();
        }
        jobsLeft = numJobs;
        jobs = new String[numJobs];
        time = new int[numJobs];
        turnaroundTime = new int[numJobs];

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
            if (time[index] >= 5) {
                endTime += 5;
                time[index] = (time[index] - 5); // Decrease remaining time by 2
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
                startTime += 5;
            }
            else {
                if (time[index] != 0) {
                    endTime += time[index];
                    time[index] = (time[index] - time[index]); // Decrease remaining time by 1
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

        sc.close();
    }
    
}