<html>
<jsp:useBean id="triangle" class="calculators.Triangle" />
<jsp:setProperty name="triangle" property="*" />
  <head>
    <title>Right Triangle Calculator</title>
  </head>
  <body>
    <h1>The Right Triangle Calculator</h1>
    <form action="Triangle.jsp" method="post">
      Side A:&nbsp;
      <input type="text" name="sideA" 
          value="<jsp:getProperty 
                      name="triangle" 
                      property="sideA" />" >
      <br><br>
      Side B:&nbsp;
      <input type="text" name="sideB" 
          value="<jsp:getProperty 
                      name="triangle" 
                      property="sideB" />" >
      <br><br>
      Side C:&nbsp;
      <jsp:getProperty name="triangle" property="sideC" />
      <br><br>
      <input type="submit" value="Calculate" >
    </form>
  </body>
</html>