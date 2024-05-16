package com.techfirm.stock.model;

import java.math.BigDecimal;

public interface EmailService {
    void sendMail(EmailDetails details);

    void sendRefundEmail(MailDetails details);
}
