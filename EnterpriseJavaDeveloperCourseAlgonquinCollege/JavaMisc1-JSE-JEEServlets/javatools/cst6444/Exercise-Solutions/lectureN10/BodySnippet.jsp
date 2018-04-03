<% String bgColor = request.getParameter("bgColor");
   if ((bgColor == null) || (bgColor.equals(""))) {
     bgColor = "WHITE";
   }
%>
<BODY BGCOLOR="<%= bgColor %>">