import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args){
        int maxTime = 1;
        try{
            //load input file
            File f1=new File("sample\\input1.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String fileLine;
            PriorityQueue<Food> foods = new PriorityQueue<>();
            int foodNumber = Integer.parseInt(br.readLine());
            while ((fileLine = br.readLine()) != null){
                String[] foodInfo = fileLine.split(" ");
                Food food = new Food(foodInfo[0], Integer.parseInt(foodInfo[1]), Integer.parseInt(foodInfo[2]), Integer.parseInt(foodInfo[3]), 0);
                foods.add(food);
                maxTime = lcm(maxTime, food.getInterval());
            }
            //handle offers
            Food[] foodsArray = new Food[foodNumber];
            int u = 0;
            for (int i = 0 ; i < foodNumber ; i++) {
                foodsArray[i] = foods.poll();
            }
            foods.addAll(Arrays.asList(foodsArray).subList(0, foodNumber));
            for (int i = 0; i < foodNumber; i++) {
                u += foodsArray[i].getTime()/foodsArray[i].getInterval();
            }
            if(u >= 1){
                System.out.println("cant handle!!!!");
            }
            else {
                //RateMonotonic rateMonotonic = new RateMonotonic(foods, maxTime);
                //rateMonotonic.scheduling();

                //DeadlineFirst deadlineFirst = new DeadlineFirst(foods, maxTime);
                //deadlineFirst.scheduling();

                LeastLaxity leastLaxity = new LeastLaxity(foods, maxTime);
                leastLaxity.scheduling();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int lcm(int number1, int number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        int absNumber1 = Math.abs(number1);
        int absNumber2 = Math.abs(number2);
        int absHigherNumber = Math.max(absNumber1, absNumber2);
        int absLowerNumber = Math.min(absNumber1, absNumber2);
        int lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}
