package CST6526IntermediateJavaProgramming.lab3;

import java.util.StringTokenizer;

public class StringTest
{

  
  public static StringBuilder subString(String str) {
    StringBuilder sb = new StringBuilder();
    return sb.append(str.substring(str.indexOf(' ')-1, str.indexOf(' ')+2));
  }

  public static StringBuilder reverseString1(String str) {
    StringBuilder sb=new StringBuilder();
    sb.append(str);

    sb.reverse();
    
    return sb;
  }

  public static StringBuilder reverseString2(String str) {
    StringBuilder sb=new StringBuilder();
    StringBuilder sbReturn=new StringBuilder();
    sb.append(str);
    
    int i = 0;
    for(i=sb.length()-1;i>=0;i--)
    {
      sbReturn.append(sb.charAt(i));
    }
    
    return sbReturn;
  }

  public static void printEachWordInStringOnNewLine(String str) {
    StringBuilder sb=new StringBuilder();
    sb.append(str);
    
    int idx=0,next=0;
    while(idx<sb.length())
    {
      int nextIdx = sb.indexOf(" ",next);
      if(nextIdx != -1)
      {
        System.out.println(sb.substring(next, nextIdx));
        next = sb.indexOf(" ",next)+1;
        idx = next;
      }
      else
      {
        System.out.println(sb.substring(next));
        idx = sb.length();
      }
    }
    
  }

  public static void printEachWordInStringOnNewLine2(String str) {
    StringTokenizer tok = new StringTokenizer(str);
    if (tok.countTokens() == 0) {
        return; // no valid values
    }
    while (tok.hasMoreTokens()) {
        String word = tok.nextToken();
        System.out.println(word);
    }
    
  }

  public static void main(String[] args) 
  {
    System.out.println(subString("Hello there"));
    System.out.println(reverseString1("Hello there"));
    System.out.println(reverseString2("Hello there"));
//    printEachWordInStringOnNewLine("Hello there me bye");
    printEachWordInStringOnNewLine2("Hello there me bye");
  }


}
