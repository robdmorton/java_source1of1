<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Random Background</TITLE>
</HEAD>
<%! private int randomInt(int range) {
      return(1 + (int)(Math.random() * range));
    }
    
    private String[] colors = { "GREEN", "RED", "BLUE", "YELLOW" };
%>

<BODY BGCOLOR="<%= colors[randomInt(colors.length) - 1] %>">
<H1>Random Background</H1>

</BODY>
</HTML>