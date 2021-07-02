import java.util.PriorityQueue;
import java.util.Queue;

public class RateMonotonic{
    private  Queue<Food> readyQueue = new PriorityQueue<>();
    private int currentTime = 0;
    private int foodNumber;
    private boolean isArrive = false;
    private  int maximumTime;

    public RateMonotonic(Queue<Food> readyQueue, int maximumTime) {
        this.readyQueue = readyQueue;
        this.maximumTime = maximumTime;
        foodNumber = readyQueue.size();
    }

    public void addFood(Food food){
        isArrive = true;
        this.readyQueue.add(food);
    }

    public boolean isMissPredict(Queue<Food> readyQueue){
        int foodNumber =readyQueue.size();
        boolean miss = false;
        int count = 0;
        Food[] foods = new Food[foodNumber];
        for (int i = 0 ; i < foodNumber ; i++) {
            foods[i] = readyQueue.poll();
            count++;
            if (currentTime + foods[i].getRemainingTime() > foods[i].getDeadline() + foods[i].getArriveTime()) {
                miss = true;
                break;
            }
        }
        for (int i = 0 ; i < count ; i++) {
            readyQueue.add(foods[i]);
        }
        return miss;
    }

    public boolean isDeadlineMiss(Queue<Food> readyQueue) {
        int foodNumber =readyQueue.size();
        boolean miss = false;
        int count = 0;
        Food[] foods = new Food[foodNumber];
        for (int i = 0 ; i < foodNumber ; i++) {
            foods[i] = readyQueue.poll();
            count++;
            if (foods[i].getRemainingTime() > 0  && currentTime == foods[i].getDeadline() + foods[i].getArriveTime()) {
                miss = true;
                break;
            }
        }
        for (int i = 0 ; i < count ; i++) {
            readyQueue.add(foods[i]);
        }
        return miss;
    }

    public void scheduling(){
        System.out.println("***start scheduling***");
        while(!readyQueue.isEmpty()){
            readyQueue.add(readyQueue.poll());
            Food food = readyQueue.poll();
            food.setCooking(true);
            boolean missPredict = isMissPredict(readyQueue);
            boolean deadlineMiss = isDeadlineMiss(readyQueue);

            while(currentTime < maximumTime && !isArrive && !deadlineMiss && !missPredict){
                currentTime++;
                food.updateRemainingTime();
                if(food.getRemainingTime() == 0){
                    System.out.println("Food is ready!!");
                    food.printFood();
                    System.out.println("****");
                    break;
                }
                missPredict = isMissPredict(readyQueue);
                deadlineMiss = isDeadlineMiss(readyQueue);
            }
            if(food.getRemainingTime() != 0){
                readyQueue.add(food);
            }
            else if(deadlineMiss){
                System.out.println("Deadline of " + food.getFoodName() + "is missed in" + currentTime);
            }
            else if(missPredict){
                System.out.println("Cooker cant handel!!!");
            }
        }
    }
}
