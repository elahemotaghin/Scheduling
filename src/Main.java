import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args){
        int maxTime = 1;
        try{
            //load input file
            File f1=new File("sample\\input.txt");
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            String fileLine;
            PriorityQueue<Food> foods = new PriorityQueue<>();
            while ((fileLine = br.readLine()) != null){
                String[] foodInfo = fileLine.split(" ");
                Food food = new Food(foodInfo[0], Integer.parseInt(foodInfo[1]), Integer.parseInt(foodInfo[2]), Integer.parseInt(foodInfo[3]), 0);
                foods.add(food);
                maxTime = lcm(maxTime, food.getInterval());
            }
            //System.out.println(maxTime);
            /*for (int i = 0 ; i < 3 ; i++) {
                foods.poll().printFood();
            }*/

            RateMonotonic rateMonotonic = new RateMonotonic(foods, maxTime);
            rateMonotonic.scheduling();
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
