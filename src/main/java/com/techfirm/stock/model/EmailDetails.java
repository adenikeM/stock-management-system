package com.techfirm.stock.model;

import lombok.Builder;

@Builder
public record EmailDetails(String recipient, String msgBody, String subject) {
}

