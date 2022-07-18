import java.util.ArrayDeque;
import java.util.Deque;

public class TestProgram {

  public boolean checkFormula(String input) {
    if (input.length() < 1) return false;
    input = input.replaceAll(" ","");
    Deque<Character> numberStack = new ArrayDeque<>();
    Deque<Character> operatorBracketStack = new ArrayDeque<>();
    boolean isTrue = true;

    for ( int i = 0 ; i < input.length(); i++) {
      char temp = input.charAt(i);

      if (isNumber(temp)) {
        if (isTrue) {
          numberStack.push(temp);
          isTrue = false;
        }
      } else if (isOperator(temp)) {
        if (i+1 == input.length()) {
          return false;
        }
        if (input.charAt(i+1) == ')') {
          return false;
        }
        if (temp == '/' || temp == '%') {
          if (i+1 != input.length() && input.charAt(i+1) == '0') {
            return false;
          }
        }
        operatorBracketStack.push(temp);
        isTrue = true;
      } else {
        if (isBracketOpen(temp)) {
          operatorBracketStack.push(temp);
        } else {
          boolean tempBracket = true;
          while (!operatorBracketStack.isEmpty()) {
            char bracket = operatorBracketStack.pop();
            if (bracket == '(') {
              tempBracket = false;
              break;
            } else {
              if (numberStack.isEmpty()) {
                return false;
              } else {
                numberStack.pop();
              }
            }
          }
          if (tempBracket) {
            return false;
          }
        }
      }
    }

    while (!operatorBracketStack.isEmpty()) {
      char temp = operatorBracketStack.pop();

      if (!isOperator(temp)) {
        return false;
      }
      if (numberStack.isEmpty()) {
        return false;
      }
      else {
        numberStack.pop();
      }
    }

    if (numberStack.size() > 1 || !operatorBracketStack.isEmpty()) return false;
    return true;
  }

  public  boolean isNumber(char c) {
    if (c >= 48 && c <= 57) {
      return true;
    }
    return false;
  }

  public boolean isOperator(char c) {
    if (c == '+' || c == '-' || c == '*' || c == '/' || c =='%') {
      return true;
    }
    return false;
  }

  public  boolean isBracketOpen(char c) {
    if(c == '(') {
      return true;
    }
    return false;
  }

  public static void main(String args[]){
    TestProgram tp = new TestProgram();
    System.out.println("3 * (3+5)" + tp.checkFormula("(3 * (3+5))"));
    System.out.println("3 * (3+5))(" + tp.checkFormula("3 * (3+5))("));
    System.out.println("(3 * (3 * (5 - 2)))" + tp.checkFormula("(3 * (3 * (5 - 2)))"));
    System.out.println("((-3+5) + 5%2) + 4)" + tp.checkFormula("((-3+5) + 5%2) + 4)"));
    System.out.println("((-4/a) * 32) + 3" + tp.checkFormula("((-4/a) * 32) + 3"));
    System.out.println("((-4/0) * 32) + 3" + tp.checkFormula("((-4/0) * 32) + 3"));
    System.out.println(" ((-4/3) *+ 32) + 3" + tp.checkFormula(" ((-4/3) *+ 32) + 3"));
    System.out.println("(/(-4/8) *+ 32) + 3" + tp.checkFormula("(/(-4/8) *+ 32) + 3"));
    System.out.println("(*(-4/2) *+ 32) + 3" + tp.checkFormula("(*(-4/2) *+ 32) + 3"));
  }

}
