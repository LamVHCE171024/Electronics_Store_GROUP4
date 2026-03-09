<%@page import="model.CategoryDetail"%>
<%@page import="model.CategoryDetailGroup"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<CategoryDetailGroup> categoryDetailGroupList = (List<CategoryDetailGroup>) request.getAttribute("categoryGroupList");
    List<CategoryDetail> categoryDetailList = (List<CategoryDetail>) request.getAttribute("categoryDetailList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>
        <link rel="stylesheet" href="Css/productDetail1.css">
    </head>
    <body>
        <div style="width: 100%">
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
                            <h2><%= cateGroup.getNameCategoryDetailsGroup()%></h2>
                            <span class="arrow-icon" id="arrow<%= groupIndex%>">▼</span>
                        </div>
                    </td>
                </tr>
                <tbody id="detailGroup<%= groupIndex%>" class="group-details hidden">
                    <%
                        if (categoryDetailList != null && !categoryDetailList.isEmpty()) {
                            for (CategoryDetail cateList : categoryDetailList) {
                                if (cateList.getCategoryDetailsGroupID() == cateGroup.getCategoryDetailsGroupID()) {
                    %>
                    <tr>
                        <td class="category-name">
                            <%= cateList.getCategoryDatailName()%>
                        </td>
                        <td class="attribute-values">
                            <input type="text"
                                   style="width: auto; max-width: 190px; background-color: none;"
                                   class="attribute-input"
                                   name="attribute_<%= cateList.getCategoryDetailID()%>">
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

        <script>
            function toggleDetails(index) {
                const detailGroup = document.getElementById("detailGroup" + index);
                const arrowIcon = document.getElementById("arrow" + index);

                if (detailGroup.classList.contains("hidden")) {
                    detailGroup.classList.remove("hidden");
                    arrowIcon.innerText = "▲"; // hoặc dùng ▾ nếu thích
                } else {
                    detailGroup.classList.add("hidden");
                    arrowIcon.innerText = "▼";
                }
            }
        </script>
    </body>
</html>
