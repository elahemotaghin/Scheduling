public class Food  implements Comparable<Food>{
    private String foodName;
    private int time;
    private int deadline;
    private int interval;
    private int arriveTime;
    private int remainingTime;
    private boolean isCooking;
    private boolean isJustArrive = false;

    public Food(String foodName, int time, int deadline, int interval, int arriveTime){
        this.foodName = foodName;
        this.time = time;
        this.deadline = deadline;
        this.interval = interval;
        this.arriveTime = arriveTime;
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

    public boolean isJustArrive() {
        return isJustArrive;
    }

    public void updateRemainingTime() {
        this.remainingTime --;
    }

    public void setCooking(boolean cooking) {
        isCooking = cooking;
    }

    public void setJustArrive(boolean justArrive) {
        isJustArrive = justArrive;
    }

    public void printFood(){
        System.out.println("Food name: " + this.getFoodName());
        System.out.println("Time: " + this.getTime());
        System.out.println("Interval: " + this.getInterval());
        System.out.println("Deadline: " + this.getDeadline());
        System.out.println("Remaining Time: " + this.getRemainingTime());
    }
    //deadline first
    @Override
    public int compareTo(Food o) {
        if(o.isJustArrive())
            return -1;
        else if(this.isJustArrive())
            return 1;
        else if(o.isJustArrive() && this.isJustArrive || o.getInterval() == this.getInterval()) {
            if (o.isCooking() && this.isCooking())
                return this.getTime() - o.getTime();
            else if (o.isCooking() && !this.isCooking())
                return -1;
            else if (!o.isCooking() && this.isCooking())
                return 1;
            else
                return this.getTime() - o.getTime();
        }
        return this.getDeadline() - o.getDeadline();
    }

    //rate monotonic
    /*
    @Override
    public int compareTo(Food o) {
        if(o.isJustArrive())
            return -1;
        else if(this.isJustArrive())
            return 1;
        else if(o.isJustArrive() && this.isJustArrive || o.getInterval() == this.getInterval()) {
            if (o.isCooking() && this.isCooking())
                return this.getTime() - o.getTime();
            else if (o.isCooking() && !this.isCooking())
                return -1;
            else if (!o.isCooking() && this.isCooking())
                return 1;
            else
                return this.getTime() - o.getTime();
        }
        return this.getInterval() - o.getInterval();
    }
    */

}
