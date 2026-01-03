package com.xianyu.marketplace.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String receiverName;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String province;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String district;
    
    @Column(nullable = false)
    private String detailAddress;
    
    @Column(name = "is_default")
    private Boolean isDefault;
    
    @PrePersist
    protected void onCreate() {
        if (isDefault == null) {
            isDefault = false;
        }
    }
}
