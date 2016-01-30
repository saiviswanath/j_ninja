import java.util.Random;


public class LotteryPrinter {

  public static void main(String[] args) {
    int upperbound = 50;
    int[] randomArray = new int[6];
    Random random = new Random();
    boolean randCheck = false;

    for (int i = 0; i < randomArray.length; i++) {
      // reset i if rand exists in array or rand = 0. So array index is re-used.
      if (randCheck) {
        i--;
      } else {
        randCheck = false;
      }

      int rand = random.nextInt(upperbound);

      if (rand > 0) {
        for (int j = 0; j < randomArray.length; j++) {
          if (randomArray[j] == randomArray[i]) {
            randCheck = true;
            break;
          }
        }

        if (!randCheck) {
          randomArray[i] = rand;
        }
      } else {
        randCheck = true;
      }
    }

    System.out.println("Play this combination—it'll make you rich!");
    for (int i = 0; i < randomArray.length; i++) {
      if (i == randomArray.length - 1) {
        System.out.print(randomArray[i]);
      } else {
        System.out.print(randomArray[i] + " ");
      }
    }
  }
}
