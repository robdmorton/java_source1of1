<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Baked Bean Test</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>

<BODY>
<H1>Baked Bean Test</H1>
<jsp:useBean id="bean" class="lectureN11.BakedBean" />
<jsp:setProperty name="bean" property="goesWith"
                 value="caviar" />
<H2>Bean level: 
<jsp:getProperty name="bean" property="level" /></H2>
<H2>Dish bean goes with:
<jsp:getProperty name="bean" property="goesWith" /></H2>

</BODY>
</HTML>