import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Project1 {
    public static float allTimes = 0; // Holds average times for all 20 trials

    /*
    * Performs the First Come First Served algorithm given an array of jobs,
    *  an array of job lengths, an array to hold job completion times, and
    *  the number of tests (1 or 20).
    */
    public static void FirstComeFirstServed(String[] jobs, int[] time, int[] turnaroundTime, int numTests) {
        int index = 0; // Index for iterating through arrays
        int startTime = 0; // Start time for each job
        int endTime = 0; // End time for each job
        int totalTime = 0; // Total time to complete all jobs
        int numJobs = jobs.length; // Number of jobs to complete (5, 10, or 15)

        // Only print table for single test
        if (numTests == 1) {
            System.out.println("\n- First Come First Served -\n");
            System.out.println("------------------------------------");
            System.out.println("Job: \t| Length: | Start: | End: ");
            System.out.println("------------------------------------");
        }
        
        while (index < jobs.length) {
            endTime += time[index]; // Update current job end time
            totalTime += endTime; // Update total time
            turnaroundTime[index] = endTime; // Add current job end time to array

            // Only print table for single test
            if (numTests == 1) {
                if (startTime > 99) {
                    System.out.println(jobs[index] + "\t|  " + time[index] + "\t  |  " + startTime + "   |  " + endTime);
                }
                else {
                    System.out.println(jobs[index] + "\t|  " + time[index] + "\t  |  " + startTime + "\t   |  " + endTime);
                }
                System.out.println("------------------------------------");
            }
            
            startTime += time[index]; // Update next job start time
            index++; // Increase index to perform next job
        }

        // Only print individual turnaround time for single test
        if (numTests == 1) {
            System.out.print("\nAverage Turnaround Time: (" + turnaroundTime[0]);
            for (int i = 1; i < numJobs; i++) {
                System.out.print(" + " + turnaroundTime[i]);
            }
            System.out.println(") / " + numJobs + " = " + ((float)totalTime / numJobs) + " ms\n");
        }

        // Add average turnaround time for each test to total
        if (numTests == 20) {
            allTimes += (float)totalTime;
        }
    }
    
    /*
    * Performs the Shortest Job First algorithm given an array of jobs,
    *  an array of job lengths, an array to hold job completion times, and
    *  the number of tests (1 or 20).
    */
    public static void ShortestJobFirst(String[] jobs, int[] time, int[] turnaroundTime, int numTests) {
        int index = 0; // Index for iterating through arrays
        int startTime = 0; // Start time for each job
        int endTime = 0; // End time for each job
        int totalTime = 0; // Total time to complete all jobs
        int numJobs = jobs.length; // Number of jobs to complete (5, 10, or 15)
        int[] timeRemaining = new int[time.length]; // New array so original is not affected

        // Copy time array into new array so original is not affected
        System.arraycopy(time, 0, timeRemaining, 0, time.length);

        // Only print table for single test
        if (numTests == 1) {
            System.out.println("\n- Shortest Job First -\n");
            System.out.println("------------------------------------");
            System.out.println("Job: \t| Length: | Start: | End: ");
            System.out.println("------------------------------------");
        }
        
        while (index < jobs.length) {
            int shortestJob = Integer.MAX_VALUE; // Holds shortest job
            int jobIndex = 0; // Holds index of shortest job
            
            // Find next shortest job
            for (int i = 0; i < numJobs; i++) {
                if (timeRemaining[i] < shortestJob) {
                    shortestJob = timeRemaining[i];
                    jobIndex = i;
                }
            }
            endTime += timeRemaining[jobIndex]; // Update current job end time
            totalTime += endTime; // Update total time
            turnaroundTime[jobIndex] = endTime; // Add current job end time to array

            // Only print table for single test
            if (numTests == 1) {
                if (startTime > 99) {
                    System.out.println(jobs[jobIndex] + "\t|  " + timeRemaining[jobIndex] + "  \t  |  " + startTime + "   |  " + endTime);
                }
                else {
                    System.out.println(jobs[jobIndex] + "\t|  " + timeRemaining[jobIndex] + "  \t  |  " + startTime + "\t   |  " + endTime);
                }
                System.out.println("------------------------------------");
            }
            startTime += timeRemaining[jobIndex]; // Update next job start time
            timeRemaining[jobIndex] = Integer.MAX_VALUE; // Make job length max value so job is not run twice
            index++; // Increase index to perform next job
        }

        // Only print individual turnaround time for single test
        if (numTests == 1) {
            System.out.print("\nAverage Turnaround Time: (" + turnaroundTime[0]);
            for (int i = 1; i < numJobs; i++) {
                System.out.print(" + " + turnaroundTime[i]);
            }
            System.out.println(") / " + numJobs + " = " + ((float)totalTime / numJobs) + " ms\n");
        }

        // Add average turnaround time for each test to total
        if (numTests == 20) {
            allTimes += (float)totalTime;
        }
    }
    
    /*
    * Performs the Round Robin algorithm with time slice 2 given an array of jobs,
    *  an array of job lengths, an array to hold job completion times, and
    *  the number of tests (1 or 20).
    */
    public static void RoundRobin2(String[] jobs, int[] time, int[] turnaroundTime, int numTests) {
        int index = 0; // Index for iterating through arrays
        int startTime = 0; // Start time for each job
        int endTime = 0; // End time for each job
        int totalTime = 0; // Total time to complete all jobs
        int numJobs = jobs.length; // Number of jobs to complete (5, 10, or 15)
        int jobsLeft = numJobs; // Number of jobs remaining (-1 for each completion)
        int[] timeRemaining = new int[time.length]; // New array so original is not affected

        // Copy time array into new array so original is not affected
        System.arraycopy(time, 0, timeRemaining, 0, time.length);
        
        // Only print table for single test
        if (numTests == 1) {
            System.out.println("\n- Round Robin (2) -\n");
            System.out.println("Time:\t| Job:\t   | Remaining:");
            System.out.println("-------------------------------");
        }

        while (jobsLeft > 0) {
            // If remaining job length is 2 or more, subtract 2
            if (timeRemaining[index] >= 2) {
                endTime += 2; // Update end time
                timeRemaining[index] = (timeRemaining[index] - 2); // Decrease remaining time by 2
                // If job is complete
                if (timeRemaining[index] == 0) {
                    // Only print table for single test
                    if (numTests == 1) {
                        System.out.println(startTime + "-" + endTime + "\t| " + jobs[index] + "\t   |  " + timeRemaining[index] + " [Complete]");
                    }
                    
                    turnaroundTime[index] = endTime; // Add current job end time to array
                    totalTime += endTime; // Add current job end time to total
                    jobsLeft --; // Decrease number of jobs
                }
                else {
                    // Only print table for single test
                    if (numTests == 1) {
                        System.out.println(startTime + "-" + endTime + "\t| " + jobs[index] + "\t   |  " + timeRemaining[index]);
                    }
                }
                startTime += 2; // Update start time for next job
            }
            // If remaining job length is less than 2, subtract up to 1
            else {
                // Only subtract if remaining time is > 0
                if (timeRemaining[index] == 1) {
                    endTime += 1; // Update current job end time
                    timeRemaining[index] = (timeRemaining[index] - 1); // Decrease remaining time by 1
                    // If job is complete
                    if (timeRemaining[index] == 0) {
                        // Only print table for single test
                        if (numTests == 1) {
                            System.out.println(startTime + "-" + endTime + "\t| " + jobs[index] + "\t   |  " + timeRemaining[index] + " [Complete]");
                        }
                        turnaroundTime[index] = endTime; // Add current job end time to array
                        totalTime += endTime; // Add current job end time to total
                        jobsLeft --; // Decrease number of jobs left
                    }
                    else {
                        // Only print table for single test
                        if (numTests == 1) {
                            System.out.println(startTime + "-" + endTime + "\t| " + jobs[index] + "\t   |  " + timeRemaining[index]);
                        }
                    }
                    startTime += 1; // Update start time for next job
                }
            }
            index++; // Increase index to perform next job
            // If one iteration is done, loop again
            if (index == numJobs) {
                index = 0;
            }
        }
        // Only print individual turnaround time for single test
        if (numTests == 1) {
            System.out.print("\nAverage Turnaround Time: (" + turnaroundTime[0]);
            for (int i = 1; i < numJobs; i++) {
                System.out.print(" + " + turnaroundTime[i]);
            }
            System.out.println(") / " + numJobs + " = " + ((float)totalTime / numJobs) + " ms\n");
        }

        // Add average turnaround time for each test to total
        if (numTests == 20) {
            allTimes += (float)totalTime;
        }
    }

    /*
    * Performs the Round Robin algorithm with time slice 5 given an array of jobs,
    *  an array of job lengths, an array to hold job completion times, and
    *  the number of tests (1 or 20).
    */
    public static void RoundRobin5(String[] jobs, int[] time, int[] turnaroundTime, int numTests) {
        int index = 0; // Index for iterating through arrays
        int startTime = 0; // Start time for each job
        int endTime = 0; // End time for each job
        int totalTime = 0; // Total time to complete all jobs
        int numJobs = jobs.length; // Number of jobs to complete (5, 10, or 15)
        int jobsLeft = numJobs; // Number of jobs remaining (-1 for each completion)
        int[] timeRemaining = new int[time.length]; // New array so original is not affected

        // Copy time array into new array so original is not affected
        System.arraycopy(time, 0, timeRemaining, 0, time.length);
        
        // Only print table for single test
        if (numTests == 1) {
            System.out.println("\n- Round Robin (5) -\n");
            System.out.println("Time:\t| Job:\t   | Remaining:");
            System.out.println("-------------------------------");
        }

        while (jobsLeft > 0) {
            // If remaining job length is 5 or more, subtract 5
            if (timeRemaining[index] >= 5) {
                endTime += 5; // Update end time
                timeRemaining[index] = (timeRemaining[index] - 5); // Decrease remaining time by 5
                // If job is complete
                if (timeRemaining[index] == 0) {
                    // Only print table for single test
                    if (numTests == 1) {
                        System.out.println(startTime + "-" + endTime + "\t| " + jobs[index] + "\t   |  " + timeRemaining[index] + " [Complete]");
                    }
                    turnaroundTime[index] = endTime; // Add current job end time to array
                    totalTime += endTime; // Add current job end time to total time
                    jobsLeft --; // Decrease number of jobs left if complete
                }
                else {
                    // Only print table for single test
                    if (numTests == 1) {
                        System.out.println(startTime + "-" + endTime + "\t| " + jobs[index] + "\t   |  " + timeRemaining[index]);
                    }
                }
                startTime += 5; // Update next job start time
            }
            // If remaining job length is less than 5, subtract up to 4
            else {
                // Only subtract if remaining time is > 0
                if (timeRemaining[index] != 0) {
                    endTime += timeRemaining[index]; // Update current job end time
                    timeRemaining[index] = (timeRemaining[index] - timeRemaining[index]); // Decrease remaining time by time left
                    // If job is complete
                    if (timeRemaining[index] == 0) {
                        // Only print table for single test
                        if (numTests == 1) {
                            System.out.println(startTime + "-" + endTime + "\t| " + jobs[index] + "\t   |  " + timeRemaining[index] + " [Complete]");
                        }
                        turnaroundTime[index] = endTime; // Add current job end time to array
                        totalTime += endTime; // Add current job end time to total time
                        jobsLeft --; // Decrease number of jobs left
                    }
                    else {
                        // Only print table for single test
                        if (numTests == 1) {
                            System.out.println(startTime + "-" + endTime + "\t| " + jobs[index] + "\t   |  " + timeRemaining[index]);
                        }
                    }
                    startTime += 1; // Update next job start time
                }
            }
            index++; // Increase index to perform next job
            // If one iteration is done, loop again
            if (index == numJobs) {
                index = 0;
            }
        }
        // Only print individual turnaround time for single test
        if (numTests == 1) {
            System.out.print("\nAverage Turnaround Time: (" + turnaroundTime[0]);
            for (int i = 1; i < numJobs; i++) {
                System.out.print(" + " + turnaroundTime[i]);
            }
            System.out.println(") / " + numJobs + " = " + ((float)totalTime / numJobs) + " ms\n");
        }
        // Add average turnaround time for each test to total
        if (numTests == 20) {
            allTimes += (float)totalTime;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int index = 0; // Index for file creation
        int numJobs; // Number of jobs to add to file (5, 10, 15)
        int inputType; // Randomly generated or user input file
        int testType; // Single or 20 trial test
        int numTests; // 1 or 20 depending on test type
        String temp; // Collects newline characters when reading input file
        String[] jobs; // Holds job names
        int[] time; // Holds job lengths
        int[] turnaroundTime; // Holds job end times for turnaround time calculation
        float[] averageTimes = new float[4]; // Total time per algorithm for 20 trial turnaround time calculation
        Scanner sc = new Scanner(System.in);
        File file = new File("./job.txt");

        // Ask user for test type
        System.out.println("Choose a test type:\n[1] Randomly Generated\n[2] Input File");
        inputType = sc.nextInt();
        temp = sc.nextLine();
        while (inputType != 1 && inputType != 2) {
            System.out.println("Invalid response. Please enter 1 or 2.");
            inputType = sc.nextInt();
            temp = sc.nextLine();
        }
        
        // For randomly generated file:
        if (inputType == 1) {
            // Ask user for test length
            System.out.println("Choose a test length:\n[5] 5 Jobs\n[10] 10 Jobs\n[15] 15 Jobs");
            numJobs = sc.nextInt();
            temp = sc.nextLine();
            while (numJobs != 5 && numJobs != 10 && numJobs != 15) {
                System.out.println("Invalid response. Please enter 5, 10, or 15.");
                numJobs = sc.nextInt();
                temp = sc.nextLine();
            }
            jobs = new String[numJobs]; // Set job array length to number of jobs
            time = new int[numJobs]; // Set job length array length to number of jobs
            turnaroundTime = new int[numJobs]; // Set turnaround time array length to number of jobs

            // Ask user for test type
            System.out.println("Choose a test type:\n[1] Single Test\n[2] 20 Trial Test");
            testType = sc.nextInt();
            temp = sc.nextLine();
            while (testType != 1 && testType != 2) {
                System.out.println("Invalid response. Please enter 1 or 2.");
                testType = sc.nextInt();
                temp = sc.nextLine();
            }

            // Set number of tests to 1 for single test
            if (testType == 1) {
                numTests = 1;
            }
            // Set number of tests to 20 for 20 trial test
            else {
                numTests = 20;
            }

            // Generate random input file
            for (int n = 0; n < numTests; n++) {
                index = 0; // Reset index for each iteration
                PrintStream newFile = new PrintStream(new File("job.txt")); // Create file
                PrintStream console = System.out; // Save console output location
                System.setOut(newFile); // Print to file
                // Add jobs and job lengths to file
                for (int i = 0; i < numJobs; i++) {
                    System.out.println("Job" + (i + 1));
                    System.out.println((int)(Math.random() * 24) + 2); //Between 2 and 25 inclusive
                }
                System.setOut(console); // Set output back to console
                
                // Read in file input
                try {
                    Scanner scan = new Scanner(file);
                    while(scan.hasNextLine()) {
                        // Store job names and lengths in arrays
                        jobs[index] = scan.nextLine();
                        time[index] = scan.nextInt();

                        // Remove newline character from all but first instance
                        temp = scan.nextLine();
                        index++;
                    }
                    scan.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            // Call scheduling methods
            FirstComeFirstServed(jobs, time, turnaroundTime, numTests);
            averageTimes[0] += allTimes;
            allTimes = 0;

            ShortestJobFirst(jobs, time, turnaroundTime, numTests);
            averageTimes[1] += allTimes;
            allTimes = 0;

            RoundRobin2(jobs, time, turnaroundTime, numTests);
            averageTimes[2] += allTimes;
            allTimes = 0;

            RoundRobin5(jobs, time, turnaroundTime, numTests);
            averageTimes[3] += allTimes;
            allTimes = 0;
            }
        }
        // For user input file:
        else {
            numTests = 1;
            System.out.println("Using input file job.txt, please enter the number of jobs:");
            numJobs = sc.nextInt();
            temp = sc.nextLine();
            jobs = new String[numJobs]; // Set job array length to number of jobs
            time = new int[numJobs]; // Set job length array length to number of jobs
            turnaroundTime = new int[numJobs]; // Set turnaround time array length to number of jobs

            try {
                Scanner scan = new Scanner(file);
                while(scan.hasNextLine()) {
                    // Store job names and lengths in arrays
                    jobs[index] = scan.nextLine();
                    time[index] = scan.nextInt();
                    
                    // Remove newline character from all but first instance
                    temp = scan.nextLine();

                    index++;
                }
                scan.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Call scheduling methods
            FirstComeFirstServed(jobs, time, turnaroundTime, numTests);
            ShortestJobFirst(jobs, time, turnaroundTime, numTests);
            RoundRobin2(jobs, time, turnaroundTime, numTests);
            RoundRobin5(jobs, time, turnaroundTime, numTests);
        }
        
        // Print total average turnaround time if 20 trial test
        if (numTests == 20) {
            System.out.println("\nTotal Average Turnaround Time (20 trials, " + numJobs + " jobs each trial):\n");
            System.out.println("  First Come First Served:\t" + averageTimes[0] + " ms / " + (numJobs * 20) + " jobs = " + (averageTimes[0] / (numJobs * 20)) + " ms\n");
            System.out.println("  Shortest Job First:\t\t" + averageTimes[1] + " ms / " + (numJobs * 20) + " jobs = " + (averageTimes[1] / (numJobs * 20)) + " ms\n");
            System.out.println("  Round Robin (2):\t\t" + averageTimes[2] + " ms / " + (numJobs * 20) + " jobs = " + (averageTimes[2] / (numJobs * 20)) + " ms\n");
            System.out.println("  Round Robin (5):\t\t" + averageTimes[3] + " ms / " + (numJobs * 20) + " jobs = " + (averageTimes[3] / (numJobs * 20)) + " ms");
        }
        sc.close();
    }
}