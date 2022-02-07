package kodlamaio.hrms.core.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty()
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",
            message = "Geçerli bir email giriniz")
    @Column(name = "email")
    private String email;

    @NotEmpty()
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "Şifreniz en az 8 karakterden oluşmaktadır." +
                    "Şifrenizde en az bir özel karakter , büyük harf , küçük harf , rakam olması gerekir." +
                    "Şifrenizde boşluk olmaması gerekir."
    )
    @Column(name = "password")
    private String password;

}
