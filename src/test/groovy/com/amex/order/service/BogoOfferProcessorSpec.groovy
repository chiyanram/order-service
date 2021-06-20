package com.amex.order.service

import com.amex.order.domain.Offer
import com.amex.order.domain.OfferType
import com.amex.order.domain.OrderLine
import spock.lang.Specification
import spock.lang.Subject

class BogoOfferProcessorSpec extends Specification {

    @Subject
    private BogoOfferProcessor offerProcessor

    def setup() {
        offerProcessor = new BogoOfferProcessor()
    }

    def "order qty is one"() {
        given:
        def offer = new Offer(
            productId: 1,
            type: OfferType.BOGO
        )

        def orderLine = new OrderLine(
            productId: 1,
            qty: 1,
            price: 0.60,
            total: 0.60
        )
        when:
        def discountInfo = offerProcessor.process(offer, orderLine)

        then:
        discountInfo.empty
    }

    def "order qty is two"() {
        given:
        def offer = new Offer(
            productId: 1,
            type: OfferType.BOGO
        )

        def orderLine = new OrderLine(
            productId: 1,
            qty: 2,
            price: 0.60,
            total: 1.20
        )
        when:
        def discountInfo = offerProcessor.process(offer, orderLine)

        then:
        discountInfo.present
        discountInfo.get().discount() == 0.60
    }

    def "order qty is odd"() {
        given:
        def offer = new Offer(
            productId: 1,
            type: OfferType.BOGO
        )

        def orderLine = new OrderLine(
            productId: 1,
            qty: 5,
            price: 0.60,
            total: 3.00
        )
        when:
        def discountInfo = offerProcessor.process(offer, orderLine)

        then:
        discountInfo.present
        discountInfo.get().discount() == 1.20
    }
}
