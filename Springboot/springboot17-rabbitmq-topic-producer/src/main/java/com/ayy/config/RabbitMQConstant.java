package com.ayy.config;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/05/2021
 * @ Version 1.0
 */

public interface RabbitMQConstant {
    final String EXCHANGE_NAME = "topic_order_exchange";

    final String SMS_ROUTING_KEY = "#.sms.#";

    final String EMAIL_ROUTING_KEY = "#.email.#";

    final String SMS_QUEUE = "sms.topic.queue";

    final String EMAIL_QUEUE = "email.topic.queue";
}
