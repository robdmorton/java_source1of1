<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>XML Injector</TITLE>
</HEAD>
<BODY>
<H2>XML Input:</H2>
<FORM ACTION="<%=request.getContextPath()%>/BookStoreServlet"
      METHOD="POST">
<TABLE>

  <TR><TD VALIGN="TOP">
      <TD><TEXTAREA ROWS="25" COLS="80" NAME="xml"></TEXTAREA>
  <TR><TD COLSPAN="2" ALIGN="CENTER"><INPUT TYPE="SUBMIT" VALUE="Submit XML">
</TABLE>
</FORM>
</BODY></HTML>