package at.gv.egiz.stal.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class TestSignatureData {
  
  protected final static Log log = LogFactory.getLog(TestSignatureData.class);
  
  public static final String[] ID = new String[] {"signed-data-reference-0-1214921968-27971781-24309", "signed-data-reference-1"};
  public static final String ENCODING = "UTF-8";
  
  public static final Map<String, byte[]> HASHDATA_INPUT = new HashMap<String, byte[]>();
  static {
    try {
      HASHDATA_INPUT.put(ID[0], "Ich bin ein einfacher Text. ll�����".getBytes(ENCODING));
      HASHDATA_INPUT.put(ID[1], "2te referenz".getBytes(ENCODING));
    } catch (UnsupportedEncodingException ex) {
      log.error("failed to init signature test data", ex);
    }
  }
  public static final Map<String, String> HASHDATA_MIMETYPES = new HashMap<String, String>();
  static {
      HASHDATA_MIMETYPES.put(ID[0], "text/plain");
      HASHDATA_MIMETYPES.put(ID[1], "any/mime-type");
  }
  
//  private static final byte[] signedInfo = "<dsig:SignedInfo  xmlns:dsig=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\"><dsig:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /> <dsig:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1\" /> <dsig:Reference Id=\"signed-data-reference-0-1214921968-27971781-24309\" URI=\"#signed-data-object-0-1214921968-27971781-13578\"><dsig:Transforms> <dsig:Transform Algorithm=\"http://www.w3.org/2002/06/xmldsig-filter2\"> <xpf:XPath xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\" Filter=\"intersect\">id('signed-data-object-0-1214921968-27971781-13578')/node()</xpf:XPath></dsig:Transform></dsig:Transforms><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /> <dsig:DigestValue>H1IePEEfGQ2SG03H6LTzw1TpCuM=</dsig:DigestValue></dsig:Reference><dsig:Reference Id=\"etsi-data-reference-0-1214921968-27971781-25439\" Type=\"http://uri.etsi.org/01903/v1.1.1#SignedProperties\" URI=\"#xmlns(etsi=http://uri.etsi.org/01903/v1.1.1%23)%20xpointer(id('etsi-data-object-0-1214921968-27971781-3095')/child::etsi:QualifyingProperties/child::etsi:SignedProperties)\"><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><dsig:DigestValue>yV6Q+I60buqR4mMaxA7fi+CV35A=</dsig:DigestValue></dsig:Reference></dsig:SignedInfo>".getBytes();
//  private static final byte[] signedInfo2Ref = "<dsig:SignedInfo  xmlns:dsig=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\"><dsig:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /> <dsig:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1\" /> <dsig:Reference Id=\"signed-data-reference-0-1214921968-27971781-24309\" URI=\"#signed-data-object-0-1214921968-27971781-13578\"><dsig:Transforms> <dsig:Transform Algorithm=\"http://www.w3.org/2002/06/xmldsig-filter2\"> <xpf:XPath xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\" Filter=\"intersect\">id('signed-data-object-0-1214921968-27971781-13578')/node()</xpf:XPath></dsig:Transform></dsig:Transforms><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /> <dsig:DigestValue>H1IePEEfGQ2SG03H6LTzw1TpCuM=</dsig:DigestValue></dsig:Reference><dsig:Reference Id=\"signed-data-reference-1\" URI=\"#signed-data-object-0-1214921968-27971781-13578\"><dsig:Transforms> <dsig:Transform Algorithm=\"http://www.w3.org/2002/06/xmldsig-filter2\"> <xpf:XPath xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\" Filter=\"intersect\">id('signed-data-object-1')/node()</xpf:XPath></dsig:Transform></dsig:Transforms><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /> <dsig:DigestValue>H1IePEEfGQ2SG03H6LTzw1TpCuM=</dsig:DigestValue></dsig:Reference><dsig:Reference Id=\"etsi-data-reference-0-1214921968-27971781-25439\" Type=\"http://uri.etsi.org/01903/v1.1.1#SignedProperties\" URI=\"#xmlns(etsi=http://uri.etsi.org/01903/v1.1.1%23)%20xpointer(id('etsi-data-object-0-1214921968-27971781-3095')/child::etsi:QualifyingProperties/child::etsi:SignedProperties)\"><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><dsig:DigestValue>yV6Q+I60buqR4mMaxA7fi+CV35A=</dsig:DigestValue></dsig:Reference></dsig:SignedInfo>".getBytes();
//  private static final String signedInfo2Ref = "<dsig:Reference Id=\"signed-data-reference-1\" URI=\"#signed-data-object-0-1214921968-27971781-13578\"><dsig:Transforms> <dsig:Transform Algorithm=\"http://www.w3.org/2002/06/xmldsig-filter2\"> <xpf:XPath xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\" Filter=\"intersect\">id('signed-data-object-1')/node()</xpf:XPath></dsig:Transform></dsig:Transforms><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /> <dsig:DigestValue>H1IePEEfGQ2SG03H6LTzw1TpCuM=</dsig:DigestValue></dsig:Reference>";
  /**
   * SIGNED_INFO[0] contains reference ID[0]
   * SIGNED_INFO[1] contains reference ID[0] and ID[1]
   */
  public static final List<byte[]> SIGNED_INFO = new ArrayList<byte[]>(); 
  static {
    SIGNED_INFO.add("<dsig:SignedInfo  xmlns:dsig=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\"><dsig:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /> <dsig:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1\" /> <dsig:Reference Id=\"signed-data-reference-0-1214921968-27971781-24309\" URI=\"#signed-data-object-0-1214921968-27971781-13578\"><dsig:Transforms> <dsig:Transform Algorithm=\"http://www.w3.org/2002/06/xmldsig-filter2\"> <xpf:XPath xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\" Filter=\"intersect\">id('signed-data-object-0-1214921968-27971781-13578')/node()</xpf:XPath></dsig:Transform></dsig:Transforms><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /> <dsig:DigestValue>H1IePEEfGQ2SG03H6LTzw1TpCuM=</dsig:DigestValue></dsig:Reference><dsig:Reference Id=\"etsi-data-reference-0-1214921968-27971781-25439\" Type=\"http://uri.etsi.org/01903/v1.1.1#SignedProperties\" URI=\"#xmlns(etsi=http://uri.etsi.org/01903/v1.1.1%23)%20xpointer(id('etsi-data-object-0-1214921968-27971781-3095')/child::etsi:QualifyingProperties/child::etsi:SignedProperties)\"><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><dsig:DigestValue>yV6Q+I60buqR4mMaxA7fi+CV35A=</dsig:DigestValue></dsig:Reference></dsig:SignedInfo>".getBytes());
    SIGNED_INFO.add("<dsig:SignedInfo  xmlns:dsig=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\"><dsig:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" /> <dsig:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1\" /> <dsig:Reference Id=\"signed-data-reference-0-1214921968-27971781-24309\" URI=\"#signed-data-object-0-1214921968-27971781-13578\"><dsig:Transforms> <dsig:Transform Algorithm=\"http://www.w3.org/2002/06/xmldsig-filter2\"> <xpf:XPath xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\" Filter=\"intersect\">id('signed-data-object-0-1214921968-27971781-13578')/node()</xpf:XPath></dsig:Transform></dsig:Transforms><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /> <dsig:DigestValue>H1IePEEfGQ2SG03H6LTzw1TpCuM=</dsig:DigestValue></dsig:Reference><dsig:Reference Id=\"signed-data-reference-1\" URI=\"#signed-data-object-0-1214921968-27971781-13578\"><dsig:Transforms> <dsig:Transform Algorithm=\"http://www.w3.org/2002/06/xmldsig-filter2\"> <xpf:XPath xmlns:xpf=\"http://www.w3.org/2002/06/xmldsig-filter2\" Filter=\"intersect\">id('signed-data-object-1')/node()</xpf:XPath></dsig:Transform></dsig:Transforms><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /> <dsig:DigestValue>H1IePEEfGQ2SG03H6LTzw1TpCuM=</dsig:DigestValue></dsig:Reference><dsig:Reference Id=\"etsi-data-reference-0-1214921968-27971781-25439\" Type=\"http://uri.etsi.org/01903/v1.1.1#SignedProperties\" URI=\"#xmlns(etsi=http://uri.etsi.org/01903/v1.1.1%23)%20xpointer(id('etsi-data-object-0-1214921968-27971781-3095')/child::etsi:QualifyingProperties/child::etsi:SignedProperties)\"><dsig:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" /><dsig:DigestValue>yV6Q+I60buqR4mMaxA7fi+CV35A=</dsig:DigestValue></dsig:Reference></dsig:SignedInfo>".getBytes());
  }
  
}
