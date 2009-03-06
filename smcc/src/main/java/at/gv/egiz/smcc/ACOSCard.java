//Copyright (C) 2002 IAIK
//http://jce.iaik.at
//
//Copyright (C) 2003 Stiftung Secure Information and 
//                 Communication Technologies SIC
//http://www.sic.st
//
//All rights reserved.
//
//This source is provided for inspection purposes and recompilation only,
//unless specified differently in a contract with IAIK. This source has to
//be kept in strict confidence and must not be disclosed to any third party
//under any circumstances. Redistribution in source and binary forms, with
//or without modification, are <not> permitted in any case!
//
//THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
//ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
//IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
//ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
//FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
//DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
//OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
//HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
//LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
//OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
//SUCH DAMAGE.
//
//
package at.gv.egiz.smcc;

import at.gv.egiz.smcc.util.SMCCHelper;
import java.nio.charset.Charset;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ACOSCard extends AbstractSignatureCard implements SignatureCard {

  private static Log log = LogFactory.getLog(ACOSCard.class);

  public static final byte[] AID_DEC = new byte[] { (byte) 0xA0, (byte) 0x00,
      (byte) 0x00, (byte) 0x01, (byte) 0x18, (byte) 0x45, (byte) 0x4E };

  public static final byte[] DF_DEC = new byte[] { (byte) 0xdf, (byte) 0x71 };

  public static final byte[] AID_SIG = new byte[] { (byte) 0xA0, (byte) 0x00,
      (byte) 0x00, (byte) 0x01, (byte) 0x18, (byte) 0x45, (byte) 0x43 };

  public static final byte[] DF_SIG = new byte[] { (byte) 0xdf, (byte) 0x70 };

  public static final byte[] EF_C_CH_EKEY = new byte[] { (byte) 0xc0,
      (byte) 0x01 };

  public static final int EF_C_CH_EKEY_MAX_SIZE = 2000;

  public static final byte[] EF_C_CH_DS = new byte[] { (byte) 0xc0, (byte) 0x02 };

  public static final int EF_C_CH_DS_MAX_SIZE = 2000;

  public static final byte[] EF_PK_CH_EKEY = new byte[] { (byte) 0xb0,
      (byte) 0x01 };

  public static final byte[] EF_INFOBOX = new byte[] { (byte) 0xc0, (byte) 0x02 };

  public static final int EF_INFOBOX_MAX_SIZE = 1500;

  public static final byte KID_PIN_SIG = (byte) 0x81;

  public static final byte KID_PIN_DEC = (byte) 0x81;

  public static final byte KID_PIN_INF = (byte) 0x83;

  public static final byte[] DST_SIG = new byte[] { (byte) 0x84, (byte) 0x01, // tag
      // ,
      // length
      // (
      // key
      // ID
      // )
      (byte) 0x88, // SK.CH.SIGN
      (byte) 0x80, (byte) 0x01, // tag, length (algorithm ID)
      (byte) 0x14 // ECDSA
  };

  public static final byte[] DST_DEC = new byte[] { (byte) 0x84, (byte) 0x01, // tag
      // ,
      // length
      // (
      // key
      // ID
      // )
      (byte) 0x88, // SK.CH.EKEY
      (byte) 0x80, (byte) 0x01, // tag, length (algorithm ID)
      (byte) 0x01 // RSA // TODO: Not verified yet
  };

  private static final int PINSPEC_INF = 0;
  private static final int PINSPEC_DEC = 1;
  private static final int PINSPEC_SIG = 2;

  public ACOSCard() {
    super("at/gv/egiz/smcc/ACOSCard");
    pinSpecs.add(PINSPEC_INF,
            new PINSpec(0, 8, "[0-9]", getResourceBundle().getString("inf.pin.name"), KID_PIN_INF, AID_DEC));
    pinSpecs.add(PINSPEC_DEC, 
            new PINSpec(0, 8, "[0-9]", getResourceBundle().getString("dec.pin.name"), KID_PIN_DEC, AID_DEC));
    pinSpecs.add(PINSPEC_SIG, 
            new PINSpec(0, 8, "[0-9]", getResourceBundle().getString("sig.pin.name"), KID_PIN_SIG, AID_SIG));
  }

  /* (non-Javadoc)
   * @see at.gv.egiz.smcc.SignatureCard#getCertificate(at.gv.egiz.smcc.SignatureCard.KeyboxName)
   */
  @Override
  public byte[] getCertificate(KeyboxName keyboxName)
      throws SignatureCardException, InterruptedException {
  
    try {
      
      if (keyboxName == KeyboxName.SECURE_SIGNATURE_KEYPAIR) {
        
        try {
          getCard().beginExclusive();
          byte[] certificate = readTLVFile(AID_SIG, EF_C_CH_DS, EF_C_CH_DS_MAX_SIZE);
          if (certificate == null) {
            throw new NotActivatedException();
          }
          return certificate;
        } catch (FileNotFoundException e) {
          throw new NotActivatedException();
        } finally {
          getCard().endExclusive();
        }
        
      } else if (keyboxName == KeyboxName.CERITIFIED_KEYPAIR) {
        
        try {
          getCard().beginExclusive();
          byte[] certificate = readTLVFile(AID_DEC, EF_C_CH_EKEY, EF_C_CH_EKEY_MAX_SIZE);
          if (certificate == null) {
            throw new NotActivatedException();
          }
          return certificate;
        } catch (FileNotFoundException e) {
          throw new NotActivatedException();
        } finally {
          getCard().endExclusive();
        }
        
      } else {
        throw new IllegalArgumentException("Keybox " + keyboxName
            + " not supported.");
      }

    } catch (CardException e) {
      log.warn(e);
      throw new SignatureCardException("Failed to access card.", e);
    }
    
  }

  /* (non-Javadoc)
   * @see at.gv.egiz.smcc.SignatureCard#getInfobox(java.lang.String, at.gv.egiz.smcc.PINProvider, java.lang.String)
   */
  @Override
  public byte[] getInfobox(String infobox, PINProvider provider, String domainId)
      throws SignatureCardException, InterruptedException {
  
    try {
      if ("IdentityLink".equals(infobox)) {
 
        PINSpec spec = pinSpecs.get(PINSPEC_INF);
        //new PINSpec(4, 4, "[0-9]", getResourceBundle().getString("inf.pin.name"));
        
        int retries = -1;
        String pin = null;
        boolean pinRequiered = false;

        do {
          if (pinRequiered) {
            pin = provider.providePIN(spec, retries);
            if (pin == null) {
              throw new CancelledException();
            }
          }
          try {
            getCard().beginExclusive();
            return readTLVFile(AID_DEC, EF_INFOBOX, pin, KID_PIN_INF, EF_INFOBOX_MAX_SIZE);
          } catch (FileNotFoundException e) {
            throw new NotActivatedException();
          } catch (SecurityStatusNotSatisfiedException e) {
            pinRequiered = true;
          } catch (VerificationFailedException e) {
            pinRequiered = true;
            retries = e.getRetries();
          } finally {
            getCard().endExclusive();
          }
        } while (retries != 0);

        throw new LockedException();

      } else {
        throw new IllegalArgumentException("Infobox '" + infobox
            + "' not supported.");
      }

    } catch (CardException e) {
      log.warn(e);
      throw new SignatureCardException("Failed to access card.", e);
    }
  
  }

  @Override
  public byte[] createSignature(byte[] hash, KeyboxName keyboxName,
      PINProvider provider) throws SignatureCardException, InterruptedException {
  
    if (hash.length != 20) {
      throw new IllegalArgumentException("Hash value must be of length 20.");
    }
  
    try {
      
      if (KeyboxName.SECURE_SIGNATURE_KEYPAIR.equals(keyboxName)) {

        PINSpec spec = pinSpecs.get(PINSPEC_SIG);
        //new PINSpec(6, 10, "[0-9]", getResourceBundle().getString("sig.pin.name"));
        
        int retries = -1;
        String pin = null;

        do {
          pin = provider.providePIN(spec, retries);
          if (pin == null) {
            throw new CancelledException();
          }
          try {
            getCard().beginExclusive();
            
            // SELECT DF
            selectFileFID(DF_SIG);
            // VERIFY
            retries = verifyPIN(pin, KID_PIN_SIG);
            if (retries != -1) {
              throw new VerificationFailedException(retries);
            }
            // MSE: SET DST
            mseSetDST(0x81, 0xb6, DST_SIG);
            // PSO: HASH
            psoHash(hash);
            // PSO: COMPUTE DIGITAL SIGNATURE
            return psoComputDigitalSiganture();

          } catch (SecurityStatusNotSatisfiedException e) {
            retries = verifyPIN(null, KID_PIN_SIG);
          } catch (VerificationFailedException e) {
            retries = e.getRetries();
          } finally {
            getCard().endExclusive();
          }
        } while (retries != 0);

        throw new LockedException();
        
    
      } else if (KeyboxName.CERITIFIED_KEYPAIR.equals(keyboxName)) {
        
        PINSpec spec = pinSpecs.get(PINSPEC_DEC);
        //new PINSpec(4, 4, "[0-9]", getResourceBundle().getString("dec.pin.name"));

        int retries = -1;
        String pin = null;
        boolean pinRequiered = false;

        do {
          if (pinRequiered) {
            pin = provider.providePIN(spec, retries);
            if (pin == null) {
              throw new CancelledException();
            }
          }
          try {
            getCard().beginExclusive();
            
            // SELECT DF
            selectFileFID(DF_DEC);
            // VERIFY
            retries = verifyPIN(pin, KID_PIN_DEC);
            if (retries != -1) {
              throw new VerificationFailedException(retries);
            }
            // MSE: SET DST
            mseSetDST(0x41, 0xa4, DST_DEC);
            // INTERNAL AUTHENTICATE
            return internalAuthenticate(hash);
            
          } catch (FileNotFoundException e) {
            throw new NotActivatedException();
          } catch (SecurityStatusNotSatisfiedException e) {
            pinRequiered = true;
            retries = verifyPIN(null, KID_PIN_DEC);
          } catch (VerificationFailedException e) {
            pinRequiered = true;
            retries = e.getRetries();
          } finally {
            getCard().endExclusive();
          }
        } while (retries != 0);

        throw new LockedException();

      } else {
        throw new IllegalArgumentException("KeyboxName '" + keyboxName
            + "' not supported.");
      }
      
    } catch (CardException e) {
      log.warn(e);
      throw new SignatureCardException("Failed to access card.", e);
    } 
      
  }

  protected ResponseAPDU selectFileFID(byte[] fid) throws CardException, SignatureCardException {
    CardChannel channel = getCardChannel();
    return transmit(channel, new CommandAPDU(0x00, 0xA4, 0x00,
        0x00, fid, 256));
  }

  @Override
  protected int verifyPIN(String pin, byte kid) throws LockedException, NotActivatedException, SignatureCardException {

    CardChannel channel = getCardChannel();

    ResponseAPDU resp;
    try {
      if (pin != null) {
        resp = transmit(channel, new CommandAPDU(0x00, 0x20, 0x00, kid, encodePINBlock(pin)), false);
      } else {
        //TODO this is not supported
        resp = transmit(channel, new CommandAPDU(0x00, 0x20, 0x00, kid), false);
      }
    } catch (CardException ex) {
      log.error("smart card communication failed: " + ex.getMessage());
      throw new SignatureCardException("smart card communication failed: " + ex.getMessage(), ex);
    }

    //6A 00 (falshe P1/P2) nicht in contextAID
    //69 85 (nutzungsbedingungen nicht erfüllt) in DF_Sig und nicht sigpin

    if (resp.getSW() == 0x63c0) {
      throw new LockedException("PIN locked.");
    } else if (resp.getSW1() == 0x63 && resp.getSW2() >> 4 == 0xc) {
      // return number of possible retries
      return resp.getSW2() & 0x0f;
    } else if (resp.getSW() == 0x6983) {
      throw new NotActivatedException();
    } else if (resp.getSW() == 0x9000) {
      return -1;
    } else {
      throw new SignatureCardException("Failed to verify pin: SW="
          + Integer.toHexString(resp.getSW()) + ".");
    }
  }
  
  private void mseSetDST(int p1, int p2, byte[] dst) throws CardException, SignatureCardException {
    CardChannel channel = getCardChannel();
    ResponseAPDU resp = transmit(channel, new CommandAPDU(0x00, 0x22, p1,
        p2, dst));
    if (resp.getSW() != 0x9000) {
      throw new SignatureCardException("MSE:SET DST failed: SW="
          + Integer.toHexString(resp.getSW()));
    }
  }

  private void psoHash(byte[] hash) throws CardException, SignatureCardException {
    CardChannel channel = getCardChannel();
    ResponseAPDU resp = transmit(channel, new CommandAPDU(0x00, 0x2A, 0x90,
        0x81, hash));
    if (resp.getSW() != 0x9000) {
      throw new SignatureCardException("PSO:HASH failed: SW="
          + Integer.toHexString(resp.getSW()));
    }
  }

  private byte[] psoComputDigitalSiganture() throws CardException,
      SignatureCardException {
    CardChannel channel = getCardChannel();
    ResponseAPDU resp = transmit(channel, new CommandAPDU(0x00, 0x2A, 0x9E,
        0x9A, 256));
    if (resp.getSW() != 0x9000) {
      throw new SignatureCardException(
          "PSO: COMPUTE DIGITAL SIGNATRE failed: SW="
              + Integer.toHexString(resp.getSW()));
    } else {
      return resp.getData();
    }
  }
  
  private byte[] internalAuthenticate(byte[] hash) throws CardException, SignatureCardException {
    byte[] digestInfo = new byte[] {
        (byte) 0x30, (byte) 0x21, (byte) 0x30, (byte) 0x09, (byte) 0x06, (byte) 0x05, (byte) 0x2B, (byte) 0x0E, 
        (byte) 0x03, (byte) 0x02, (byte) 0x1A, (byte) 0x05, (byte) 0x00, (byte) 0x04
    };
    
    byte[] data = new byte[digestInfo.length + hash.length + 1];
    
    System.arraycopy(digestInfo, 0, data, 0, digestInfo.length);
    data[digestInfo.length] = (byte) hash.length;
    System.arraycopy(hash, 0, data, digestInfo.length + 1, hash.length);
    
    CardChannel channel = getCardChannel();
    
    ResponseAPDU resp = transmit(channel, new CommandAPDU(0x00, 0x88, 0x10, 0x00, data, 256));
    if (resp.getSW() != 0x9000) {
      throw new SignatureCardException("INTERNAL AUTHENTICATE failed: SW=" + Integer.toHexString(resp.getSW()));
    } else {
      return resp.getData();
    }
  }

  @Override
  public String toString() {
    return "a-sign premium";
  }

  /**
   * ASCII encoded pin, padded with 0x00
   * @param pin
   * @return a 8 byte pin block 
   */
  private byte[] encodePINBlock(String pin) {
    byte[] asciiPIN = pin.getBytes(Charset.forName("ASCII"));
    byte[] encodedPIN = new byte[8];
    System.arraycopy(asciiPIN, 0, encodedPIN, 0, Math.min(asciiPIN.length,
        encodedPIN.length));
//    System.out.println("ASCII encoded PIN block: " + SMCCHelper.toString(encodedPIN));
    return encodedPIN;
  }

  @Override
  public void activatePIN(PINSpec pinSpec, String pin) throws SignatureCardException {
    throw new SignatureCardException("PIN activation not supported by this card");
  }

  /**
   * SCARD_E_NOT_TRANSACTED inf/dec PIN not active (pcsc crash)
   * @param pinSpec
   * @param oldPIN
   * @param newPIN
   * @throws at.gv.egiz.smcc.LockedException
   * @throws at.gv.egiz.smcc.VerificationFailedException
   * @throws at.gv.egiz.smcc.NotActivatedException
   * @throws at.gv.egiz.smcc.SignatureCardException
   */
  @Override
  public void changePIN(PINSpec pinSpec, String oldPIN, String newPIN)
          throws LockedException, VerificationFailedException, NotActivatedException, SignatureCardException {
    Card icc = getCard();
    try {
      icc.beginExclusive();
      CardChannel channel = icc.getBasicChannel();

      if (pinSpec.getContextAID() != null) {
        ResponseAPDU responseAPDU = transmit(channel,
                new CommandAPDU(0x00, 0xa4, 0x04, 0x0c, pinSpec.getContextAID()));
        if (responseAPDU.getSW() != 0x9000) {
          icc.endExclusive();
          String msg = "Select AID " + SMCCHelper.toString(pinSpec.getContextAID()) +
                  ": SW=" + Integer.toHexString(responseAPDU.getSW());
          log.error(msg);
          throw new SignatureCardException(msg);
        }
      }

      byte[] cmd = new byte[16];
      System.arraycopy(encodePINBlock(oldPIN), 0, cmd, 0, 8);
      System.arraycopy(encodePINBlock(newPIN), 0, cmd, 8, 8);

      ResponseAPDU responseAPDU = transmit(channel,
              new CommandAPDU(0x00, 0x24, 0x00, pinSpec.getKID(), cmd), false);

      icc.endExclusive();

      log.debug("change pin returned SW=" + Integer.toHexString(responseAPDU.getSW()));

      if (responseAPDU.getSW() == 0x63c0) {
        log.error(pinSpec.getLocalizedName() + " locked");
        throw new LockedException();
      } else if (responseAPDU.getSW1() == 0x63 && responseAPDU.getSW2() >> 4 == 0xc) {
        int retries = responseAPDU.getSW2() & 0x0f;
        log.error("wrong " + pinSpec.getLocalizedName() + ", " + retries + " retries");
        throw new VerificationFailedException(retries);
      } else if (responseAPDU.getSW() == 0x6983) {
        // sig-pin only (card not transacted for inf/dec pin)
        log.error(pinSpec.getLocalizedName() + " not activated");
        throw new NotActivatedException();
      } else if (responseAPDU.getSW() != 0x9000) {
        String msg = "Failed to change " + pinSpec.getLocalizedName() +
                ": SW=" + Integer.toHexString(responseAPDU.getSW());
        log.error(msg);
        throw new SignatureCardException(msg);
      }
    } catch (CardException ex) {
      log.error("Failed to change " + pinSpec.getLocalizedName() +
              ": " + ex.getMessage());
      throw new SignatureCardException(ex.getMessage(), ex);
    }
  }
}
