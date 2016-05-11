package employee.stock;

import application.ConnectionToken;
import application.Connector;
import application.Utility;
import business.employee.AuthToken;
import business.stock.Category;
import business.stock.DataRender;
import business.stock.Ingredient;
import business.stock.Entry;
import business.stock.Item;
import business.stock.Processor;
import business.stock.Result;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class Action extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("param");
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		JSONObject json = new JSONObject();
		Enumeration<String> parameters = (Enumeration<String>) request.getParameterNames();
		boolean proceed = true;
		while (parameters.hasMoreElements()) {
			if (request.getParameter(parameters.nextElement()).trim().length() < 1) {
				proceed = false;
				break;
			}
		}
		char result = '\0';
		AuthToken authToken = ((AuthToken) request.getSession().getAttribute("auth_token"));
		Connection con = connector.getConnection(token);
		if (proceed && param != null) {
			if (authToken != null) {
				if (con != null) {
					switch (param) {
						case "add_category":
							result = Processor.addCategory(request.getParameter("category"), authToken, con);
							writeUpdateResponse(result, json, response, "Category added successfully!");
							break;
						case "activate_category":
							result = Processor.alterStatusOfCategory(Integer.parseInt(request.getParameter("code")), true, authToken, con);
							writeUpdateResponse(result, json, response, "Category activated successfully!");
							break;
						case "disable_category":
							result = Processor.alterStatusOfCategory(Integer.parseInt(request.getParameter("code")), false, authToken, con);
							writeUpdateResponse(result, json, response, "Category disabled successfully!");
							break;
						case "edit_category":
							result = Processor.editCategory(Integer.parseInt(request.getParameter("code")), request.getParameter("category"), authToken, con);
							writeUpdateResponse(result, json, response, "Category renamed successfully!");
							break;
						case "enable_item":
							result = Processor.alterStatusOfItem(Integer.parseInt(request.getParameter("code")), true, authToken, con);
							writeUpdateResponse(result, json, response, "Item enabled successfully!");
							break;
						case "disable_item":
							result = Processor.alterStatusOfItem(Integer.parseInt(request.getParameter("code")), false, authToken, con);
							writeUpdateResponse(result, json, response, "Item disabled successfully!");
							break;
						case "add_item":
							Item item = new Item();
							item.setName(request.getParameter("name"));
							item.setDescription(request.getParameter("description"));
							item.setMinPercentage(Integer.parseInt(request.getParameter("min_percentage")));
							item.setOnOrder(request.getParameter("on_order").charAt(0));
							item.setDelivery(request.getParameter("delivery").charAt(0));
							item.setPrice(Float.parseFloat(request.getParameter("price")));
							item.setCategory(Integer.parseInt(request.getParameter("category")));
							item.setImageExt(Utility.getSuitableExtension(request.getParameter("file-type")));
							Result rs = Processor.addItem(item, authToken, con);
							boolean success = false;
							if (rs.getStatus() == Processor.SUCCESS) {
								ServletContext context = getServletContext();
								File root = new File(context.getRealPath("/"));
								l:
								for (File file : root.listFiles()) {
									if (file.isDirectory() && file.getName().equals("img")) {
										for (File dir : file.listFiles()) {
											if (dir.isDirectory() && dir.getName().equals("item")) {
												request.getSession().setAttribute("dir", dir);
												request.getSession().setAttribute("code", new Integer(rs.getCode()));
												success = true;
											}
										}
									}
								}
							}
							writeUploadResponse(rs.getStatus(), success, json, response, "Item added successfully", "Item could not be added");
							break;
						case "add_item_stock":
							Result res = Processor.addItemInStock(Integer.parseInt(request.getParameter("code")), Float.parseFloat(request.getParameter("amount")), Utility.parseInputDateTime(request.getParameter("expiry")), Boolean.parseBoolean(request.getParameter("override")), authToken, con);
							writeBatchUpdate(res, json, response, "Batch added successfully!", "Insufficient ingredients, continue anyway?");
							break;
						case "reduce_item_stock":
							result = Processor.reduceStock(0, Integer.parseInt(request.getParameter("code")), Float.parseFloat(request.getParameter("amount")), "item", authToken, con);
							writeUpdateResponse(result, json, response, "Stock reduced successfully");
							break;
						case "reduce_batch_item_stock":
							result = Processor.reduceStock(Integer.parseInt(request.getParameter("batch")), Integer.parseInt(request.getParameter("code")), Float.parseFloat(request.getParameter("amount")), "item", authToken, con);
							writeUpdateResponse(result, json, response, "Batch Stock reduced successfully");
							break;
						case "change_price":
							result = Processor.changePrice(Integer.parseInt(request.getParameter("code")), Float.parseFloat(request.getParameter("price")), authToken, con);
							writeUpdateResponse(result, json, response, "Price changed successfully!");
							break;
						case "remove_item_batch":
							result = Processor.removeBatch(Integer.parseInt(request.getParameter("code")), "item", authToken, con);
							writeUpdateResponse(result, json, response, "Batch removed successfully!");
							break;
						case "add_item_unit":
							result = Processor.addItemUnit(Integer.parseInt(request.getParameter("item")), request.getParameter("name"), Float.parseFloat(request.getParameter("fraction")), Float.parseFloat(request.getParameter("price")),authToken, con);
							writeUpdateResponse(result, json, response, "Item unit added successfully!");
							break;
						case "enable_item_unit":
							result = Processor.alterStatusOfUnit(Integer.parseInt(request.getParameter("code")), true, authToken, con);
							writeUpdateResponse(result, json, response, "Item unit enabled successfully!");
							break;
						case "disable_item_unit":
							result = Processor.alterStatusOfUnit(Integer.parseInt(request.getParameter("code")), false, authToken, con);
							writeUpdateResponse(result, json, response, "Item unit disabled successfully!");
							break;
						case "add_ingredient":
							Ingredient ingridient = new Ingredient();
							ingridient.setName(request.getParameter("ingridient"));
							ingridient.setState(request.getParameter("state").charAt(0));
							ingridient.setAddedBy(((AuthToken) request.getSession().getAttribute("auth_token")).getName());
							result = Processor.addIngridient(ingridient, authToken, con);
							writeUpdateResponse(result, json, response, "Ingridient added successfully!");
							break;
						case "add_ingredient_stock":
							res = Processor.addIngridientStock(Integer.parseInt(request.getParameter("code")), Float.parseFloat(request.getParameter("amount")), Utility.parseInputDateTime(request.getParameter("expiry")), authToken, con);
							writeBatchUpdate(res, json, response, "Ingridient batch added succeessfully!", "Error Occured!");
							break;
						case "remove_ingredient_batch":
							result = Processor.removeBatch(Integer.parseInt(request.getParameter("code")), "ingridients", authToken, con);
							writeUpdateResponse(result, json, response, "Batch removed successfully!");
							break;
						case "reduce_ingredient_stock":
							result = Processor.reduceStock(0, Integer.parseInt(request.getParameter("code")), Float.parseFloat(request.getParameter("amount")), "ingridients", authToken, con);
							writeUpdateResponse(result, json, response, "Stock reduced successfully");
							break;
						case "reduce_batch_ingredient_stock":
							result = Processor.reduceStock(Integer.parseInt(request.getParameter("batch")), Integer.parseInt(request.getParameter("code")), Float.parseFloat(request.getParameter("amount")), "ingridients", authToken, con);
							writeUpdateResponse(result, json, response, "Batch Stock reduced successfully");
							break;
						case "add_relation":
							result = Processor.addRelation(Integer.parseInt(request.getParameter("item")), Integer.parseInt(request.getParameter("ingridient")), Double.parseDouble(request.getParameter("amount")), authToken, con);
							writeUpdateResponse(result, json, response, "Relation added successfully!");
							break;
						case "remove_relation":
							result = Processor.removeRelation(Integer.parseInt(request.getParameter("code")), authToken, con);
							writeUpdateResponse(result, json, response, "Relation removed successfully!");
					}
					connector.closeConnection(token);
				} else {
					writeErrorResponse(json, response, "Database connection failed, try again!");
				}
			} else {
				writeErrorResponse(json, response, "Atuhentication Failed");
			}

		} else {
			writeErrorResponse(json, response, "Empty values are not allowed!");
		}

	}

	public void writeUploadResponse(char result, boolean success, JSONObject json, HttpServletResponse response, String successMessage, String errorMessage) throws IOException {
		if (result == Processor.SUCCESS && success) {
			json.put("status", 1);
			json.put("message", successMessage + " uploading file!");
		} else if (result != Processor.SUCCESS) {
			json.put("status", 2);
			json.put("message", errorMessage + "!");
		} else {
			json.put("status", 0);
			json.put("message", successMessage + " but file could not be uploaded!");
		}
		PrintWriter writer = response.getWriter();
		writer.print(json);
	}

	public void writeErrorResponse(JSONObject json, HttpServletResponse response, String errorMessage) throws IOException {
		json.put("status", 2);
		json.put("message", errorMessage);
		PrintWriter writer = response.getWriter();
		writer.print(json);
	}

	public void writeUpdateResponse(char result, JSONObject json, HttpServletResponse response, String successMessage) throws IOException {
		switch (result) {
			case Processor.SUCCESS:
				json.put("status", 1);
				json.put("message", successMessage);
				break;
			case Processor.INSUFFICIENT_BATCH_STOCK:
				json.put("status", 0);
				json.put("message", "Insuffucient batch stock!");
				break;
			case Processor.INSUFFICIENT_STOCK:
				json.put("status", 0);
				json.put("message", "Insufficient stock!");
				break;
			default:
				json.put("status", 0);
				json.put("message", "Unauthorized Operation!");
				break;
		}
		PrintWriter writer = response.getWriter();
		writer.print(json);
	}

	public void writeBatchUpdate(Result result, JSONObject json, HttpServletResponse response, String successMessage, String errorMessage) throws IOException {
		switch (result.getStatus()) {
			case Processor.SUCCESS:
				json.put("status", 10);
				json.put("message", successMessage);
				json.put("batch", result.getCode());
				json.put("url", response.encodeURL("templates/batchCode.jsp"));
				break;
			case Processor.INSUFFICIENT_STOCK:
				json.put("status", 9);
				json.put("message", errorMessage);
				break;
			default:
				json.put("status", 0);
				json.put("message", "Unauthorized Access!");
				break;
		}
		PrintWriter writer = response.getWriter();
		writer.print(json);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("param");
		Connector connector = (Connector) getServletContext().getAttribute("connector");
		ConnectionToken token = connector.getToken();
		AuthToken authToken = (AuthToken) request.getSession().getAttribute("auth_token");
		Connection con = connector.getConnection(token);
		if (authToken != null && con != null) {
			switch (param) {
				case "view_item_display":
					ArrayList<Category> category = Processor.getCategories(con);
					request.setAttribute("categories", category);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/employee/stock/resource/itemDisplay.jsp");
					dispatcher.forward(request, response);
					break;
				case "view_ingredient_display":
					ArrayList<Ingredient> list = Processor.getIngridients(authToken, con);
					request.setAttribute("ingredients", list);
					dispatcher = request.getRequestDispatcher("/employee/stock/resource/ingredientDisplay.jsp");
					dispatcher.forward(request, response);
					break;
				case "view_ingredient_transactions":
					ArrayList<Entry> ingridient_entry = Processor.getIngridientEntry(Integer.parseInt(request.getParameter("code")), "ingridients", authToken, con);
					request.setAttribute("ingridient_entry", ingridient_entry);
					dispatcher = request.getRequestDispatcher("/employee/stock/resource/ingredientEntry.jsp");
					dispatcher.forward(request, response);
					break;
				case "view_item_transactions":
					ArrayList<Entry> item_entry = Processor.getIngridientEntry(Integer.parseInt(request.getParameter("code")), "item", authToken, con);
					request.setAttribute("item_entry", item_entry);
					dispatcher = request.getRequestDispatcher("/employee/stock/resource/itemEntry.jsp");
					dispatcher.forward(request, response);
					break;
				case "view_relations":
					category = Processor.getRelations(authToken, con);
					request.setAttribute("categories", category);
					dispatcher = request.getRequestDispatcher("/employee/stock/resource/viewRelations.jsp");
					dispatcher.forward(request, response);
					break;
				case "view_price_history":
					ArrayList<DataRender> priceList = Processor.getPriceHistory(Integer.parseInt(request.getParameter("code")), authToken, con);
					request.setAttribute("list", priceList);
					dispatcher = request.getRequestDispatcher("/employee/stock/resource/viewPriceHistory.jsp");
					dispatcher.forward(request, response);
					break;
				case "view_item_config":
					category = Processor.getItemConfig(authToken, con);
					request.setAttribute("categories", category);
					dispatcher = request.getRequestDispatcher("/employee/stock/resource/itemConfig.jsp");
					dispatcher.forward(request, response);
					break;
			}
			connector.closeConnection(token);
		}
	}
}
