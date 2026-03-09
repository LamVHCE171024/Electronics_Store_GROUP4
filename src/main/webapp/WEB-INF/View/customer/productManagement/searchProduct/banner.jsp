<%-- 
    Document   : banner
    Created on : Jul 12, 2025, 6:03:29 PM
    Author     : HP - Gia Khiêm
--%>

<%@page import="model.Brand"%>
<%@page import="java.util.List"%>
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
        <%
            if (brandList != null) {
                String banner1 = null;
                String banner2 = null;
                Brand br = brandList.get(0);
                switch (br.getCategoryID()) {
                    case 1:
                        banner1 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/fb/74/fb74632c62b3fa78b974fc4c3d737433.png";
                        banner2 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/ef/dd/efddab4db919e5f678367d599c64be42.png";
                        break;
                    case 2:
                        banner1 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/8f/fd/8ffd049e153449d4c64e748dd342d7f9.png";
                        banner2 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/cf/35/cf35c359dbbdc83e3c4337b9d2f0dec8.png";
                        break;
                    case 3:
                        banner1 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/f4/c5/f4c5f6d61359b32152a7012ecc6b5bf2.png";
                        banner2 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/5d/55/5d55b7c7b9671bf770c422de789c95ab.png";
                        break;
                    case 4:
                        banner1 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/e1/96/e1966ec7e26c50f7e721c47de654ff7b.png";
                        banner2 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/d2/d1/d2d17936e0d736c7e34c9bbc009666b4.png";
                        break;
                    case 5:
                        banner1 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/84/07/8407b345ec365ee66445938e8fe080ee.png";
                        banner2 = "https://cdnv2.tgdd.vn/mwg-static/dmx/Banner/d9/64/d96429099d3b2854a744430125ab360b.png";
                        break;
                }
        %>
        <div style = "width: 100%; gap: 0.5%; display: flex; ">

            <div style = "width: 49.5%; margin-left: 0.25%">
                <img style = "width: 100%; border-radius: 12px;" src = "<%= banner1%>">
            </div>

            <div style = "width: 49.5%; margin-right: 0.25%">
                <img style = "width: 100%; border-radius: 12px;" src = "<%= banner2%>">
            </div>

        </div>
        <%
            }
        %>
    </body>
</html>
