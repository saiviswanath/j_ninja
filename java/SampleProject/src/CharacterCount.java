import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CharacterCount {

  public static void main(String[] args) {
    String s = args[0];
    printSortedChars(s);
    printSortedCharsUsingColl(s);
  }

  private static void printSortedCharsUsingColl(String s) {
    char[] arr = s.toCharArray();
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    for (int i = 0; i < arr.length; i++) {
      Integer intVal = map.get(arr[i]);
      if (intVal == null) {
        intVal = new Integer(1);
        map.put(arr[i], intVal);
      } else {
        intVal++;
        map.put(arr[i], intVal);
      }
    }
    sortByValues(map);
    System.out.println(sortByValues(map).toString());
  }

  private static <K, V extends Comparable<V>> Map<K, V> sortByValues(Map<K, V> map) {
    Comparator<K> valueComparator = new Comparator<K>() {
      public int compare(K k1, K k2) {
        int compare = map.get(k2).compareTo(map.get(k1));
        if (compare == 0)
          return 1;
        else
          return compare;
      }
    };
    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
    sortedByValues.putAll(map);
    return sortedByValues;
  }

  private static void printSortedChars(String s) {
    char[] arr = s.toCharArray();
    int[] hist = new int[122];
    for (int i = 0; i < arr.length; i++) {
      int asciNum = arr[i]; // Ascii number
      hist[asciNum]++;
    }
    selctionSortArray(hist);
    for (int i = 0; i < hist.length; i++) {
      int val = hist[i];
      if (val > 0) {
        System.out.println(val);
      }
    }
  }

  private static void selctionSortArray(int[] arr) { // [1, 73, 0, 1, 6, 31, 84, 4, 2]
    int i = 0;
    int arrLength = arr.length;
    while (i < arrLength) {
      int maxValIndex = indexOfMaxValueInArrayRange(arr, i, arrLength - 1);
      swapElement(arr, i, maxValIndex);
      i++;
    }
  }

  private static int indexOfMaxValueInArrayRange(int[] arr, int low, int high) {
    if (low == high) {
      return low;
    } else {
      int arbMidPoint = low + (high - low) / 2;
      int index1 = indexOfMaxValueInArrayRange(arr, low, arbMidPoint);
      int index2 = indexOfMaxValueInArrayRange(arr, arbMidPoint + 1, high);
      if (arr[index1] > arr[index2]) {
        return index1;
      } else {
        return index2;
      }
    }
  }

  private static void swapElement(int[] arr, int index1, int index2) {
    if (index1 != index2) {
      arr[index1] = arr[index1] + arr[index2];
      arr[index2] = arr[index1] - arr[index2];
      arr[index1] = arr[index1] - arr[index2];
    }
  }
}
