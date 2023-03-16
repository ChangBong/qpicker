package com.peopulley.core.base.converter;

import com.peopulley.core.common.consts.Constants;
import lombok.RequiredArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

@RequiredArgsConstructor
public class EncryptConverter implements AttributeConverter<String, String> {

    private static final String AES = "AES";
    private static final byte[] encryptionKey = Constants.ATTR_ENC_KEY.getBytes();
    private final Key key;

    private final Cipher cipher;

    public EncryptConverter() throws Exception {
        key = new SecretKeySpec(encryptionKey, AES);
        cipher = Cipher.getInstance(AES);
    }

    @Override
    synchronized public String convertToDatabaseColumn(String attribute) {
        try {
            if(attribute == null) return null;
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    synchronized public String convertToEntityAttribute(String dbData) {
        try {
            if(dbData == null) return null;
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getMimeDecoder().decode(dbData)));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
