<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>The contentType Attribute</TITLE>
</HEAD>

<BODY>
<H2>The contentType Attribute</H2>
This should be rendered as plain text,
<B>not</B> as HTML.

<% String test = "text/plain"; %>
<%@ page contentType= test %>
</BODY>
</HTML>