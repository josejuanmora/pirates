package com.pirates.services;

import com.pirates.jpa.entities.*;
import com.pirates.jpa.repositories.EventRepository;
import com.pirates.jpa.repositories.GoodRepository;
import com.pirates.jpa.repositories.PortRepository;
import com.pirates.jpa.repositories.ShipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Contains all the services that are related to ships.
 */
@Service
public class ShipService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShipService.class);

    private ShipRepository shipRepository;

    private PortRepository portRepository;

    private EventRepository eventRepository;

    private GoodRepository goodRepository;

    @Autowired
    public void setShipRepository(final ShipRepository theShipRepository) {
        this.shipRepository = theShipRepository;
    }

    @Autowired
    public void setPortRepository(final PortRepository thePortRepository) {
        this.portRepository = thePortRepository;
    }

    @Autowired
    public void setEventRepository(final EventRepository theEventRepository) {
        this.eventRepository = theEventRepository;
    }

    @Autowired
    public void setGoodRepository(final GoodRepository theGoodRepository) {
        this.goodRepository = theGoodRepository;
    }

    public ShipService() {
    }

    /**
     * Retrieves the ship if found.
     * @param id the identifier
     * @return the ship
     */
    public Optional<Ship> findShip(final long id) { return shipRepository.findById(id); }

    /**
     * Retrieves the ships.
     * @return the ship
     */
    public Iterable<Ship> findAllShips() { return shipRepository.findAll(); }

    /**
     * Creates the event.
     * @param shipId the ship identifier
     * @param portId the port identifier
     * @param eventType the type of event
     * @param goods the stock
     * @return the created event, if created
     */
    @Transactional
    public Optional<Event> createShipEvent(
            final long shipId,
            final long portId,
            final EventType eventType,
            final List<Good> goods) {

        Optional<Event> result = Optional.empty();

        Optional<Ship> optionalShip = findShip(shipId);
        Optional<Port> optionalPort = portRepository.findById(portId);

        if(optionalShip.isPresent() && optionalPort.isPresent()) {
            Ship ship = optionalShip.get();
            Port port = optionalPort.get();
            if(eventType.isEventAllowed(ship, port, goods)) {
                Event event =
                    Event.builder().
                        ship(ship).
                        port(port).
                        eventType(eventType).
                        creationDate(LocalDateTime.now()).build();
                Event persistedEvent = eventRepository.save(event);
                goods.stream().forEach(g -> saveGood(persistedEvent, g));
                result = Optional.of(persistedEvent);
            }
        }
        return result;
    }

    private void saveGood(final Event event, final Good good) {
        good.setEvent(event);
        goodRepository.save(good);
    }
}
