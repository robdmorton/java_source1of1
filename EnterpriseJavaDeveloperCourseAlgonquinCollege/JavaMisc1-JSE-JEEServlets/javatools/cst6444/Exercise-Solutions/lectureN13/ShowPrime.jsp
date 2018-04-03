<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>A Prime Number</TITLE>
<LINK REL=STYLESHEET
      HREF="/lectureN13/JSP-Styles.css"  <%-- NOTE URL --%>
      TYPE="text/css">
</HEAD>

<BODY>
<H1>A Prime Number</H1>
<jsp:useBean id="primeBean" class="lectureN13.PrimeBean" 
             scope="application" />
<jsp:getProperty name="primeBean" property="prime" />
</BODY>
</HTML>