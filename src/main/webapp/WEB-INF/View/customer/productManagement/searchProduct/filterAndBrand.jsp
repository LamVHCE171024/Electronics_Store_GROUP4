<!-- ... phần đầu không đổi ... -->
<%@page import="model.Brand"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Brand> brandList = (List<Brand>) request.getAttribute("brandList");
    int brandId = 0;
    int categoryId = 0;
    if (brandList != null){
        brandId = brandList.get(0).getBrandId();
        categoryId = brandList.get(0).getCategoryID();
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Filter & Brand</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            .filter {
                border: 1px solid #2a83e9;
                border-radius: 8px;
                cursor: pointer;
                padding: 8px 12px;
                background: #fff;
                color: #4a90e2;
            }

            /* Brand selected radio effect */
            label input[type="radio"]:checked + img {
                border: 2px solid #2a83e9;
                border-radius: 8px;
                box-shadow: 0 0 5px rgba(42, 131, 233, 0.6);
                padding: 2px;
                background-color: #e8f1fd;
            }

            .price-pill {
                display: inline-flex;
                align-items: center;
                justify-content: center;
                border: 2px solid #ccc;
                border-radius: 999px;
                padding: 6px 14px;
                cursor: pointer;
                transition: all 0.2s ease;
                background-color: #f9f9f9;
                font-size: 14px;
            }


            .price-pill.checked {
                background-color: #e6f1ff;
                border: 2px solid #2a83e9;
            }
        </style>
    </head>
    <body>
        <div style="display: flex; gap: 1%;">
            <!-- Filter Button -->
            <div style="align-content: center; margin-top: 1%;">
                <button class="filter" onclick="openModal()" style="display: flex;">
                    <i class="fas fa-filter" style="margin-right: 3px; color: #333; align-content: center"></i>
                    <span style = "align-content: center">Filter</span>
                </button>
            </div>

            <!-- Modal -->
            <div id="filterModal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background-color: rgba(0,0,0,0.4); z-index:1000;">
                <div style="background-color:#fff; padding: 20px; border-radius: 10px; width: 40%; margin: 5% auto;">
                    <h3>Chọn hãng và giá</h3>
                    <form action="SortProduct?categoryId=<%= categoryId%>&brandId=<%= brandId%>" method="get">
                        <div>
                            <p><strong>Hãng:</strong></p>
                            <div style="display: flex; flex-wrap: wrap; gap: 10px; margin-top: 10px;">
                                <%
                                    if (brandList != null) {
                                        for (Brand br : brandList) {
                                %>
                                <label style="width: 100px; height: 60px; display: flex; justify-content: center; align-items: center; border-radius: 10px; padding: 5px; cursor: pointer;">
                                    <input type="radio" name="brandcategory" value="<%=br.getBrandId()%>-<%=br.getCategoryID()%>"; style="display: none;">
                                    <img src="<%=br.getImgUrlLogo()%>" alt="<%=br.getBrandName()%>" style="max-width: 100%; max-height: 100%; object-fit: contain;">
                                </label>
                                <%
                                        }
                                    }
                                %>
                            </div>
                        </div>

                        <!-- Price Section -->
                        <div style="display: flex; flex-wrap: wrap; gap: 10px; margin-top: 10px;">
                            <label class="price-pill" onclick="selectRadio(this)">
                                <input type="radio" name="priceRange" value="under7" hidden>
                                <span>Less than 7 million</span>
                            </label>

                            <label class="price-pill" onclick="selectRadio(this)">
                                <input type="radio" name="priceRange" value="7to9" hidden>
                                <span>7 - 9 million</span>
                            </label>

                            <label class="price-pill" onclick="selectRadio(this)">
                                <input type="radio" name="priceRange" value="9to12" hidden>
                                <span>9 - 12 million</span>
                            </label>

                            <label class="price-pill" onclick="selectRadio(this)">
                                <input type="radio" name="priceRange" value="12to15" hidden>
                                <span>12 - 15 million</span>
                            </label>

                            <label class="price-pill" onclick="selectRadio(this)">
                                <input type="radio" name="priceRange" value="15to20" hidden>
                                <span>15 - 20 million</span>
                            </label>

                            <label class="price-pill" onclick="selectRadio(this)">
                                <input type="radio" name="priceRange" value="above20" hidden>
                                <span>Above 20 million</span>
                            </label>
                        </div>

                        <!-- Buttons -->
                        <div style="margin-top: 20px;">
                            <button type="submit" class="filter">Áp dụng</button>
                            <button type="button" class="filter" onclick="closeModal()" style="margin-left: 10px;">Đóng</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- JavaScript -->
            <script>
                function openModal() {
                    document.getElementById('filterModal').style.display = 'block';
                }

                function closeModal() {
                    document.getElementById('filterModal').style.display = 'none';
                }

                window.onclick = function (event) {
                    let modal = document.getElementById('filterModal');
                    if (event.target === modal) {
                        modal.style.display = "none";
                    }
                }

                function selectRadio(label) {
                    // Bỏ class checked của tất cả label cùng name
                    const all = document.querySelectorAll('input[name="priceRange"]');
                    all.forEach(input => {
                        if (input.closest('label')) {
                            input.closest('label').classList.remove('checked');
                            input.checked = false;
                        }
                    });

                    // Thêm checked cho label hiện tại
                    const input = label.querySelector('input');
                    input.checked = true;
                    label.classList.add('checked');
                }


            </script>

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
        </div>
    </body>
</html>
