package xyz.jninja.ds.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 
 */

/**
 * @author viswa
 *
 */
public class ArrayTree<E extends Comparable<? super E>> {
  private E[] tree;
  int size;

  @SuppressWarnings("unchecked")
  public ArrayTree(Class<?> cls) {
    tree = (E[]) Array.newInstance(cls, 128);
    size = 0;
  }

  public boolean isEmpty() {
    return this.size() == 0;
  }

  public E getCargo(int i) {
    return tree[i];
  }

  public void setCargo(int i, E cargo) {
    tree[i] = cargo;
    size++;
  }

  public int getLeft(int i) {
    i = 2 * i;
    if (this.getCargo(i) == null) {
      return -1;
    }
    return i;
  }

  public int getRight(int i) {
    i = (2 * i) + 1;
    if (this.getCargo(i) == null) {
      return -1;
    }
    return i;
  }

  public int getParent(int i) {
    i = i / 2;
    if (this.getCargo(i) == null) {
      return -1;
    }
    return i;
  }

  private void print(int i) {
    E cargo = this.getCargo(i);
    if (cargo != null) {
      System.out.println(cargo);
    }

    E left = this.getCargo(getLeft(i));
    if (left != null) {
      System.out.println(left);
    }
    E right = this.getCargo(getRight(i));
    if (right != null) {
      System.out.println(right);
    }
  }

  private int findMax() {
    int i = 1;
    int max = i;
    while (i <= this.size()) {
      if (tree[i].compareTo(tree[max]) >= 0) {
        max = i;
      }
      i++;
    }
    return max;
  }

  /**
   * O(n)
   * 
   * @param e
   * @param i
   * @return
   */
  private boolean contains(E e, int i) {
    if (i > this.size()) {
      return false;
    }

    int e1l = this.getLeft(i);
    int e1r = this.getRight(i);

    if (i > 0 && this.getCargo(i).equals(e)) {
      return true;
    } else if (e1l > 0 && this.getCargo(e1l).equals(e)) {
      return true;
    } else if (e1r > 0 && this.getCargo(e1r).equals(e)) {
      return true;
    }
    return this.contains(e, ++i);
  }

  /**
   * Incomplete impl
   * 
   * 1. Check for tree fullness untill last but one level else false; 2. Check if last level is
   * properly filled from left to right. -pass through size0, size 1. -Iterate from last but one
   * levels min to max and break if left is -1.
   * 
   * @return
   */
  private boolean isComplete() {
    if (!this.isTreeAlmostFull()) {
      return false;
    }

    if (this.size() == 0 || this.size() == 1) {
      return true;
    }

    int lastLevel = height(this.size());
    int checkVal = getNodesAtLevel(lastLevel - 1);
    int checkLastLevel = getNodesAtLevel(lastLevel);
    boolean chk = true;
    for (int i = checkVal; i <= (2 * checkVal - 1); i++) {
      int left = this.getLeft(i);
      if (left == -1) {
        if (checkLastLevel > this.size()) {
          chk = true;
        } else {
          chk = false;
        }
        break;
      } else {
        checkLastLevel += 1;
      }

      int right = this.getRight(i);
      if (checkLastLevel != right) {
        if (checkLastLevel > this.size()) {
          chk = true;
        } else {
          chk = false;
        }
        break;
      } else {
        checkLastLevel += 1;
      }

    }
    return chk;
  }

  /**
   * 0. size =0/1 return true. 1. Get the last but one level of the tree. 2. Iterate though the
   * index untill tree size and break if i-index is equals to min val of level from 1. 3. For each
   * i-index check if left and right are -1 if so return false;
   * 
   * @return
   */
  private boolean isTreeAlmostFull() {
    if (this.size() == 0 || this.size() == 1) {
      return true;
    }

    int treeHt = height(this.size());
    int checkVal = getNodesAtLevel(treeHt - 1);

    for (int i = 1; i <= this.size(); i++) {
      if (i == checkVal) {
        break;
      }
      int leftIndex = this.getLeft(i);
      int rightIndex = this.getRight(i);
      if (leftIndex == -1 || rightIndex == -1) {
        return false;
      }
    }
    return true;
  }

  private int isCompleteSample(int i) {
    if (i == -1) {
      return 0;
    }

    int lft = isCompleteSample(this.getLeft(i));
    int rgt = isCompleteSample(this.getRight(i));
    if (height(lft) == height(rgt)) {
      return 1;
    } else if (height(lft) - 1 == height(rgt)) {
      return 1;
    } else {
      return -1;
    }
  }

  private boolean isHepeafied() {
    if (this.size() == 0 || this.size() == 1) {
      return true;
    }

    boolean chk = false;
    for (int i = 1; i <= this.size(); i++) {
      E root = this.getCargo(i);
      int leftIndex = this.getLeft(i);
      int rightIndex = this.getRight(i);

      if (leftIndex == -1) {
        break;
      }

      E left = this.getCargo(leftIndex);
      E right = this.getCargo(rightIndex);

      if (root.compareTo(left) >= 0) {
        chk = true;
      }

      if (root.compareTo(right) >= 0) {
        chk = true;
      }
    }

    return chk;
  }

  private void add(E e) {
    this.tree[++this.size] = e;
    if (this.size != 0 && this.size() != 1) {
      reheapify(this.size());
    }
  }

  private void reheapify(int i) {

    int greatest;

    int lindex = this.getLeft(i);
    int rindex = this.getRight(i);

    E lcargo = (lindex != -1) ? this.getCargo(lindex) : null;
    E rcargo = (rindex != -1) ? this.getCargo(rindex) : null;

    if (lcargo != null && rcargo != null && lindex < this.size() && lcargo.compareTo(rcargo) >= 0) {
      greatest = lindex;
    } else {
      greatest = i;
    }

    if (rcargo != null && rindex < this.size() && rcargo.compareTo(this.getCargo(greatest)) >= 0) {
      greatest = rindex;
    }

    if (greatest != i) {
      this.swap(i, greatest);
      this.reheapify(greatest);
    }
  }

  private void swap(int i, int j) {
    E temp = this.tree[i];
    this.tree[i] = this.tree[j];
    this.tree[j] = temp;
  }

  private static int getNodesAtLevel(int level) {
    return (int) Math.pow(2, level - 1);
  }

  private int size() {
    return this.size;
  }

  /**
   * size >= 1, height = 1 size >=2, height = 2 size >=4 height =3 size >=8 height =4 .... size = 2
   * ^ (height - 1) then height - 1 = log (size) base 2 height = log (size) base 2 + 1
   * 
   * @return
   */
  // Height of tree = log (number of nodes) to base 2.
  private static int height(int tsize) {
    if (tsize == -1) {
      return -1;
    }
    return (int) (Math.log(tsize) / Math.log(2)) + 1;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    ArrayTree<Integer> tree = new ArrayTree<Integer>(Integer.class);
    if (tree.isEmpty()) {
      System.out.println("Empty");
    }

/*    String str = new String("\tabc");
    System.out.println(str);
    tree.add(20);
    tree.add(30);
    tree.add(50);
    tree.add(40);
    tree.add(80);
    System.out.println(tree);*/


    /*
     * tree.setCargo(6, 60); tree.setCargo(7, 60); tree.setCargo(8, 60); tree.setCargo(9, 60);
     * tree.setCargo(10, 60); tree.setCargo(11, 60); tree.setCargo(12, 60); tree.setCargo(13, 60);
     * tree.setCargo(14, 60); tree.setCargo(15, 60);
     */
    // tree.setCargo(16, 60);

    /*
     * System.out.println(tree.findMax()); System.out.println();
     * 
     * if (tree.contains(45, 1)) { System.out.println("Found"); } else {
     * System.out.println("Not Found"); }
     */
    // System.out.println(height(-1));

    /*
     * if (tree.isCompleteSample(1) == 1) { System.out.println("Complete"); } else {
     * System.out.println("Not Complete"); }
     */

    // tree.print(1);
    // System.out.println(tree.size());
    // System.out.println(tree.isTreeComplete());

    // System.out.println(tree.getCargo(tree.getParent(1)));

    // System.out.println(height(tree.size()));

    /*
     * for (int i = 1; i<=2000; i++) { System.out.println(getnlogn(i) + " : " + getnn(i)); }
     */
    
    try {
      tree.printMenu();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ArrayTree [tree=" + Arrays.toString(tree) + ", size=" + size + "]";
  }

  private void printMenu() throws IOException {
    OptionsEnum[] enu = OptionsEnum.values();
    for (OptionsEnum en : enu) {
      System.out.println(en.getId() + "." + en.getName());
    }
    System.out.print("Please select an option from 0-3-> ");

    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    int selectedOption = Integer.parseInt(keyboard.readLine());

    switch (selectedOption) {
      case 0:
        exit();
        break;
      case 1:
        printSubMenu(1, keyboard);
        break;
      case 2:
        printSubMenu(2, keyboard);
        break;
      case 3:
        printSubMenu(3, keyboard);
        break;
      default:
        printMenu();
        break;
    }
  }

  private void printSubMenu(int mainOption, BufferedReader br) throws IOException {
    switch (mainOption) {
      case 1:
        System.out.print("Please input the Node you want to add-> ");
        E node = (E) br.readLine();
        this.add(node);
        System.out.println("Node added successfully");
        printMenu();
        break;
      case 2:
        int size = this.size;
        System.out.println("There are " + size + " nodes in that tree.");
        printMenu();
        break;
      case 3:
        System.out.print("Please input the node you want to look for -> ");
        E foundNode = (E) br.readLine();
        System.out.println("Please input the location index -> ");
        int index = Integer.parseInt(br.readLine());
        boolean fNode = this.contains(foundNode, index);

        if (fNode) {
          System.out.println(foundNode + " is Found");
        } else {
          System.out.println("Node " + foundNode + " does not exist");
        }
        printMenu();
        break;

    }
  }

  enum OptionsEnum {
    EXIT("Exit"), ADDNODE("Add Node"), TREESIZE("Tree Size"), FINDNODE("Find Node");

    private final String name;

    OptionsEnum(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public int getId() {
      return this.ordinal();
    }
  }

  private void exit() {
    System.out.println("You have exited the program");
    System.exit(1);
  }


}
