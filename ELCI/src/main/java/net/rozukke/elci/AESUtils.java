package net.rozukke.elci;

import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyException;
import java.security.SecureRandom;
import java.util.Arrays;

public class AESUtils {
    public static byte[] aes_256_cbc_encrypt(byte[] plaintext, byte[] key) throws Exception {
        // Pad the data
        byte[] padded_plaintext = padData(plaintext);

        // Generate new random 128 IV required for CBC mode
        byte[] iv = generateRandomIV();

        // AES CBC Cipher
        Cipher aes_256_cbc_cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes_256_cbc_cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));

        // Encrypt padded plaintext
        byte[] ciphertext = aes_256_cbc_cipher.doFinal(padded_plaintext);

        // Concatenate IV and ciphertext
        byte[] ivAndCiphertext = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, ivAndCiphertext, 0, iv.length);
        System.arraycopy(ciphertext, 0, ivAndCiphertext, iv.length, ciphertext.length);

        return ivAndCiphertext;
    }

    private static byte[] padData(byte[] data) {
        int blockSize = 16; // AES block size is 128 bits (16 bytes)
        int paddingLength = blockSize - (data.length % blockSize);
        byte[] paddedData = new byte[data.length + paddingLength];
        System.arraycopy(data, 0, paddedData, 0, data.length);
        for (int i = data.length; i < paddedData.length; i++) {
            paddedData[i] = (byte) paddingLength;
        }
        return paddedData;
    }

    private static byte[] generateRandomIV() {
        byte[] iv = new byte[16]; // 16 bytes for 128-bit IV
        // Generate random bytes for IV using a secure random generator
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return iv;
    }

    public static String aes_256_cbc_decrypt(byte[] cipherData, byte[] key) throws Exception {
        // Extract iv and ciphertext
        byte[] iv = new byte[16];
        byte[] ciphertext = new byte[cipherData.length - 16];
        System.arraycopy(cipherData, 0, iv, 0, 16);
        System.arraycopy(cipherData, 16, ciphertext, 0, ciphertext.length);

        // Recover padded plaintext
        PaddedBufferedBlockCipher aesCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
        aesCipher.init(false, new ParametersWithIV(new KeyParameter(key), iv));
        byte[] recovered_padded_plaintext = new byte[aesCipher.getOutputSize(ciphertext.length)];
        int outputLen = aesCipher.processBytes(ciphertext, 0, ciphertext.length, recovered_padded_plaintext, 0);
        outputLen += aesCipher.doFinal(recovered_padded_plaintext, outputLen);

        // Remove padding
        int padLength = recovered_padded_plaintext[recovered_padded_plaintext.length - 1];
        byte[] recovered_plaintext = new byte[recovered_padded_plaintext.length - padLength];
        System.arraycopy(recovered_padded_plaintext, 0, recovered_plaintext, 0, recovered_padded_plaintext.length);
        return new String(recovered_plaintext, StandardCharsets.UTF_8).substring(0, outputLen);
    }

    public static byte[] create_hmac_sha_256_tag(byte[] data, byte[] key) throws Exception {
        Mac hmac_sha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        hmac_sha256.init(secretKey);
        byte[] hmac_tag = hmac_sha256.doFinal(data);
        return hmac_tag;
    }

    public static void verify_hmac_sha_256_tag(byte[] tag, byte[] data, byte[] key) throws Exception {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        hmacSha256.init(secretKey);
        byte[] hmacTag = hmacSha256.doFinal(data);
        if (!Arrays.equals(tag, hmacTag)) {
            throw new KeyException("Calculated tag does not equal hmacTag");
        }
    }
}

