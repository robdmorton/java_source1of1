<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Random List</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>

<BODY>
<H1>Random List</H1>
<UL>
<% int numEntries = 1 + (int)(Math.random() * 20);
   for(int i=0; i<numEntries; i++) {
     int randomNum = 1 + (int)(Math.random() * 20);
     out.println("<LI>" + randomNum);
   }
%>
</UL>

</BODY>
</HTML>