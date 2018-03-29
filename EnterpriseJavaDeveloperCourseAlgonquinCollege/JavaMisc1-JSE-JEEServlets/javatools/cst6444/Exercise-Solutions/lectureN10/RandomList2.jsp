<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Random List 2</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>

<BODY>
<H1>Random List 2</H1>
<%-- RandomInt.jsp modified on 1/17/01 --%>
<%@ include file="RandomInt.jsp" %>
<OL>
  <LI><%= randomInt(100) %>
  <LI><%= randomInt(100) %>
  <LI><%= randomInt(100) %>
  <LI><%= randomInt(100) %>
</OL>

</BODY>
</HTML>