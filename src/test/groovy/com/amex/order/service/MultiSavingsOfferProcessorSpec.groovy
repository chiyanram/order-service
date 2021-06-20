package com.amex.order.service

import com.amex.order.domain.*
import spock.lang.Specification
import spock.lang.Subject

class MultiSavingsOfferProcessorSpec extends Specification {

    @Subject
    private MultiSavingsOfferProcessor offerProcessor

    def setup() {
        offerProcessor = new MultiSavingsOfferProcessor();
    }

    def "multi savings with not enough qty"() {
        given:
        def offer = new Offer(
            productId: 1,
            type: OfferType.MULTI_SAVINGS,
            config: new OfferConfig(Optional.empty(), Optional.of(new MultiSaving(3, 2)))
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
        discountInfo.empty
    }

    def "multi savings with same qty"() {
        given:
        def offer = new Offer(
            productId: 1,
            type: OfferType.MULTI_SAVINGS,
            config: new OfferConfig(Optional.empty(), Optional.of(new MultiSaving(3, 2)))
        )

        def orderLine = new OrderLine(
            productId: 1,
            qty: 3,
            price: 0.60,
            total: 1.80
        )

        when:
        def discountInfo = offerProcessor.process(offer, orderLine)

        then:
        discountInfo.present
        discountInfo.get().discount() == 0.60
    }

    def "multi savings with more than config qty"() {
        given:
        def offer = new Offer(
            productId: 1,
            type: OfferType.MULTI_SAVINGS,
            config: new OfferConfig(Optional.empty(), Optional.of(new MultiSaving(3, 2)))
        )

        def orderLine = new OrderLine(
            productId: 1,
            qty: 4,
            price: 0.60,
            total: 2.40
        )

        when:
        def discountInfo = offerProcessor.process(offer, orderLine)

        then:
        discountInfo.present
        discountInfo.get().discount() == 0.60
    }

    def "multi savings with double than config qty"() {
        given:
        def offer = new Offer(
            productId: 1,
            type: OfferType.MULTI_SAVINGS,
            config: new OfferConfig(Optional.empty(), Optional.of(new MultiSaving(3, 2)))
        )

        def orderLine = new OrderLine(
            productId: 1,
            qty: 6,
            price: 0.60,
            total: 3.20
        )

        when:
        def discountInfo = offerProcessor.process(offer, orderLine)

        then:
        discountInfo.present
        discountInfo.get().discount() == 1.20
    }
}
