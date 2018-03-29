<html>
<jsp:useBean id="cart" class="books.BookCart" scope="session"/>
<jsp:setProperty name="cart" property="*" />
  <head>
    <title>Buy My Books!</title>
  </head>
  <body>
    <h2>Which of my books do you want to buy?</h1>
    <form action="BuyMyBook.jsp?book=netfd" method="post">
      <input type="submit" value="Buy" >&nbsp;&nbsp;
      Networking For Dummies<br><br>
    </form>
    <form action="BuyMyBook.jsp?book=netaio" method="post">
      <input type="submit" value="Buy" >&nbsp;&nbsp;
      Networking All-in-One Desk Reference For Dummies<br><br>
    </form>
    <form action="BuyMyBook.jsp?book=wordaio" method="post">
      <input type="submit" value="Buy" >&nbsp;&nbsp;
      Word 2003 All-in-One Desk Reference For Dummies <br><br>
    </form>
    <br><h2>Your cart contains:</h2>
    <jsp:getProperty name="cart" property="list" />
  </body>
</html>