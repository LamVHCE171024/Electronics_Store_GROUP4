<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>

            body{
                padding: 15px;
            }

            .committed {
                font-size: 14px;
                margin-left: 10px;
                align-self: center;
            }

            .committedImg {
                width: 40px;
                height: 40px;
                object-fit: contain;
            }

            .committedRow {
                width: 50%;
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }

            .committedWrapper {
                display: flex;
                flex-wrap: wrap;
                padding: 10px;
            }

            .committedImgWrapper {
                width: 40px;
                height: 40px;
                flex-shrink: 0;
            }

            h1 {
                font-size: 16px;
            }
        </style>
    </head>
    <body>
        <h1>TShop is committed</h1>

        <%
            if (product != null) {
                String t1 = "", t2 = "", t3 = "", t4 = "", t5 = "";
                String logo1 = "", logo2 = "", logo3 = "", logo4 = "", logo5 = "";

                switch (product.getCategoryId()) {
                    case 1:
                        logo1 = "https://cdnv2.tgdd.vn/pim/cdn/images/202501/icon%20bao%20hanh170435.png";
                        t1 = "1 year remote warranty, 12 years compressor";

                        logo2 = "https://cdnv2.tgdd.vn/pim/cdn/images/202411/stroke104155.png";
                        t2 = "Additional material fees may be incurred";

                        logo3 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/Exchange232102.png";
                        t3 = "What to exchange for 12 months at home (free for the first month)";

                        logo4 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20bao%20hanh170837.png";
                        t4 = "Genuine warranty of 1 year, someone comes to your house";

                        logo5 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20sp%20kem%20theo142836.png";
                        t5 = "Air conditioner box with: Remote, Remote rack";
                        break;

                    case 2:
                        logo1 = "https://cdnv2.tgdd.vn/pim/cdn/images/202411/icon%20bao%20hanh173451.png";
                        t1 = "If used for business activities (laundry, restaurants, hotels, etc.), only 12 months warranty.";

                        logo2 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/Exchange150303.png";
                        t2 = "12 months at home (free for the first month)";

                        logo3 = "https://cdnv2.tgdd.vn/pim/cdn/images/202411/icon%20bao%20hanh173451.png";
                        t3 = "Genuine warranty of 2 years, someone comes to your house";

                        logo4 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20sp%20kem%20theo142836.png";
                        t4 = "Included with the machine are: Warranty card, Instruction manual";

                        logo5 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20lap%20dat140848.png";
                        t5 = "Free installation on delivery";
                        break;

                    case 3:
                        logo1 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20bao%20hanh170837.png";
                        t1 = "12 Month Remote Warranty";

                        logo2 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/Exchange150303.png";
                        t2 = "What to exchange for 12 months at home (free for the first month)";

                        logo3 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20bao%20hanh170837.png";
                        t3 = "Genuine warranty of 2 years, someone comes to your house";

                        logo4 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20sp%20kem%20theo142836.png";
                        t4 = "TV box has: Remote, Stand";

                        logo5 = "https://cdnv2.tgdd.vn/pim/cdn/images/202411/icon%20lap%20dat134827.png";
                        t5 = "Free installation on delivery";
                        break;

                    case 4:
                        logo1 = "https://cdnv2.tgdd.vn/pim/cdn/images/202411/stroke132943.png";
                        t1 = "If it is used for business activities (factory, hotel, laundry,...), it is not covered by the warranty.";

                        logo2 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20bao%20hanh170837.png";
                        t2 = "10-year compressor warranty";

                        logo3 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/Exchange150303.png";
                        t3 = "What to exchange for 12 months at home (free for the first month)";

                        logo4 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20bao%20hanh170837.png";
                        t4 = "Genuine warranty of 2 years, someone comes to your house";

                        logo5 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20lap%20dat140848.png";
                        t5 = "Free installation on delivery";
                        break;

                    case 5:
                        logo1 = "https://cdnv2.tgdd.vn/pim/cdn/images/202411/Delivery101707.png";
                        t1 = "Fast Home Delivery";

                        logo2 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/Exchange232102.png";
                        t2 = "Genuine 2-year warranty at service centers See";

                        logo3 = "https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20bao%20hanh170837.png";
                        t3 = "What to exchange for 12 months at home (free for the first month)";
                        break;

                }
        %>

        <div class="committedWrapper">
            <div class="committedRow">
                <div class="committedImgWrapper">
                    <img class="committedImg" src="<%= logo1%>">
                </div>
                <h2 class="committed"><%= t1%></h2>
            </div>

            <div class="committedRow">
                <div class="committedImgWrapper">
                    <img class="committedImg" src="<%= logo2%>">
                </div>
                <h2 class="committed"><%= t2%></h2>
            </div>

            <div class="committedRow">
                <div class="committedImgWrapper">
                    <img class="committedImg" src="<%= logo3%>">
                </div>
                <h2 class="committed"><%= t3%></h2>
            </div>

            <%
                if (product.getCategoryId() != 5) {
            %>
            <div class="committedRow">
                <div class="committedImgWrapper">
                    <img class="committedImg" src="<%= logo4%>">
                </div>
                <h2 class="committed"><%= t4%></h2>
            </div>

            <div class="committedRow">
                <div class="committedImgWrapper">
                    <img class="committedImg" src="<%= logo5%>">
                </div>
                <h2 class="committed"><%= t5%></h2>
            </div>
            <%}
            %>
        </div>
        <%
            }
        %>
    </body>
</html>
