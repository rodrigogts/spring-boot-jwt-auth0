package expertostech.password.encrypt.sample;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class EncryptSample {

    public static void main(String[] args) {
        String pepper = "pepper"; // secret key used by password encoding
        int iterations = 185000;  // number of hash iteration
        int hashWidth = 265;      // hash width in bits

        String plainPassword = "password";

        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth);
        pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);
        String encodePbkdf2 = pbkdf2PasswordEncoder.encode(plainPassword);
        System.out.println(pbkdf2PasswordEncoder.matches(plainPassword, encodePbkdf2));


        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodeBcrypt = bCryptPasswordEncoder.encode(plainPassword);


        System.out.println(encodePbkdf2);
        System.out.println(encodeBcrypt);
    }


}
