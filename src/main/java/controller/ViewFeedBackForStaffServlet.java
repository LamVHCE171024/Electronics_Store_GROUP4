/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import dao.ProductDAO;
import dao.ProductRatingDAO;
import dao.RatingRepliesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Customer;
import model.Product;
import model.ProductRating;
import model.RatingReplies;

/**
 *
 * @author VinhNTCE181630
 */
@WebServlet(name = "ViewFeedBackForStaffServlet", urlPatterns = {"/ViewFeedBackForStaff"})
public class ViewFeedBackForStaffServlet extends HttpServlet {

   /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
    throws jakarta.servlet.ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewFeedbackForManager</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewFeedbackForManager at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
    throws jakarta.servlet.ServletException, IOException {
          int rateID =Integer.parseInt(request.getParameter("rateID"));
          String isOK = request.getParameter("isOk");
           
        ProductRatingDAO pDAO = new ProductRatingDAO();
        ProductRating productRating = pDAO.getProductRating(rateID);
        
        int productID = productRating.getProductID();
        

        RatingRepliesDAO rrDAO = new RatingRepliesDAO();
        List<RatingReplies> listReplies = rrDAO.getAllRatingRepliesByRateID(productRating.getRateID());
        
        ProductDAO pdDAO = new ProductDAO();
        Product pro = pdDAO.getProductByID(productID);
      
        CustomerDAO cDAO = new CustomerDAO();
        Customer cus = cDAO.getCustomerbyID(productRating.getCustomerID());
        
                
       request.setAttribute("Product", pro);
       request.setAttribute("cus", cus);
        request.setAttribute("rate", productRating);
        request.setAttribute("dataReplies", listReplies);

        request.getRequestDispatcher("/WEB-INF/View/staff/feedbackManagement/ViewNewFeedback.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
    throws jakarta.servlet.ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
