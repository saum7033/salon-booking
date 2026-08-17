package com.example.payment.payload.response;

import lombok.Data;

@Data
public class PaymentLinkResponse {
    private String payment_link_url;
    private String get_Payment_link_id;
}
