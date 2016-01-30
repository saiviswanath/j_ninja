public class BillPayment {
  public static void main(String... args) {
    if (args.length < 2) {
      System.out.println("Usage:"); // TODO
    }

    double billAmount = Double.parseDouble(args[0]);
    int noOfFriends = Integer.parseInt(args[1]);

    double tip = (billAmount / 100) * 15;
    double totalCost = billAmount + tip;

    double billAmtPerFriend = billAmount / noOfFriends;
    double tipPerFriend = tip / noOfFriends;
    double amtPaidPerFriend = billAmtPerFriend + tipPerFriend;

    System.out.println("--------------------------------");
    System.out.println("Amount of the bill: " + billAmount + "\nTip: " + tip + "\nTotal Cost: "
        + totalCost + "Amount Per Friend: " + amtPaidPerFriend + "\n" + "Bill Amount Per Friend: "
        + billAmtPerFriend + "Tip per Friend: " + tipPerFriend);
    System.out.println("--------------------------------");
  }
}
