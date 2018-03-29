<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Random Number</TITLE>
<LINK REL=STYLESHEET
      HREF="/lectureN13/JSP-Styles.css"  <%-- NOTE URL --%>
      TYPE="text/css">
</HEAD>

<BODY>
<jsp:useBean id="randomNum" class="lectureN13.NumberBean" 
             scope="request" />
<H2>Random Number:
<jsp:getProperty name="randomNum" property="number" />
</H2>

</BODY>
</HTML>