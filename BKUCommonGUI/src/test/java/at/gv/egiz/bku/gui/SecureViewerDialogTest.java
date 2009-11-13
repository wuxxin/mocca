/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.gv.egiz.bku.gui;

import at.gv.egiz.stal.impl.ByteArrayHashDataInput;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author clemens
 */
@Ignore
public class SecureViewerDialogTest {

  static SecureViewerDialog secureViewer;
  static ResourceBundle messages;

  @BeforeClass
  public static void setUpClass() throws Exception {
    messages = ResourceBundle.getBundle("at/gv/egiz/bku/gui/Messages");
    secureViewer = new SecureViewerDialog(null, messages,null, null, new DummyFontLoader(), null);
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    @Ignore
    public void testLatin1Supplement() throws UnsupportedEncodingException, FileNotFoundException, IOException {
//      StringBuilder data = new StringBuilder();
//      data.append("\nhttp://www.unicode.org/charts/PDF/U0080.pdf\n");
//      for (int i = 0x0080; i <= 0x00ff; i++) {
//        data.append((char) i);
//      }
//      System.out.println(data.toString());
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream("/home/clemens/IAIK/MOCCA/encoding/test_iso-8859-1.txt"));
      byte[] bytes = new byte[bis.available()];
      bis.read(bytes);
      String s = new String(bytes, "iso-8859-1");
      System.out.println("read iso-8859-1 bytes " + s);

      secureViewer.setContent(new ByteArrayHashDataInput(s.getBytes("UTF-8"), "id-1", "text/plain", "iso-8859-1"));

    }

    @Test
    @Ignore
    public void testGreek() throws UnsupportedEncodingException {
//      Font fm = new Font(Font.MONOSPACED, Font.PLAIN, 10);
      Font fm = new Font("Lucida Sans Typewriter", Font.PLAIN, 18); //GothicBBB-MediumH", Font.PLAIN, 10);
      System.out.println(fm.getFontName() + ", " + fm.getPSName() + ", " + fm);

      StringBuilder data = new StringBuilder();
      data.append("\nhttp://www.unicode.org/charts/PDF/U0370.pdf\n");
      for (int i = 0x0370; i <= 0x03ff; i++) {
        if (!fm.canDisplay((char) i)) {
          System.out.println("cannot display " + Integer.toHexString(i) );
        }
        data.append((char) i);
      }

      System.out.println(data.toString());
      secureViewer.setContent(new ByteArrayHashDataInput(data.toString().getBytes("UTF-8"), "id-1", "text/plain", "UTF-8"));

    }

  /**
   * Test of setContent method, of class SecureViewerDialog.
   */
  @Test
//  @Ignore
  public void testCyrillic() throws UnsupportedEncodingException {

    StringBuilder data = new StringBuilder("\n");
    
    int[] mocca = new int[] {0x041c, 0x04a8, 0x0480, 0x0480, 0x0466 };
    
    for (int i = 0; i < mocca.length; i++) {
      data.append((char) mocca[i]);
    }
    data.append(" goes cyrillic\n");

    data.append("\nCyrillic - http://www.unicode.org/charts/PDF/U0400.pdf\n");
    for (int i = 0x0400; i <= 0x04ff; i++) {
//      System.out.printf("%c%04x=%c\t", (i & 7) == 0 ? '\n' : '\0', i, (char)i);
//      System.out.print((char) i);
      data.append((char)i);
    }

    data.append("\n\nCyrillic Supplement - http://www.unicode.org/charts/PDF/U0500.pdf\n");
    for (int i = 0x0500; i <= 0x0525; i++) {
//      System.out.printf("%c%04x=%c\t", (i & 7) == 0 ? '\n' : '\0', i, (char)i);
//      System.out.print((char) i);
      data.append((char) i);
    }

    for (int i = 0; i < data.length(); i++) {
      char c = data.charAt(i);
      if (c >= '\u0400' && c <= '\u0525') {
        System.out.println(c + "\tcyrillic");
      } else if (c < '\u007f') {
        System.out.println(c + "\tlatin");
      } else {
        System.out.println(c + "\tunknown");
      }
    }

    System.out.println(data.toString());

//    char[] cyrillicChars = new char[] {(char) 0x0411, (char) 0x0444};
//    System.out.println(new String(cyrillicChars)); // + ": " + SMCCHelper.toString(cyrillicBytes));
//    byte[] cyrillicBytes = new byte[] {(byte) 0x11, (byte) 0x04, (byte) 0x0444};
//    System.out.println(new String(cyrillicBytes, "UTF-8") + ": " + SMCCHelper.toString(cyrillicBytes));

//    String encoding = "cp1252";
//    String data = "öäüß€";
//    byte[] bytes = data.getBytes(encoding);
//    System.out.println(data + "\t" +  SMCCHelper.toString(bytes));
//    byte[] bytes2 = data.getBytes("cp1252");
//    System.out.println(data + "\t" +  SMCCHelper.toString(bytes2));

    secureViewer.setContent(new ByteArrayHashDataInput(data.toString().getBytes("UTF-8"), "id-1", "text/plain", "UTF-8"));

    System.out.println("\n\n=============================\n");
//
////    int[] mocca = new int[] {0x0428, 0x0429, 0x04a8, 0x04e8, 0x047a, 0x042d, 0x042d, 0x0421, 0x0421, 0x04d0, 0x0466 };
//    int[] mocca = new int[] {0x0429, 0x04a8, 0x0480, 0x0480, 0x0466 };
    for (int i = 0; i < mocca.length; i++) {
      System.out.print((char) mocca[i]);
    }
//    for (int i = 0; i < mocca.length; i++) {
//      System.out.printf(" 0x%04x", mocca[i]);
//    }
//
    System.out.println("\n=============================\n");

  }

  
}