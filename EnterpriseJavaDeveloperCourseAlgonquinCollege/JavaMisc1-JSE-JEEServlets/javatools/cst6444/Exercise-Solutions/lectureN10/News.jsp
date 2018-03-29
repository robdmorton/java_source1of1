<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>News</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>

<BODY>
<% String newsPage;
   if (Math.random() < 0.5)
     newsPage="GoodNews.jsp";
   else
     newsPage="BadNews.jsp";
%>
<jsp:include page="<%= newsPage %>" flush="true" />
</BODY>
</HTML>