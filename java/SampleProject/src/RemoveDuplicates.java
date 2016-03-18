import java.util.HashSet;
import java.util.Set;


public class RemoveDuplicates {
  public static void main(String... args) {
    int[] arr = {2, 4, 4, 6, 8, 8, 10};
    Set<Integer> set = new HashSet<Integer>();
    for (int i = 0; i < arr.length; i++) {
      set.add(arr[i]);
    }
    
    for (Integer i : set) {
      System.out.print(i + " ");
    }
  }
}
