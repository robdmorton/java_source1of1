<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Session Data</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>
<%! private String checkVal(Object orig, String replacement) {
      if (orig != null)
        return((String)orig);
      else
        return(replacement);
    }
%>

<BODY>
<H1>Session Data</H1>
<UL>
  <LI><B>Repeat visitor:</B> 
      <%= checkVal(session.getAttribute("repeatVisitor"), "no") %>
  <LI><B>First name:</B> 
      <%= checkVal(session.getAttribute("firstName"), "Unknown") %>
  <LI><B>Last name:</B> 
      <%= checkVal(session.getAttribute("lastName"), "Unknown") %>
  <LI><B>Email address:</B> 
      <%= checkVal(session.getAttribute("emailAddress"), "Unknown") %>
</UL>
</BODY>
</HTML>