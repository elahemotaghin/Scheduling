import java.util.Queue;

public class RateMonotonic{
    private  Queue<Food> readyQueue;
    private Food[] allFoods;
    private int currentTime = 0;
    private boolean isArrive = false;
    private  int maximumTime;
    private int restTime = 0;
    private Food missedDeadline;

    public RateMonotonic(Queue<Food> readyQueue, int maximumTime) {
        this.readyQueue = readyQueue;
        this.maximumTime = maximumTime;
        this.allFoods = foodArray(readyQueue);
    }

    public Food[] foodArray(Queue<Food> readyQueue){
        int foodNumber = readyQueue.size();
        Food[] foods = new Food[foodNumber];
        for (int i = 0 ; i < foodNumber ; i++) {
            foods[i] = readyQueue.poll();
        }
        for (int i = 0 ; i < foodNumber ; i++) {
            readyQueue.add(foods[i]);
        }
        return foods;
    }

    public void manageIntervals(){
        for (int i = 0 ; i < allFoods.length ; i++) {
            if(currentTime < maximumTime && currentTime % allFoods[i].getInterval() == 0){
                Food temp = new Food(allFoods[i].getFoodName(), allFoods[i].getTime(), allFoods[i].getDeadline(), allFoods[i].getInterval(),currentTime);
                temp.setJustArrive(true);
                isArrive = true;
                this.readyQueue.add(temp);
            }
        }
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
                missedDeadline = foods[i];
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
        while(currentTime < maximumTime){
            readyQueue.add(readyQueue.poll());
            Food food = readyQueue.poll();
            food.setCooking(true);
            boolean missPredict = isMissPredict(readyQueue);
            boolean deadlineMiss = isDeadlineMiss(readyQueue);
            boolean errorOccured = false;
            while(currentTime < maximumTime && !isArrive && !deadlineMiss && !missPredict && food.getRemainingTime()>0){
                food.updateRemainingTime();
                currentTime++;
                manageIntervals();
                System.out.println(currentTime + " " + food.getFoodName());
                System.out.println("****");
                if(food.getRemainingTime() == 0){
                    break;
                }
                else if(currentTime == food.getDeadline() + food.getArriveTime()){
                    System.out.println("Deadline of " + food.getFoodName() + " is missed in time " + currentTime);
                    errorOccured = true;
                    break;
                }
                missPredict = isMissPredict(readyQueue);
                deadlineMiss = isDeadlineMiss(readyQueue);
            }
            if (errorOccured)
                break;
            while (readyQueue.isEmpty() && currentTime < maximumTime){
                currentTime++;
                restTime++;
                manageIntervals();
            }
            if(food.getRemainingTime() != 0){
                food.setCooking(false);
                readyQueue.add(food);
            }
            if(isArrive){
                readyQueue.add(food);
                isArrive = false;
            }
            if(deadlineMiss){
                System.out.println("Deadline of " + missedDeadline.getFoodName() + " is missed in time " + currentTime);
                break;
            }
            if(missPredict){
                System.out.println("Cooker cant handel!!!");
                break;
            }
        }
        System.out.println("cookers rest time: " + restTime);
    }
}
