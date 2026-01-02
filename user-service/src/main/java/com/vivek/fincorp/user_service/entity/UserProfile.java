package com.vivek.fincorp.user_service.entity;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @Column(nullable = false, updatable = false)
    private String userId;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String fullName;

    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Phone number must be 10 digits"
    )
    @Column(length = 10)
    private String phone;

    @Size(max = 255)
    private String address;
}