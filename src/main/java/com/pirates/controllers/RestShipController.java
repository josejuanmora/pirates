package com.pirates.controllers;

import static com.pirates.DTOHelper.*;

import com.pirates.dto.*;
import com.pirates.jpa.entities.*;
import com.pirates.Application;
import com.pirates.services.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
     * Retrieves all ships.
     * @return the http status
     */
    @RequestMapping(value = BASE_URI , method = RequestMethod.GET)
    public ResponseEntity findAllShips() {
        Iterable<Ship> list = shipService.findAllShips();
        return new ResponseEntity<>(toShipDTOList(list), HttpStatus.OK);
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
                toGoods(eventDTO.getGoods()));
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

    private List<Good> toGoods(final List<GoodDTO> goods) {
        return goods.stream().
                map(g -> Good.builder().goodType(g.getGoodType()).qty(g.getQty()).build()).collect(Collectors.toList());
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
                goods(toGoodDTO(event.getGoods())).
                creationDate(from(event.getCreationDate())).build();
    }

    private List<ShipDTO> toShipDTOList(Iterable<Ship> ships) {
        return StreamSupport.stream(ships.spliterator(), false).
                map(s -> toShipDTO(s, null)).collect(Collectors.toList());
    }
}
