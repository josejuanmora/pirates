package com.stratio.pirates.services;

import com.stratio.pirates.jpa.entities.*;
import com.stratio.pirates.jpa.repositories.*;
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

    public ShipService() {
    }

    /**
     * Retrieves the ship if found.
     * @param id the identifier
     * @return the ship
     */
    public Optional<Ship> findShip(final long id) { return shipRepository.findById(id); }

    /**
     * Creates the event.
     * @param shipId the ship identifier
     * @param portId the port identifier
     * @param barrelsOfRum the number of barrels of rum
     * @param goldCoins the number of gold coins
     * @param eventType the type of event
     * @return the created event, if created
     */
    @Transactional
    public Optional<Event> createShipEvent(
            final long shipId,
            final long portId,
            final EventType eventType,
            final int barrelsOfRum,
            final int goldCoins) {

        Optional<Event> result = Optional.empty();

        Optional<Ship> optionalShip = findShip(shipId);
        Optional<Port> optionalPort = portRepository.findById(portId);

        if(optionalShip.isPresent() && optionalPort.isPresent()) {
            Ship ship = optionalShip.get();
            Port port = optionalPort.get();
            Stock stock = Stock.builder().barrelsOfRum(barrelsOfRum).goldCoins(goldCoins).build();
            if(eventType.isEventAllowed(ship, port, stock)) {
                Event event =
                    Event.builder().
                        ship(ship).
                        port(port).
                        eventType(eventType).
                        barrelsOfRum(stock.getBarrelsOfRum()).
                        goldCoins(stock.getGoldCoins()).
                        creationDate(LocalDateTime.now()).build();
                result = Optional.of(eventRepository.save(event));
            }
        }
        return result;
    }
}
