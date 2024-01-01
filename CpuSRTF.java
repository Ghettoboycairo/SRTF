import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Process {
    private int processNumber;
    private int burstTime;
    private int arrivalTime;
    private int priority;

    public Process(int processNumber, int burstTime, int arrivalTime, int priority) {
        this.processNumber = processNumber;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }

    public int getProcessNumber() {
        return processNumber;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int remainingBurstTime) {
        this.burstTime = remainingBurstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Process{" +
                "processNumber=" + processNumber +
                ", remainingBurstTime=" + burstTime +
                ", arrivalTime=" + arrivalTime +
                ", priority=" + priority +
                '}';
    }
}

public class SRTF {
    private int numberOfProcesses;
    private List<Process> processesList;

    public void collectData(){
        Scanner scan = new Scanner(System.in);

        System.out.println("how many processes do you have? ");
        numberOfProcesses = scan.nextInt();        
        processesList = new ArrayList<>();

        for(int i=0; i<numberOfProcesses; i++){
            System.out.println("enter the burst time of process number "+i+1);
            int brstTime = scan.nextInt();

            System.out.println("enter the arrival time of process number "+i+1);
            int arrTime = scan.nextInt(); 
    
            processesList.add(new Process(i, brstTime, arrTime, numberOfProcesses));
        }
    }

    public void simulateSRTF(){
        int systemTime = 0;
        boolean run = true;
        Collections.sort(processesList, Comparator.comparingInt(Process::getArrivalTime));
        Process shortestProcess = processesList.get(0);
        System.out.println("earliest process in the queue is: "+shortestProcess+" with arrival time of: "+shortestProcess.getArrivalTime());
        while(run){
            System.out.println("system time is now at: "+systemTime);
            
            for(Process p : processesList){  //checks if there is an available process with a shortest burst time than the shortest process burst time
                if(p.getArrivalTime() >= systemTime && p.getBurstTime() < shortestProcess.getBurstTime() && p.getBurstTime() > 0){
                    shortestProcess = p;
                    System.out.println("the process with the remaining shortest burst time is now process: "+p.getProcessNumber()+" with a burst time of: "+p.getBurstTime());
                }
            }

            if(shortestProcess.getBurstTime() > 0 && shortestProcess.getArrivalTime() >= systemTime){   //checks if the shortest process that is currently availabel still wants to use the cpu if so it decrements the burst time by one.
                shortestProcess.setBurstTime(shortestProcess.getBurstTime()-1);
                if(shortestProcess.getBurstTime()==0){
                    processesList.remove(shortestProcess.getProcessNumber());
                }
                System.out.println("process: "+shortestProcess.getProcessNumber()+" remaining burst time is: "+shortestProcess.getBurstTime());
            }

            for(Process p : processesList){   //to check if there is a process that still isn't done running.
                if(p.getBurstTime()==0){
                    run = false;
                }else{
                    run = true;
                    break;
                }
            }

            Thread.sleep(1000);
            systemTime++;
        }
    
    }

    public static void main(String[] args) {
        SRTF srtf = new SRTF();
        srtf.collectData();
        srtf.simulateSRTF();
    }
}
