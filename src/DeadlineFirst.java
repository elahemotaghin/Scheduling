import java.util.*;

public class DeadlineFirst {
    private Queue<Food> readyQueue;
    private Food[] allFoods;
    private int currentTime = 0;
    private boolean isArrive = false;
    private  int maximumTime;
    private int restTime = 0;
    private Food missedDeadlineFood;
    private Food missPredictedFood;
    private HashMap<String, Integer> waitingTimes = new HashMap<>();

    public DeadlineFirst(Queue<Food> readyQueue, int maximumTime) {
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
        readyQueue.addAll(Arrays.asList(foods).subList(0, foodNumber));
        return foods;
    }

    public void updateWaitingTimes(Queue<Food> readyQueue, String currentFoodName){
        Food[] foods = new Food[readyQueue.size()];
        LinkedList<String> foodNames = new LinkedList<>();
        for (int i = 0 ; i < foods.length ; i++) {
            foods[i] = readyQueue.poll();
            if(!foodNames.contains(foods[i].getFoodName()) && !currentFoodName.equals(foods[i].getFoodName())){
                if(waitingTimes.containsKey(foods[i].getFoodName())){
                    waitingTimes.replace(foods[i].getFoodName(), waitingTimes.get(foods[i].getFoodName()), waitingTimes.get(foods[i].getFoodName())+1);
                    foodNames.add(foods[i].getFoodName());
                }
                else {
                    waitingTimes.put(foods[i].getFoodName(), 1);
                    foodNames.add(foods[i].getFoodName());
                }
            }
        }
        readyQueue.addAll(Arrays.asList(foods));
    }

    public void manageIntervals(){
        for (Food allFood : allFoods) {
            if (currentTime < maximumTime && currentTime % allFood.getInterval() == 0) {
                Food temp = new Food(allFood.getFoodName(), allFood.getTime(), allFood.getDeadline(), allFood.getInterval(), currentTime);
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
                missPredictedFood = foods[i];
                miss = true;
                break;
            }
        }
        readyQueue.addAll(Arrays.asList(foods).subList(0, count));
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
                missedDeadlineFood = foods[i];
                break;
            }
        }
        readyQueue.addAll(Arrays.asList(foods).subList(0, count));
        return miss;
    }

    public void scheduling(){
        System.out.println("***Start EDF scheduling***");
        if(!readyQueue.isEmpty()){
            readyQueue.add(readyQueue.poll());
            boolean missPredict = isMissPredict(readyQueue);
            boolean deadlineMiss = isDeadlineMiss(readyQueue);
            while(currentTime < maximumTime){
                Food food = readyQueue.poll();
                food.setCooking(true);
                while(currentTime < maximumTime && !isArrive && !deadlineMiss && !missPredict && food.getRemainingTime() > 0){
                    //cooking time
                    food.setJustArrive(false);
                    food.updateRemainingTime();
                    updateWaitingTimes(readyQueue, food.getFoodName());
                    System.out.println(currentTime + " " + food.getFoodName());
                    System.out.println("****");
                    currentTime++;
                    manageIntervals();
                    if(food.getRemainingTime() == 0){
                        break;
                    }
                    else if(currentTime > food.getDeadline() + food.getArriveTime()){
                        System.out.println("Deadline of " + food.getFoodName() + " is missed in time " + currentTime);
                    }
                    missPredict = isMissPredict(readyQueue);
                    deadlineMiss = isDeadlineMiss(readyQueue);
                }
                while (readyQueue.isEmpty() && currentTime < maximumTime){
                    //rest time
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
                    food.setCooking(false);
                    isArrive = false;
                }
                if(deadlineMiss){
                    System.out.println("Deadline of " + missedDeadlineFood.getFoodName() + " is missed in time " + currentTime);
                    deadlineMiss = false;
                }
                if(missPredict){
                    System.out.println("miss Predicted for " + missPredictedFood.getFoodName() + " in time: " + currentTime);
                    missPredict = false;
                    // break;
                }
            }
        }
        System.out.println("cookers rest time: " + restTime);
        printWaitingTimes();
    }

    public void printWaitingTimes(){
        for (int i = 0; i < allFoods.length; i++) {
            System.out.println(allFoods[i].getFoodName()+ " wating time is " + waitingTimes.get(allFoods[i].getFoodName()));
        }
    }
}
