<%-- 
    Document   : brands
    Created on : Jul 13, 2025, 11:12:55 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="java.util.List"%>
<%@page import="model.Brand"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Brand> brandList = (List<Brand>) request.getAttribute("brandList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- List thương hiệu ngoài modal -->
        <div style="display: flex; width: 100%; gap: 1%;">
            <%
                if (brandList != null) {
                    for (Brand br : brandList) {
            %>
            <div style="margin-top: 1%; width: 6%; border-radius: 8px; background-color: rgba(242, 244, 247, 1); border: 1px solid rgba(242, 244, 247, 1); align-content: center;">
                <a href="FilterProduct?categoryId=<%=br.getCategoryID()%>&brandId=<%=br.getBrandId()%>">
                    <img style="width: 100%; border-radius: 12px;" src="<%= br.getImgUrlLogo()%>">
                </a>
            </div>
            <%
                }
            } else {
            %>
            <p>null</p>
            <% }%>
        </div>
    </body>
</html>
