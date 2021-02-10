package com.mkyong;


import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.lang.JoseException;

import com.chilkatsoft.CkJsonObject;
import com.chilkatsoft.CkJwe;
import com.chilkatsoft.CkStringBuilder;

// Simple test program to verify that the Chilkat Java class
// library is ready to use.  It verifies that the Chilkat DLL 
// can be loaded and that a Chilkat object can be instantiated.
	
import com.chilkatsoft.CkZip;

public class Test {
	
  public static void main(String[] args) throws JoseException {
	  //
	    // An example showing the use of JSON Web Encryption (JWE) to encrypt and then decrypt some content
	    // using a symmetric key and direct encryption.
	    //

	    // The content to be encrypted
	    String message = "Well, as of this moment, they're on DOUBLE SECRET PROBATION!";

	    // The shared secret or shared symmetric key represented as a octet sequence JSON Web Key (JWK)
	    String jwkJson = "{\"kty\":\"oct\",\"k\":\"Fdh9u8rINxfivbrianbbVT1u232VQBZYKx1HGAGPt2I\"}";
	    JsonWebKey jwk = JsonWebKey.Factory.newJwk(jwkJson);

	    // Create a new Json Web Encryption object
	    JsonWebEncryption senderJwe = new JsonWebEncryption();
	    senderJwe.setPlaintext(message);
	    senderJwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.DIRECT);
	    
	    senderJwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);

	    // Set the key on the JWE. In this case, using direct mode, the key will used directly as
	    // the content encryption key. AES_128_CBC_HMAC_SHA_256, which is being used to encrypt the
	    // content requires a 256 bit key.
	    senderJwe.setKey(jwk.getKey());

	    // Produce the JWE compact serialization, which is where the actual encryption is done.
	    // The JWE compact serialization consists of five base64url encoded parts
	    // combined with a dot ('.') character in the general format of
	    // <header>.<encrypted key>.<initialization vector>.<ciphertext>.<authentication tag>
	    // Direct encryption doesn't use an encrypted key so that field will be an empty string
	    // in this case.
	    String compactSerialization = senderJwe.getCompactSerialization();

	    // Do something with the JWE. Like send it to some other party over the clouds
	    // and through the interwebs.
	    System.out.println("JWE compact serialization: " + compactSerialization);

	    // That other party, the receiver, can then use JsonWebEncryption to decrypt the message.
	    JsonWebEncryption receiverJwe = new JsonWebEncryption();

	    // Set the algorithm constraints based on what is agreed upon or expected from the sender
	    AlgorithmConstraints algConstraints = new AlgorithmConstraints(ConstraintType.PERMIT, KeyManagementAlgorithmIdentifiers.DIRECT);
	    receiverJwe.setAlgorithmConstraints(algConstraints);
	    AlgorithmConstraints encConstraints = new AlgorithmConstraints(ConstraintType.PERMIT, ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
	    receiverJwe.setContentEncryptionAlgorithmConstraints(encConstraints);

	    // Set the compact serialization on new Json Web Encryption object
	    receiverJwe.setCompactSerialization(compactSerialization);

	    // Symmetric encryption, like we are doing here, requires that both parties have the same key.
	    // The key will have had to have been securely exchanged out-of-band somehow.
	    receiverJwe.setKey(jwk.getKey());

	    // Get the message that was encrypted in the JWE. This step performs the actual decryption steps.
	    String plaintext = receiverJwe.getPlaintextString();

	    // And do whatever you need to do with the clear text message.
	    System.out.println("plaintext: " + plaintext);
}
  }




