import os

mac_key = os.urandom(256 // 8)
enc_key = os.urandom(256 // 8)

with open('enc.key', 'wb') as f:
    f.write(enc_key)

with open('mac.key', 'wb') as f:
    f.write(mac_key)

with open('ELCI/src/main/resources/enc.key', 'wb') as f:
    f.write(enc_key)

with open('ELCI/src/main/resources/mac.key', 'wb') as f:
    f.write(mac_key)