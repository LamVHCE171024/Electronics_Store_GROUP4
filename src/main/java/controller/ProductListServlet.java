package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Product;

@WebServlet(name = "ProductListServlet", urlPatterns = {"/ProductList"})
public class ProductListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        if (action.equalsIgnoreCase("list")) {
            List<Product> products = dao.getProductList();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/productManagement.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("search")) {
            String keyword = request.getParameter("keyword");
            List<Product> products;
            if (keyword != null && !keyword.trim().isEmpty()) {
                products = dao.searchProductByName(keyword);
            } else {
                products = dao.getProductList();
            }
            request.setAttribute("products", products);
            if (products.isEmpty()) {
                request.setAttribute("message", "No product found.");
            }
            request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/productManagement.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("detail")) {
            String idRaw = request.getParameter("id");
            try {
                int id = Integer.parseInt(idRaw);
                Product product = dao.getProductByID(id);
                request.setAttribute("data", product);
                request.getRequestDispatcher("/WEB-INF/View/admin/productManagement/productDetail.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                PrintWriter out = response.getWriter();
                out.print("Invalid product ID");
            } catch (Exception e) {
                PrintWriter out = response.getWriter();
                out.print(e.getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing product list, search, and details";
    }
}
