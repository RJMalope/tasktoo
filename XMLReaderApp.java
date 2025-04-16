// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReaderApp {
   public XMLReaderApp() {
   }

   public static void main(String[] var0) {
      try {
         File var1 = new File("C:\\Users\\User\\Desktop\\TaskToo\\tasktoo\\data.xml");
         DocumentBuilderFactory var2 = DocumentBuilderFactory.newInstance();
         var2.setNamespaceAware(true);
         DocumentBuilder var3 = var2.newDocumentBuilder();
         Document var4 = var3.parse(var1);
         var4.getDocumentElement().normalize();
         NodeList var5 = var4.getElementsByTagName("record");

         for(int var6 = 0; var6 < var5.getLength(); ++var6) {
            Node var7 = var5.item(var6);
            if (var7.getNodeType() == 1) {
               Element var8 = (Element)var7;
               String var9 = getTagValue("name", var8);
               String var10 = getTagValue("list", var8);
               System.out.println("Name: " + var9);
               System.out.println("Value: " + var10);
               System.out.println("--------------");
            }
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }

   private static String getTagValue(String var0, Element var1) {
      NodeList var2 = var1.getElementsByTagName(var0);
      if (var2.getLength() == 0) {
         return "";
      } else {
         NodeList var3 = var2.item(0).getChildNodes();
         Node var4 = var3.item(0);
         return var4 != null ? var4.getNodeValue() : "";
      }
   }
}
