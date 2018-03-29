<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- 
Example of using jsp:setProperty and an explicity association
with an input parameter. See SaleEntry1.jsp
and SaleEntry3.jsp for alternatives. 
   
Taken from Core Servlets and JavaServer Pages
from Prentice Hall and Sun Microsystems Press,
http://www.coreservlets.com/.
&copy; 2000 Marty Hall; may be freely used or adapted.
-->
<HTML>
<HEAD>
<TITLE>Using jsp:setProperty</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>

<BODY>

<TABLE BORDER=5 ALIGN="CENTER">
  <TR><TH CLASS="TITLE">
      Using jsp:setProperty</TABLE>

<jsp:useBean id="entry" class="coreservlets.SaleEntry" />

<jsp:setProperty 
    name="entry"
    property="itemID"
    param="itemID" />
    
<jsp:setProperty 
    name="entry"
    property="numItems"
    param="numItems" />
    
<%-- WARNING! Both the JSWDK 1.0.1 and the Java Web Server
              have a bug that makes them fail on double
              type conversions of the following sort.
--%>
<jsp:setProperty 
    name="entry"
    property="discountCode"
    param="discountCode" />

<BR>
<TABLE ALIGN="CENTER" BORDER=1>
<TR CLASS="COLORED">
  <TH>Item ID<TH>Unit Price<TH>Number Ordered<TH>Total Price
<TR ALIGN="RIGHT">
  <TD><jsp:getProperty name="entry" property="itemID" />
  <TD>$<jsp:getProperty name="entry" property="itemCost" />
  <TD><jsp:getProperty name="entry" property="numItems" />
  <TD>$<jsp:getProperty name="entry" property="totalCost" />
</TABLE>
             
</BODY>
</HTML>