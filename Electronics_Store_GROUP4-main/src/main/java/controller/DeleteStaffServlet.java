package controller;

import dao.StaffDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteStaffServlet", urlPatterns = {"/DeleteStaffServlet"})
public class DeleteStaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            try {
                int staffId = Integer.parseInt(request.getParameter("id"));
                StaffDAO staffDAO = new StaffDAO();
                boolean isSuccess = staffDAO.deleteStaffById(staffId); // Äáº£m báº£o phÆ°Æ¡ng thá»©c nÃ y Ä‘Ãºng
                if (isSuccess) {
                    response.sendRedirect("StaffList?successdelete=1");
                } else {
                    response.sendRedirect("StaffList?errordelete=1");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid staff ID.");
                request.getRequestDispatcher("/WEB-INF/View/admin/staffManagement/staffList.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("StaffList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Handles staff deletion requests";
    }
}

