package expertostech.password.encrypt.temp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class EncryptSample {

    public static void main(String[] args) {
        String pepper = "pepper"; // secret key used by password encoding
        int iterations = 185000;  // number of hash iteration
        int hashWidth = 265;      // hash width in bits

        // dfVFZMfSM+ohPwLPjubEiGHaAhXeomWgKAZN4GoUT82QIayftxRB9hw=
        // Gn05dHd5DqqkCkE4CoySwD35EXAMdmbSY4+legvltl9Kn7co3B+Rv60=

        // Nm+P5D4pHxP7D64E5nkYffmx4UDDTbNBsqRyPSZYX3pgBjtM6wcXPMI=
        // $2a$10$Swaaz2ksgvL2F3a4I6qQNO5NNtopJ4xtDJh2GA8ffczS0U0kAFNaq

        String plainPassword = "rodrigo";

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
