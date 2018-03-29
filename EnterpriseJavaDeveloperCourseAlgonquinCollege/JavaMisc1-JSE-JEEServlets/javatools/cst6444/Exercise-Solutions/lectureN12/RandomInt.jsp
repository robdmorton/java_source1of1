<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Random Int</TITLE>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</HEAD>

<BODY>
<H2>Some Random Ints</H2>
<%@ taglib uri="lecture12-taglib.tld" prefix="lecture12" %>
<UL>
  <LI><lecture12:randomInt range="10" />
  <LI><lecture12:randomInt range="100" />
  <LI><lecture12:randomInt range="1000" />
  <LI><lecture12:randomInt range="10000" />
  <LI><lecture12:randomInt range="100000" />
  <LI><lecture12:randomInt range="1000000" />
</UL>
</BODY>
</HTML>