public class SumOfPrimeNumbers {
  public static void main(String... args) {
    int sum = 0;
    for (int i = 2; i <= 100; i++) {
      if (isPrime(i)) {
        sum = sum + i;
      }
    }
  }

  private static boolean isPrime(int number) {
    for (int i = 2; i < number; i++) {
      if (number % 2 == 0) {
        return false;
      }
    }
    return true;
  }
}
