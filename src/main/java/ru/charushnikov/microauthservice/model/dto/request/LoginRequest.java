package ru.charushnikov.microauthservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import ru.charushnikov.microauthservice.util.PhoneUtils;

@Builder
public record LoginRequest(
        @NotNull(message = "{validation.phone.message}")
        @Pattern(regexp = PhoneUtils.PHONE_REGEX, message = "{validation.phone.message}")
        String mobilePhone,

        @NotNull(message = "{validation.password.message}")
        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*[!#$%&'()*+,\\-./:;<=>\\[?@\\]^_`{|}~])" +
                "[a-zA-Z0-9!#$%&'()*+,\\-./:;<=>\\[?@\\]^_`{|}~]{6,20}$",
                message = "{validation.password.message}")
        String password
) {
}
