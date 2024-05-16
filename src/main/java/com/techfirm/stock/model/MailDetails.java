package com.techfirm.stock.model;

import lombok.Builder;

@Builder
public record MailDetails(String recipient, String msgBody, String subject) {
}
