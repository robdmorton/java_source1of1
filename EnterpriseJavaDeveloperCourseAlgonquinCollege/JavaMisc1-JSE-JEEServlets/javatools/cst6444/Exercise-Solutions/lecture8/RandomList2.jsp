<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Random List</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>
<%! private int randomInt(int range) {
      return(1 + (int)(Math.random() * range));
    }
%>
<BODY>
<H1>Random List</H1>
<UL>
<% int numEntries = randomInt(20);
   for(int i=0; i<numEntries; i++) {
     out.println("<LI>" + randomInt(20));
   }
%>
</UL>

</BODY>
</HTML>