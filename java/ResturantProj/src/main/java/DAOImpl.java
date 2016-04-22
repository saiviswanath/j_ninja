import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DAOImpl implements DAO {
	private static final Properties PROPERTIES = PropertiesLoad.getProperties();
	private static final Connection SAMPLE_CONNECTION = DBConnector
			.getConnection(
					PROPERTIES
							.getProperty(PropertyConstants.RESTDB_SMAPLE_USERNAME),
					PROPERTIES
							.getProperty(PropertyConstants.RESTDB_SMAPLE_PASSWORD));
	private static final Connection LABDATAS16_CONNECTION = DBConnector
			.getConnection(
					PROPERTIES
							.getProperty(PropertyConstants.RESTDB_LABDATAS16_USERNAME),
					PROPERTIES
							.getProperty(PropertyConstants.RESTDB_LABDATAS16_PASSWORD));

	@Override
	public void createMyCategories() {
		try {
			SAMPLE_CONNECTION.setAutoCommit(false);
			if (doesTableExists("MYCATEGORIES")) {
				dropATable("MYCATEGORIES");
			}
			createMyCatTable();
			copyMyCatData();
			displayMyCatTableRows();
			System.out.println("Part D displayed");
			SAMPLE_CONNECTION.rollback();
			displayMyCatTableRows();
			System.out.println("Part F displayed");
			copyMyCatData();
			displayMyCatTableRows();
			System.out.println("Part H displayed");
			SAMPLE_CONNECTION.commit();
			displayMyCatTableRows();
			System.out.println("Part J displayed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean doesTableExists(String tableName) throws SQLException {
		PreparedStatement prepStmt = null;
		boolean tableExists = false;
		String sql = "select table_name from user_tables "
				+ "where table_name='" + tableName + "'";
		prepStmt = SAMPLE_CONNECTION.prepareStatement(sql);
		ResultSet resSet = prepStmt.executeQuery();
		if (resSet != null) {
			while (resSet.next()) {
				if (tableName.equals(resSet.getString(1))) {
					tableExists = true;
				}
			}
		}
		return tableExists;
	}

	private void dropATable(String tableName) throws SQLException {
		PreparedStatement prepStmt = null;
		prepStmt = SAMPLE_CONNECTION
				.prepareStatement("drop table " + tableName);
		prepStmt.executeUpdate();
	}

	private void createMyCatTable() throws SQLException {
		PreparedStatement prepStmt = null;
		prepStmt = SAMPLE_CONNECTION
				.prepareStatement("create table MyCategories(CategoryName varchar(15))");
		prepStmt.executeUpdate();
	}

	private void copyMyCatData() throws SQLException {
		List<String> categoryList = new ArrayList<>();
		PreparedStatement queryPrepStmt = null;
		PreparedStatement insertPrepStmt = null;
		queryPrepStmt = LABDATAS16_CONNECTION
				.prepareStatement("select CategoryName from Categories");
		ResultSet queryResSet = queryPrepStmt.executeQuery();
		if (queryResSet != null) {
			while (queryResSet.next()) {
				categoryList.add(queryResSet.getString(1));
			}
		}

		StringBuilder insertSql = new StringBuilder(
				"insert into MyCategories(CategoryName) ");
		for (String cat : categoryList) {
			insertSql.append("select '");
			insertSql.append(cat);
			insertSql.append("' from dual union all ");
		}
		String insertSqlStr = insertSql.toString();
		String newInsertSqlStr = insertSqlStr.substring(0, insertSqlStr.length() - 11);
		
		insertPrepStmt = SAMPLE_CONNECTION.prepareStatement(newInsertSqlStr);
		insertPrepStmt.executeUpdate();
	}

	private void displayMyCatTableRows() throws SQLException {
		PreparedStatement prepStmt = null;
		prepStmt = SAMPLE_CONNECTION
				.prepareStatement("select CategoryName from MyCategories");
		ResultSet resSet = prepStmt.executeQuery();
		if (resSet != null) {
			while (resSet.next()) {
				System.out.println(resSet.getString(1));
			}
		}
	}

	@Override
	public void findIngredientsByRecipe(String recipe) {
		PreparedStatement prepStmt = null;
		try {
			String sql = "select ing.ingredientname, ing.unitname, ing.quantity from "
					+ "Recipes rec inner join Ingredients ing on rec.recipeId = ing.recipeId "
					+ "and rec.RecipeName='" + recipe + "'";
			prepStmt = LABDATAS16_CONNECTION.prepareStatement(sql);

			ResultSet resSet = prepStmt.executeQuery();
			if (resSet != null) {
				System.out.println("INGREDIENTNAME, UNITNAME, QUANTITY");
				while (resSet.next()) {
					System.out.println(resSet.getString(1) + ", "
							+ resSet.getString(2) + ", " + resSet.getInt(3));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void classifyRecipes() {
		PreparedStatement prepStmt = null;
		try {
			String sql = "select rec.RecipeName, count(*) as IngredientCount from "
					+ "Recipes rec inner join Ingredients ing on rec.recipeId = ing.recipeId "
					+ "" + "group by rec.RecipeName";
			prepStmt = LABDATAS16_CONNECTION.prepareStatement(sql);

			ResultSet resSet = prepStmt.executeQuery();
			if (resSet != null) {
				System.out.println("Recipe Name, # of Ingredients, Difficulty");
				while (resSet.next()) {
					int difficultyInt = resSet.getInt(2);
					String difficultystr = null;
					if (difficultyInt < 5) {
						difficultystr = "Easy";
					} else if (difficultyInt >= 5 && difficultyInt <= 9) {
						difficultystr = "Medium";
					} else if (difficultyInt >= 10) {
						difficultystr = "Hard";
					}
					System.out.println(resSet.getString(1) + ", "
							+ difficultyInt + ", " + difficultystr);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printAuthorReport(String authorName) {
		List<String> recipesList = new ArrayList<>();
		PreparedStatement prepStmt = null;
		try {
			String sql = "select rec.RecipeName from Recipes rec inner join Author auth "
					+ "on rec.authId=auth.authId and auth.AuthorName='"
					+ authorName + "'";
			prepStmt = LABDATAS16_CONNECTION.prepareStatement(sql);

			ResultSet resSet = prepStmt.executeQuery();
			if (resSet != null) {
				while (resSet.next()) {
					recipesList.add(resSet.getString(1));
				}
			}

			for (String recipe : recipesList) {
				System.out.println("Recipe Name: " + recipe);
				System.out.println("Number of servings centered: 0"); // Dummy
				printIngredientDetails(recipe);
				System.out.println("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void printIngredientDetails(String recipe) throws SQLException {
		PreparedStatement prepStmt = null;
		String sql = "select ing.ingredientname, ing.ingredienttype, ing.unitname, ing.quantity from "
				+ "Recipes rec inner join Ingredients ing on rec.recipeId = ing.recipeId "
				+ "and rec.RecipeName='" + recipe + "'";
		prepStmt = LABDATAS16_CONNECTION.prepareStatement(sql);

		ResultSet resSet = prepStmt.executeQuery();
		if (resSet != null) {
			System.out
					.println("--------------------------------------------------------");
			System.out
					.println("| Ingredient Name, Ingredient Type, Unit Name, Quantity |");
			System.out
					.println("--------------------------------------------------------");
			while (resSet.next()) {
				System.out.println(resSet.getString(1) + ", "
						+ resSet.getString(2) + resSet.getString(3) + ", "
						+ resSet.getInt(4));
			}
			System.out
					.println("--------------------------------------------------------");
		}
	}
}
