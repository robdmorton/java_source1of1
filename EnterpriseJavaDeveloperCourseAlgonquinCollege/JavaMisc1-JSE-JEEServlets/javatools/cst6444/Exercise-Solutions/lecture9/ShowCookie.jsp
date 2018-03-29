<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>The testCookie Cookie</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>

<BODY>
<H2>The testCookie Cookie:
<%@ page import="coreservlets.*" %>
<%= ServletUtilities.getCookieValue(request.getCookies(),
                                    "testCookie", 
                                     "Not set") %>
</H2>

</BODY>
</HTML>