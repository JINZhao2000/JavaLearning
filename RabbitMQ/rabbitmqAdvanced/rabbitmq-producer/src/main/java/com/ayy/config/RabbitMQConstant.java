package com.ayy.config;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/05/2021
 * @ Version 1.0
 */

public interface RabbitMQConstant {
    final String TTL_EXCHANGE = "ttl_direct_exchange";

    final String TTL_QUEUE = "ttl.direct.queue";

    final String TTL_ROUTING_KEY = "ttl";

    final String TTL_MESSAGE_QUEUE = "ttl.message.direct.queue";

    final String TTL_MESSAGE_ROUTING_KEY = "ttl.message";
}
