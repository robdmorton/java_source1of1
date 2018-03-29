<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Thanks for Registering</TITLE>
<LINK REL=STYLESHEET
      HREF="/lectureN13/JSP-Styles.css"  <%-- NOTE URL --%>
      TYPE="text/css">
</HEAD>

<BODY>
<H1>Thanks for Registering</H1>
<jsp:useBean id="nameBean" class="lectureN13.NameBean" 
             scope="session" />
<H2>First Name:
<jsp:getProperty name="nameBean" property="firstName" />
</H2>
<H2>Last Name:
<jsp:getProperty name="nameBean" property="lastName" />
</H2>
</BODY>
</HTML>