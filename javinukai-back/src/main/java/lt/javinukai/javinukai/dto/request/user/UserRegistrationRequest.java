package lt.javinukai.javinukai.dto.request.user;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    @NonNull
    @NotBlank
    @Length(max = 100, message = "FIRST_NAME_LENGTH_EXCEEDED")
    private String name;
    @NonNull
    @NotBlank
    @Length(max = 100, message = "LAST_NAME_LENGTH_EXCEEDED")
    private String surname;
    @NonNull
    @NotNull
    @Min(value = 1900, message = "AGE_TOO_HIGH")
    @Max(value = 2100 ,message = "AGE_INVALID")
    private Integer birthYear;
    @NonNull
    @NotBlank
    @Length(max = 15, message = "PHONE_NUMBER_LENGTH_EXCEEDED")
    private String phoneNumber;
    @NonNull
    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "EMAIL_FORMAT_INCORRECT")
    @Length(max = 100, message = "EMAIL_LENGTH_EXCEEDED")
    private String email;
    @NonNull
    @NotBlank
    private String password;

    // this can be null
    @Length(max = 150, message = "INSTITUTION_NAME_LENGTH_EXCEEDED")
    private String institution;
}
