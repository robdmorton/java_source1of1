<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleBookSearchEngineProxyid" scope="session" class="ws.business.BookSearchEngineProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
sampleBookSearchEngineProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = sampleBookSearchEngineProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 5:
        gotMethod = true;
        String endpoint_0id=  request.getParameter("endpoint8");
            java.lang.String endpoint_0idTemp = null;
        if(!endpoint_0id.equals("")){
         endpoint_0idTemp  = endpoint_0id;
        }
        sampleBookSearchEngineProxyid.setEndpoint(endpoint_0idTemp);
break;
case 10:
        gotMethod = true;
        ws.business.BookSearchEngine getBookSearchEngine10mtemp = sampleBookSearchEngineProxyid.getBookSearchEngine();
if(getBookSearchEngine10mtemp == null){
%>
<%=getBookSearchEngine10mtemp %>
<%
}else{
        if(getBookSearchEngine10mtemp!= null){
        String tempreturnp11 = getBookSearchEngine10mtemp.toString();
        %>
        <%=tempreturnp11%>
        <%
        }}
break;
case 13:
        gotMethod = true;
        String xml_1id=  request.getParameter("xml24");
            java.lang.String xml_1idTemp = null;
        if(!xml_1id.equals("")){
         xml_1idTemp  = xml_1id;
        }
        ws.business.BookSearchResult searchBookByTitle13mtemp = sampleBookSearchEngineProxyid.searchBookByTitle(xml_1idTemp);
if(searchBookByTitle13mtemp == null){
%>
<%=searchBookByTitle13mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">genre:</TD>
<TD>
<%
if(searchBookByTitle13mtemp != null){
java.lang.String typegenre16 = searchBookByTitle13mtemp.getGenre();
        String tempResultgenre16 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typegenre16));
        %>
        <%= tempResultgenre16 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">author:</TD>
<TD>
<%
if(searchBookByTitle13mtemp != null){
java.lang.String typeauthor18 = searchBookByTitle13mtemp.getAuthor();
        String tempResultauthor18 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeauthor18));
        %>
        <%= tempResultauthor18 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">quantity:</TD>
<TD>
<%
if(searchBookByTitle13mtemp != null){
%>
<%=searchBookByTitle13mtemp.getQuantity()
%><%}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">title:</TD>
<TD>
<%
if(searchBookByTitle13mtemp != null){
java.lang.String typetitle22 = searchBookByTitle13mtemp.getTitle();
        String tempResulttitle22 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typetitle22));
        %>
        <%= tempResulttitle22 %>
        <%
}%>
</TD>
</TABLE>
<%
}
break;
case 26:
        gotMethod = true;
        String genre_2id=  request.getParameter("genre37");
            java.lang.String genre_2idTemp = null;
        if(!genre_2id.equals("")){
         genre_2idTemp  = genre_2id;
        }
        ws.business.BookSearchResult searchBookByGenre26mtemp = sampleBookSearchEngineProxyid.searchBookByGenre(genre_2idTemp);
if(searchBookByGenre26mtemp == null){
%>
<%=searchBookByGenre26mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">genre:</TD>
<TD>
<%
if(searchBookByGenre26mtemp != null){
java.lang.String typegenre29 = searchBookByGenre26mtemp.getGenre();
        String tempResultgenre29 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typegenre29));
        %>
        <%= tempResultgenre29 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">author:</TD>
<TD>
<%
if(searchBookByGenre26mtemp != null){
java.lang.String typeauthor31 = searchBookByGenre26mtemp.getAuthor();
        String tempResultauthor31 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typeauthor31));
        %>
        <%= tempResultauthor31 %>
        <%
}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">quantity:</TD>
<TD>
<%
if(searchBookByGenre26mtemp != null){
%>
<%=searchBookByGenre26mtemp.getQuantity()
%><%}%>
</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">title:</TD>
<TD>
<%
if(searchBookByGenre26mtemp != null){
java.lang.String typetitle35 = searchBookByGenre26mtemp.getTitle();
        String tempResulttitle35 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(typetitle35));
        %>
        <%= tempResulttitle35 %>
        <%
}%>
</TD>
</TABLE>
<%
}
break;
case 39:
        gotMethod = true;
        String genre_3id=  request.getParameter("genre42");
            java.lang.String genre_3idTemp = null;
        if(!genre_3id.equals("")){
         genre_3idTemp  = genre_3id;
        }
        ws.business.BookSearchResult[] findAllBooksByGenre39mtemp = sampleBookSearchEngineProxyid.findAllBooksByGenre(genre_3idTemp);
if(findAllBooksByGenre39mtemp == null){
%>
<%=findAllBooksByGenre39mtemp %>
<%
}else{
        String tempreturnp40 = null;
        if(findAllBooksByGenre39mtemp != null){
        java.util.List listreturnp40= java.util.Arrays.asList(findAllBooksByGenre39mtemp);
        tempreturnp40 = listreturnp40.toString();
        }
        %>
        <%=tempreturnp40%>
        <%
}
break;
}
} catch (Exception e) { 
%>
exception: <%= e %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>