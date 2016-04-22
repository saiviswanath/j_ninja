import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		DAO dao = new DAOImpl();
		do {
			System.out.println("Please choose the required option....");
			System.out.println("(1) Create MyCategories");
			System.out.println("(2) Find the ingredients used in a recipe");
			System.out.println("(3) Classify recipes by number of ingredients");
			System.out.println("(4) Print the author report");
			System.out.println("(5) Exit");

			System.out.print("Enter choice: ");
			int choice = 0;
			if (input.hasNext()) {
				try {
					choice = Integer.parseInt(input.next());
				} catch (Exception e) {
					System.out.println("Unsupported Choice");
				}
			}

			if (choice > 0 && choice <= 5) {
				switch (choice) {
				case 1:
					dao.createMyCategories();
					break;
				case 2:
					System.out.print("Enter recipe: ");
					if (input.hasNext()) {
						dao.findIngredientsByRecipe(input.next());
					}
					break;
				case 3: dao.classifyRecipes();
					break;
				case 4:
					System.out.print("Enter author name: ");
					if (input.hasNext()) {
						dao.printAuthorReport(input.next());
					}
					break;
				case 5:
					input.close();
					System.exit(0);
					break;
				case 6:
					System.out.println("Unsupported Choice");
					break;
				}
			} else {
				System.out.println("Unsupported Choice");
			}
		} while (true);
	}

}
