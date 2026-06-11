import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ShortestJobFirst {
    public static int allTimes = 0;
    public static void shortestJobFirst(String[] jobs, int[] time, int[] turnaroundTime, int numTests) {
        int index = 0;
        int startTime = 0;
        int endTime = 0;
        int totalTime = 0;
        int numJobs = jobs.length;

        System.out.println("\n- Shortest Job First -\n");

        // Only print table for single test
        if (numTests == 1) {
            System.out.println("------------------------------------");
            System.out.println("Job: \t| Length: | Start: | End: ");
            System.out.println("------------------------------------");
        }
        
        while (index < jobs.length) {
            int shortestJob = Integer.MAX_VALUE;
            int jobIndex = 0;
            // Find next shortest job
            for (int i = 0; i < numJobs; i++) {
                if (time[i] < shortestJob) {
                    shortestJob = time[i];
                    jobIndex = i;
                }
            }
            endTime += time[jobIndex];
            totalTime += endTime;
            turnaroundTime[jobIndex] = endTime;

            // Only print table for single test
            if (numTests == 1) {
                System.out.println(jobs[jobIndex] + "\t|  " + time[jobIndex] + "\t  |  " + startTime + "\t   |  " + endTime);
                System.out.println("------------------------------------");
            }
            startTime += time[jobIndex];
            time[jobIndex] = Integer.MAX_VALUE; // Make max value so job is not run twice
            index++;
        }
        System.out.print("\nAverage Turnaround Time: (" + turnaroundTime[0]);
        for (int i = 1; i < numJobs; i++) {
            System.out.print(" + " + turnaroundTime[i]);
        }
        System.out.println(") / " + numJobs + " = " + ((float)totalTime / numJobs) + " ms\n");

        // Add average turnaround time for each test to total
        if (numTests == 20) {
            allTimes += ((float)totalTime / numJobs);
        }
    }
    public static void main(String[] args) {
        int index = 0;
        int numJobs = 5;//FIXME
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
                //System.out.println(jobs[index]);
                time[index] = scan.nextInt();
                //System.out.println(time[index]);
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Sort array smallest to largest
        /*for (int i = 0; i < numJobs; i++) {
            for (int j = 0; j < numJobs; j++) {
                // Swap positions if < FIXME
                if (time[j] < time[i]) {
                    temp = jobs[i];
                    jobs[i] = jobs[j];
                    jobs[j] = temp;

                    int tempNum = time[i];
                    time[i] = time[j];
                    time[j] = tempNum;
                }
            }
        }*/

        

    }
    
}


/*
(1) There are no more than 30 jobs in the input file (job.txt).
(2) Processes arrive in the order they are read from the file for FCFS, RR-2 and RR-5.
(3) All jobs arrive at time 0.
(4) FCFS uses the order of the jobs, Job1, Job2, Job3, …

Job file format:
Job1
7
Job2
18
Job3
10
Job4
4
Job5
12
*/
