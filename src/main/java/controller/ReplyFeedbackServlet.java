/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductRatingDAO;
import dao.RatingRepliesDAO;
import model.Staff;
import model.RatingReplies;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

/**
 *
 *
 */
@WebServlet(name = "ReplyFeedbackServlet", urlPatterns = {"/ReplyFeedback"})
public class ReplyFeedbackServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReplyFeedbackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReplyFeedbackServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // không tạo session mới nếu mất
        Staff staff = (Staff) session.getAttribute("staff");

        if (staff != null) {
            try {
                int rateID = Integer.parseInt(request.getParameter("rateID"));
                String answer = request.getParameter("Answer");

                int stID = staff.getStaffID(); // Lấy trực tiếp từ object Staff

                RatingRepliesDAO rrDAO = new RatingRepliesDAO();              
                int count = rrDAO.addRatingReply(stID, rateID, answer);
                rrDAO.updateisReadComment(rateID);

                if (count > 0) {
                    response.sendRedirect("ViewFeedBackForStaff?rateID=" + rateID + "&success=success");
                } else {
                    response.sendRedirect("ViewFeedBackForStaff?rateID=" + rateID + "&success=failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("ViewFeedBackForStaff?rateID=" + request.getParameter("rateID") + "&success=error");
            }
        } else {
            response.sendRedirect("LoginStaff");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
