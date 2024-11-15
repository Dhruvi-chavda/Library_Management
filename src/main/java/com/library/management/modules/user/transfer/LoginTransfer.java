package com.library.management.modules.user.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginTransfer {

    private String accessToken;
    private String tokenType;
    private String email;
}
