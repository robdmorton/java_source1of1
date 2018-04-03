<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Plugin Applet</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>

<BODY>
<H1>Plugin Applet</H1>

<jsp:plugin type="applet" code="BlueApplet.class" width="500" height="300">
  <jsp:params>
  <jsp:param name="MESSAGE" value="Message from the HTML" />
  </jsp:params>
</jsp:plugin>
</BODY>
</HTML>