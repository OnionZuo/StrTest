import java.util.Iterator;
import java.util.TreeSet;

public class Main
{
  public static void main(String []args) {
    String text="<promptValues><name>name1</name><values><item><displayValue>value1</displayValue></item></values></promptValues><promptValues><name>name2</name><values><item><displayValue>value2</displayValue></item></values></promptValues><promptValues><name>name3</name><values><item><displayValue></displayValue></item></values></promptValues><promptValues><name>name4</name><values><item><displayValue>value4</displayValue></item></values></promptValues>";
    String result="";
    
    Main main = new Main();
    result = main.getPromptsByXml(text);
    System.out.println(result);
  }
  
  protected String getPromptsByXml(String str) {
    TreeSet<String> pnList = new TreeSet<>();
    String newPrompt = "";
    
    String promptStart = "<promptValues>";
    String promptEnd = "</promptValues>";
    String prompt = null;
    String promptNameStart = "<name>";
    String promptNameEnd = "</name>";
    String promptName = null;
    String promptValueStart = "<displayValue>";
    String promptValueEnd = "</displayValue>";
    String promptValue = null;
    
    int ps = 0;
    int pe = 0;
    int pns = 0;
    int pne = 0;
    int pvs = 0;
    int pve = 0;
    
    while (str.indexOf(promptStart) != -1) {
      ps = str.indexOf(promptStart) + promptStart.length();
      pe = str.indexOf(promptEnd);
      prompt = str.substring(ps, pe);
      
      pns = prompt.indexOf(promptNameStart) + promptNameStart.length();
      pne = prompt.indexOf(promptNameEnd);
      promptName = prompt.substring(pns, pne);
      
      promptName = promptName.substring(0, 2).replace("p_", "") + promptName.substring(2, promptName.length());
      promptName = promptName.substring(0, 2).replace("P_", "") + promptName.substring(2, promptName.length());
      
      if (prompt.indexOf(promptValueEnd) - prompt.indexOf(promptValueStart) != promptValueStart.length()) {
        newPrompt += "\n" + promptName + ": " + "\n";
        while (prompt.indexOf(promptValueStart) != -1) {
          pvs = prompt.indexOf(promptValueStart) + promptValueStart.length();
          pve = prompt.indexOf(promptValueEnd);
          promptValue = prompt.substring(pvs, pve);
          newPrompt += promptValue + "\n";
          prompt = prompt.substring(pve + promptValueEnd.length(), prompt.length());
        }
      } else {
        pnList.add(promptName);
      }
      
      str = str.substring(pe + promptEnd.length(), str.length());
    }
    for (String pn: pnList) {
      newPrompt += "\n" + pn + ": " + "\n";
      newPrompt += "all" + "\n";
    }
    newPrompt = newPrompt.trim();
    return newPrompt;

  }
}
