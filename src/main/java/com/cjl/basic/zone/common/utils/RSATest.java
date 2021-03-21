package com.cjl.basic.zone.common.utils;


import java.util.Map;

public class RSATest {

    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEho6yI065gaM+n17dgVUBKE3g523QSNDdDZa9ZW0t4m0HVQu4fpJRD8lng9Vr3qRuCorY5V/ikJTsg8BVLeokxZOJANLXp7xgDVvqzWAL6Hvy7xDq6aIuhDzeX7Vb19K9BMmry9PRFgnZ4b//Ip3xdXN9fx4SUhO9xE3AIyDtQQIDAQAB";
            privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMSGjrIjTrmBoz6fXt2BVQEoTeDnbdBI0N0Nlr1lbS3ibQdVC7h+klEPyWeD1WvepG4KitjlX+KQlOyDwFUt6iTFk4kA0tenvGANW+rNYAvoe/LvEOrpoi6EPN5ftVvX0r0EyavL09EWCdnhv/8infF1c31/HhJSE73ETcAjIO1BAgMBAAECgYBH5ZI+yXv7kdnPBy8FilM24S/cn84k7P68YERNddXDe5q3m/11uZh933CnViu2EvXE7EZvozq5AHv7KxgqEWoe9XqkmhNN1+x77G+4UjWMm4VFNDaWQX5SkqjiE+IMfXn/xqSct0I5WpfAK8dHZqYLlNNH+Imcur8EkkNa+esAAQJBAOtU8Dji7t354zx940zr9xKTOHb8zRX5WLOtpyKqtrargNC5zHM2/M7CiULpmLCOu98pNMjgcb0HSxbX9y3d90ECQQDVyR+xN325VZ98b0abCpoT0VDgKd82yCP2XPBgGskCrMYl1ewz+rKoTW+BHh40EpouAYqdSBPZ2ysrHKMli3YBAkAXqAYt8cIJVmFdZ1o+FUpU96+pZmhHWTVtentepMKRQlREpyjCPDjVoACkVU8gEHkaSc4gk09brSwDM9qJJdMBAkAYhVoiPIgqIt0JK8ZAcEXd8gtBuuvEX85oKp7Tlx7pNKIBovjVuXKWhVM9zxONy3htwHWCtsq4GUBpUH6SBt4BAkAnUDRcIVRsORwOpewyQ8BauTC1sLhuSkxChWXvfrWfA2xLNO9HOabtSEH5NKIRa1XiV8yEtws80kPl99N6sPQa";


            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        test();
    }

    static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "[{'modbus': 1, 'modbusType': 'h', 'paramList': (326.0, 0.0, 326.0, 0.0, 326.0, 0.0, 326.0, 0.0, 326.0, 0.0, 326.0, 0.0, 221.0, 0.0, 217.0, 0.0, 215.0, 0.0, 212.0, 0.0, 209.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 326.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 199.0, 0.0, 201.0, 0.0, 253.0, 0.0, 198.0, 0.0, 241.0, 0.0, 240.0, 0.0, 240.0, 0.0, 238.0, 0.0, 234.0, 0.0, 231.0), 'datetime': 1594105293278},{'modbus': 1, 'modbusType': 'h', 'paramList': (326.0, 0.0, 326.0, 0.0, 326.0, 0.0, 326.0, 0.0, 326.0, 0.0, 326.0, 0.0, 221.0, 0.0, 217.0, 0.0, 215.0, 0.0, 212.0, 0.0, 209.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 326.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 299.0, 0.0, 301.0, 0.0, 353.0, 0.0, 198.0, 0.0, 241.0, 0.0, 340.0, 0.0, 340.0, 0.0, 238.0, 0.0, 234.0, 0.0, 231.0), 'datetime': 2594105293278}]";
//        String key ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQFA4YVQZatJAyO7TsuzkWE8dz17qi8GuOCnegKbKd6alLXkDzKhVG3kd3GijouHtlqsm2zFCK7K+I5MUu8Fuk23OEwIVZn9StltjLzJ1hB1AZC1/NCoCFZG5T2+AaQolrw8LvPS5jH2TuYQf7oLDHR88BKJgV/tZlr22Jicqm0wIDAQAB";
//        String value ="JwlfCvKVZEeuFAu5XK1pgcxtldL5pHCZL1FwpOstX8zoyW42i7y17VB89J4gcM0FPg4/7L4AB7RPmST+OWbqabQe5xHgS5Uvo2DcZHe51F9v0GH3o/R5VJBwOytyHj3R/JnKSNgINIR0LnxwGT1NL0dzdQHCoo5BfaeVHjNdO/gtqir36ym3kDxx2CyWuipum8Ld7/ng3z1FgM8WlGZ8KzCh05r3rsIsYePtzkrfxoy+lKhWzrjLzQMVaj7SN8Hv/+fIzeydc9sPMoS6b3mV1hBxAJgylKM8TpNUULHf1nk9fvLaFNNUsLKIiXb6X5lodTxr/dt0c0tL6axWIbKeVAM1RcIuQyAimYgzLO2sFxjDYx2xVuuhBGMjlOgBGuTiGR2uEPFvntLzSmMMpvqznIT86VK0csFoJwuhoOKkm4+FOAcp8zhtkzJ+pzz4phgmvjgfMLq7KdEh1ucAJcM1+zm3umJl+rer3DRrd2ZIEh66u+aeyn1bb/FyRmlOyJW8Oniqth/jb4fFAURBLxwJFZrKcLm/JG/MGlm6pPc0AFEi42NMBGOuF8GgJ1M8ccPH1crtgoSgLWnvTioGtX4Z7U+Lu1CD45bUdYANlUSawXqGgmUq2oAIhOI4tMdZZkN4tn16G5pZXQ/dsy4NfwciMD0Lzft45SAaWt6zbTKrOmBaunsMV8GX+qprh5KrJoWX0ryiXpr7dmqJTpXAHjQh24vFP51XPKlHgh2mYu5Kcp2AGqPbYogqlk+83fd0ldV3HnuNtg2gnoqQk9YqWBdhhJ30HMytqrotmyBKhf3if+OGPr7V/kB9GgL4E94y31EqGRxOX+eoatn9CUrfcIHkGmCZdTZDl5cJjeDBvK/eIgF12C/lSLdyQpC43ADGaZR9dq4YauAoSC54GIyQ3T6z6TZgYl9zSTUKGAcuYgXNIoS8d4pD+x2oDr1cJO9rW1QK1X2vBFyyzZBKSFoRKfySJuRGkJKQc3W5NRjjD8fnCp+2HjuNylETNpOMAhHJ/WcGYiQISq8poo4q3kbuIS8psLrW05UY1sdVZE6ZvrSpSMbqp/WSwAUil1vX05x6hZLFPIBLMPDteeot3qZX52hqDqsXO33tBr5IQsVg/HrGCG/ffpoDcuTBfBQkJUlWmp21XjFSxTswpi49nNpF2TiDSOuCRqCELGlMTUwF9m/FP6VCuJLd0zaJwFqi3ANCfqxwQ+T7nQEbtLHAeUivxZ95pnn56hoTOT6eJJTqVgH4BeoY6Getv4NCrnLRflt1cYUgLuV5iAeWACCxkJiGleYBLQxZhI9hfUQEUL9ox0EQYF3H9qJ8Ia4AelFtWqB+zeniJf2C6IIN6Wd/nCQJ/r34pw==";
//      String key ="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMSGjrIjTrmBoz6fXt2BVQEoTeDnbdBI0N0Nlr1lbS3ibQdVC7h+klEPyWeD1WvepG4KitjlX+KQlOyDwFUt6iTFk4kA0tenvGANW+rNYAvoe/LvEOrpoi6EPN5ftVvX0r0EyavL09EWCdnhv/8infF1c31/HhJSE73ETcAjIO1BAgMBAAECgYBH5ZI+yXv7kdnPBy8FilM24S/cn84k7P68YERNddXDe5q3m/11uZh933CnViu2EvXE7EZvozq5AHv7KxgqEWoe9XqkmhNN1+x77G+4UjWMm4VFNDaWQX5SkqjiE+IMfXn/xqSct0I5WpfAK8dHZqYLlNNH+Imcur8EkkNa+esAAQJBAOtU8Dji7t354zx940zr9xKTOHb8zRX5WLOtpyKqtrargNC5zHM2/M7CiULpmLCOu98pNMjgcb0HSxbX9y3d90ECQQDVyR+xN325VZ98b0abCpoT0VDgKd82yCP2XPBgGskCrMYl1ewz+rKoTW+BHh40EpouAYqdSBPZ2ysrHKMli3YBAkAXqAYt8cIJVmFdZ1o+FUpU96+pZmhHWTVtentepMKRQlREpyjCPDjVoACkVU8gEHkaSc4gk09brSwDM9qJJdMBAkAYhVoiPIgqIt0JK8ZAcEXd8gtBuuvEX85oKp7Tlx7pNKIBovjVuXKWhVM9zxONy3htwHWCtsq4GUBpUH6SBt4BAkAnUDRcIVRsORwOpewyQ8BauTC1sLhuSkxChWXvfrWfA2xLNO9HOabtSEH5NKIRa1XiV8yEtws80kPl99N6sPQa";
//      String value="DG3WyU9EltPanKELQnz9DuBzsMlbwrqB4XFuwHwRNr/Q3aKa/jeIjAux0Zz6psb0v/kCJPaIAazEtUmGxY/b3TQx0h5r4e8pli+aUPJzKSgVVq/jAAGqIE9Z9eH2/YtqAP6KflZGmM9sCyQvbyPnFx8MUFxxvw5xKDSk+eWpQNlhzq36vVCaaIRcBIvUNndhRioequS5KgM80+GcHcaUs/TWYR4/elYVgRBJHIQS5RByRrMp0T0fiLyWs8sf5gfbnxBeJRyCs5XrgN+O4DEQ61uinkwQdsWAn9nj4E6LK/Bjb4juXR6GJk9+AV6g8hkjXOrvt12QVQRpirlOkSfvtJXjbA4DMh/qp/jv9o+kHPaARNsPZojvPs25FhyaypwbKQ7ckeab2/4Xjlv4DB5/Meb4OlrEAcTr5k9M51Bd/Utq/0PVKugAqPj4FqL3IMs2wEks3HWGI3e2b1jpmdUVLk+t47TG2eJANJrS77IpIV5CiArLOkrXkXLNuF4qnCFyOLOgFl8+bsCmxlXgiVhi8x+wwGGxCR6LOOKdLTvUmHNc5tVelXPGAbJ/sWmfuBE8eX0kbcDZz9o8ZmygLE1A64EgOFlSz3NDvW7/y9ckN1hX10RotEkPzmA6niY11RygxRCcZofo+zS1Ro/1D30keJl3aJ48i08kyG66XQXob86IqDMRaa1moSw8UCSH/9TYOmpICuqCixD5wEID8v/KBMWg3HvtZpOrIZMR9m5gQkemsfT9uew1HRkJLq4FLFr+ORdHfJhRGapKnJQVnKOAKTbRXV4c9BWmLzM1NlbUVCCovyAFqefcMbBJbF0jau/4NeVs/72vuZhT/UEjlVu+YpRtnnMSwnbp3NSwfspsw3KZlPELcI5Tw9id686c+OdJpOV7Z2pC/H/OQpAKBsRk7m8CiMvtjMyxiTFv6BNfZEBsOn9iTiX4NzN1oWzCGwyZWpvlK5JKad2UBSlRPir5hR/6TJFIK4v+X1EBE7bL+iRwxY+7hlo7GTB6HsV2dL0eRTGqYTyVdY+4lD6t+Oxk3tJESmuBGlMNQMNnBNJ6N4ZMI6mTZb8zbi3KV1Rhuq4bFqi4upNEoVKsvqR7PjJVIyRHlumH/ser+0E8cUoIhNTXLUCI1QJDaonuS1ihO7y+EajFKuw7ZP0qzl6YzNkcsMYoEUnVwhTST3S8ZuCdTb0=";

        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);

        System.out.println("加密后文字：\r\n" + new String(Base64Utils.encode(encodedData)));



        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);

        System.out.println("解密后文字: \r\n" + target);
    }


}