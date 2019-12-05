package com.stratio.pirates.controllers;

import com.stratio.pirates.Application;
import com.stratio.pirates.dto.*;
import com.stratio.pirates.jpa.entities.*;
import com.stratio.pirates.services.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller that manages ships.
 */
@RestController
public class RestShipController {

    private static final String BASE_URI = Application.BASE_URI + "/ship/";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestShipController.class);

    private ShipService shipService;

    @Autowired
    public void setShipService(final ShipService theShipService) {
        this.shipService = theShipService;
    }

    /**
     * Retrieves the ship by its id.
     * @param id the ship identifier
     * @return the http status
     */
    @RequestMapping(value = BASE_URI + "{id}" , method = RequestMethod.GET)
    public ResponseEntity findShip(
            @PathVariable final long id,
            @RequestParam(value = "eventType", required=false) final EventType eventType) {

        ResponseEntity result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<Ship> optional = shipService.findShip(id);
        if(optional.isPresent()) {
            result = new ResponseEntity<>(toShipDTO(optional.get(), eventType), HttpStatus.OK);
        }
        return result;
    }

    /**
     * Creates a new event for a given ship.
     * @param id the ship identifier
     * @param eventDTO the event to be inserted
     * @return the http status
     */
    @RequestMapping(value = BASE_URI + "{id}/event" , method = RequestMethod.PUT)
    public ResponseEntity createEvent(
            @PathVariable final long id,
            @RequestBody final CreateEventDTO eventDTO) {

        ResponseEntity result =  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Optional<Event> optional =
            shipService.createShipEvent(
                id,
                eventDTO.getPortId(),
                eventDTO.getEventType(),
                toStock(eventDTO.getStock()));
        if(optional.isPresent()) {
            result = new ResponseEntity<>(HttpStatus.CREATED);
        }
        return result;
    }

    private ShipDTO toShipDTO(final Ship ship, final EventType eventType) {
        return ShipDTO.builder().
                id(ship.getId()).
                name(ship.getName()).
                events(toEventDTO(ship.getEvents(), eventType)).build();
    }

    private PortDTO toPortDTO(final Port port) {
        return PortDTO.builder().
                id(port.getId()).
                name(port.getName()).build();
    }

    private StockDTO toStockDTO(final Stock stock) {
        return StockDTO.builder().barrelsOfRum(stock.getBarrelsOfRum()).goldCoins(stock.getGoldCoins()).build();
    }

    private Stock toStock(final StockDTO stockDTO) {
        return new Stock(stockDTO.getBarrelsOfRum(), stockDTO.getGoldCoins());
    }

    private List<EventDTO> toEventDTO(final List<Event> events, final EventType eventType) {
        return events.stream().
                filter(e -> (eventType== null || eventType.equals(e.getEventType()))).
                map(e -> toEventDTO(e)).collect(Collectors.toList());
    }

    private EventDTO toEventDTO(final Event event) {
        return EventDTO.builder().
                eventType(event.getEventType()).
                port(toPortDTO(event.getPort())).
                stock(toStockDTO(event.getStock())).
                creationDate(from(event.getCreationDate())).build();
    }

    private Date from(final LocalDateTime localDateTime) {
        final ZonedDateTime zdt = localDateTime.atZone(Application.DEFAULT_ZONE_ID);
        return Date.from(zdt.toInstant());
    }

    private LocalDate from(final Date dateToFormat) {
        return dateToFormat.toInstant().atZone(Application.DEFAULT_ZONE_ID).toLocalDate();
    }
}
