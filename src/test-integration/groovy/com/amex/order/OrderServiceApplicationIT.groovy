package com.amex.order

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

class OrderServiceApplicationIT extends AbstractIntegrationSpec {

    @Autowired
    private ApplicationContext applicationContext

    def "context loads"() {
        expect:
        applicationContext
    }

}