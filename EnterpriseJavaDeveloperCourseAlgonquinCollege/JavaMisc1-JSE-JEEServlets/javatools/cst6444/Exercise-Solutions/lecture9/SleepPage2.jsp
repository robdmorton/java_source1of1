<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>A Very Slow Page</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>
<%@ page isThreadSafe="false" %>
<BODY>
<H2>A Very Slow Page</H2>
<% try { Thread.sleep(20000); 
   } catch(InterruptedException ie) {}
%>
Gee, this page took a long time...
</BODY>
</HTML>