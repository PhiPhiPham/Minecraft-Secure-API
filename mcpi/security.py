import base64
import os

from cryptography.exceptions import InvalidSignature
from cryptography.hazmat.primitives import hashes, hmac, padding
from cryptography.hazmat.primitives.ciphers import Cipher
from cryptography.hazmat.primitives.ciphers.algorithms import AES
from cryptography.hazmat.primitives.ciphers.modes import CBC


def encrypt_then_mac_handler(plain_text_bytes):
    # Keys for AES-256 CBC and HMAC-SHA-256
    with open('../enc.key', mode='rb') as file:
        enc_key = file.read()
    with open('mac.key', mode='rb') as file:
        mac_key = file.read()

    # Sender to encrypt-then-MAC
    cipher_data = aes_256_cbc_encrypt(plain_text_bytes, enc_key)
    mac_tag = create_hmac_sha_256_tag(cipher_data, mac_key)
    data_to_send = mac_tag + cipher_data

    # Convert to b64 before sending
    return base64.b64encode(data_to_send)


def decrypt_handler(b64_encoded_message):
    # Keys for AES-256 CBC and HMAC-SHA-256
    with open('../enc.key', mode='rb') as file:
        enc_key = file.read()
    with open('mac.key', mode='rb') as file:
        mac_key = file.read()

    b64_decoded_message = base64.b64decode(b64_encoded_message)

    received_mac_tag = b64_decoded_message[:32]
    received_cipherdata = b64_decoded_message[32:]

    # Receiver to MAC-then-decrypt
    try:
        verify_hmac_sha_256_tag(received_mac_tag, received_cipherdata, mac_key)
    except InvalidSignature:
        assert False
    else:
        res = aes_256_cbc_decrypt(received_cipherdata, enc_key).decode()
        res = repr(res)
        res = res.replace("'","")
        return res.split('\\', 1)[0]


def aes_256_cbc_encrypt(plaintext, key):
    # Pad the data
    pkcs7_padder = padding.PKCS7(AES.block_size).padder()
    padded_plaintext = pkcs7_padder.update(plaintext) + pkcs7_padder.finalize()

    # Generate new random 128 IV required for CBC mode
    iv = os.urandom(128 // 8)

    # AES CBC Cipher
    aes_256_cbc_cipher = Cipher(AES(key), CBC(iv))

    # Encrypt padded plaintext
    ciphertext = aes_256_cbc_cipher.encryptor().update(padded_plaintext)

    return iv + ciphertext


def aes_256_cbc_decrypt(cipherdata, key):
    # Extract iv and ciphertext
    iv, ciphertext = cipherdata[:16], cipherdata[16:]

    # Recover padded plaintext
    aes_256_cbc_cipher = Cipher(AES(key), CBC(iv))

    # Decrypt ciphertext
    decrypted_padded_plaintext = aes_256_cbc_cipher.decryptor().update(ciphertext)

    # Remove padding
    pkcs7_unpadder = padding.PKCS7(AES.block_size).unpadder()
    unpadded_plaintext = pkcs7_unpadder.update(decrypted_padded_plaintext) + pkcs7_unpadder.finalize()

    return unpadded_plaintext


def create_hmac_sha_256_tag(data, key):
    hash_function = hashes.SHA256()
    h = hmac.HMAC(key, hash_function)
    h.update(data)
    hmac_tag = h.finalize()
    return hmac_tag


def verify_hmac_sha_256_tag(tag, data, key):
    hash_function = hashes.SHA256()
    h = hmac.HMAC(key, hash_function)
    h.update(data)
    h.verify(tag)
