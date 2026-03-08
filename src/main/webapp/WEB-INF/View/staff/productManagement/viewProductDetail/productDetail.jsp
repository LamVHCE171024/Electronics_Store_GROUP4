<%-- 
    Document   : productDetail
    Created on : Jun 20, 2025, 3:40:35 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.Product"%>
<%@page import="model.ProductDetail"%>
<%@page import="model.CategoryDetail"%>
<%@page import="model.CategoryDetailGroup"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<CategoryDetailGroup> categoryDetailGroupList = (List<CategoryDetailGroup>) request.getAttribute("categoryGroupList");
    List<CategoryDetail> categoryDetailList = (List<CategoryDetail>) request.getAttribute("categoryDetailList");
    List<ProductDetail> productDetailList = (List<ProductDetail>) request.getAttribute("productDetailList");
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Css/productDetail1.css">
    </head>
    <body>
        <div style="display: flex">
            <h4>Technical specifications</h4>
        </div>
        <div class="container col-md-12" style="background-color: #FFFFFF; border-radius: 15px;">
            <div class="row">
                <div class="col-md-12">
                    <table class="category-table">
                        <%
                            if (categoryDetailGroupList != null) {
                                int groupIndex = 0;
                                for (CategoryDetailGroup cateGroup : categoryDetailGroupList) {
                        %>
                        <!-- Tên nhóm -->
                        <tr class="group-header" onclick="toggleDetails(<%= groupIndex%>)">
                            <td colspan="2" class="group-cell">
                                <div class="group-header-content">
                                    <h2 style="max-width: 50%; word-wrap: break-word; overflow-wrap: break-word; margin: 0;"><%= cateGroup.getNameCategoryDetailsGroup()%></h2>
                                    <span class="arrow-icon" id="arrow<%= groupIndex%>">▼</span>
                                </div>
                            </td>
                        </tr>
                        <tbody id="detailGroup<%= groupIndex%>" class="group-details hidden">
                            <%
                                if (categoryDetailList != null && !categoryDetailList.isEmpty()) {
                                    for (CategoryDetail cateList : categoryDetailList) {
                                        if (cateList.getCategoryDetailsGroupID() == cateGroup.getCategoryDetailsGroupID()) {
                                            boolean hasValue = false;
                            %>
                            <tr>
                                <td class="category-name">
                                    <%= cateList.getCategoryDatailName()%>
                                </td>
                                <td class="attribute-values">
                                    <%
                                        if (productDetailList != null) {
                                            for (ProductDetail proDetail : productDetailList) {
                                                if (proDetail.getCategoryDetailID() == cateList.getCategoryDetailID()) {
                                                    hasValue = true;
                                    %>
                                    <div class="attribute-item"><%= proDetail.getAttributeValue()%></div>
                                    <%
                                                }
                                            }
                                        }
                                        if (!hasValue) {
                                    %>
                                    <div class="attribute-item">No data</div>
                                    <%
                                        }
                                    %>
                                </td>
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>
                        </tbody>
                        <%
                                groupIndex++;
                            }
                        } else {
                        %>
                        <tr><td colspan="2" class="no-data-message">No data</td></tr>
                        <%
                            }
                        %>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>

<script>
    function toggleDetails(index) {
        const detailGroup = document.getElementById("detailGroup" + index);
        const arrowIcon = document.getElementById("arrow" + index);
        if (detailGroup.classList.contains("hidden")) {
            detailGroup.classList.remove("hidden");
            arrowIcon.innerText = "▲";

        } else {
            detailGroup.classList.add("hidden");
            arrowIcon.innerText = "▼";
        }
    }
</script>

<style>
    .btn-success {
        padding: 8px 16px;
        font-size: 14px;
    }
</style>

