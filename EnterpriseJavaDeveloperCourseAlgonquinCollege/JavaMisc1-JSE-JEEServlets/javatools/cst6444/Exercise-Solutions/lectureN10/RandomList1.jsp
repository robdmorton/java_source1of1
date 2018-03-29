<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Random List 1</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>

<BODY>
<H1>Random List 1</H1>
<%-- RandomInt.jsp modified on 1/17/01 --%>
<%@ include file="RandomInt.jsp" %>
<UL>
  <LI><%= randomInt(10) %>
  <LI><%= randomInt(10) %>
  <LI><%= randomInt(10) %>
</UL>
</BODY>
</HTML>