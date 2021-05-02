package com.ayy.config;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/05/2021
 * @ Version 1.0
 */

public interface RabbitMQConstant {
    final String EXCHANGE_NAME = "fanout_order_exchange";

    final String ROUTING_KEY = "";

    final String SMS_QUEUE = "sms.fanout.queue";

    final String EMAIL_QUEUE = "email.fanout.queue";
}
