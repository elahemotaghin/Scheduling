public class Food  implements Comparable<Food>{
    private String foodName;
    private int time;
    private int deadline;
    private int interval;
    private int arriveTime;
    private int remainingTime;
    private boolean isCooking;

    public Food(String foodName, int time, int deadline, int interval, int arriveTime){
        this.foodName = foodName;
        this.time = time;
        this.deadline = deadline;
        this.interval = interval;
        this.arriveTime = arriveTime;
        this.isCooking = false;
        this.remainingTime = time;
    }

    public Food(String foodName, int time, int deadline, int interval){
        this.foodName = foodName;
        this.time = time;
        this.deadline = deadline;
        this.interval = interval;
        this.isCooking = false;
        this.remainingTime = time;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getTime() {
        return time;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getInterval() {
        return interval;
    }

    public boolean isCooking() {
        return isCooking;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getArriveTime() {
        return arriveTime;
    }

    public void updateRemainingTime() {
        this.remainingTime --;
    }

    public void setCooking(boolean cooking) {
        isCooking = cooking;
    }

    public void setArriveTime(int arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void printFood(){
        System.out.println("Food name: " + this.getFoodName());
        System.out.println("Time: " + this.getTime());
        System.out.println("Interval: " + this.getInterval());
        System.out.println("Deadline: " + this.getDeadline());
    }

    //rate monotonic
    @Override
    public int compareTo(Food o) {
        return this.getInterval() - o.getInterval();
    }
}
