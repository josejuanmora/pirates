package com.pirates.controllers;

import static com.pirates.DTOHelper.*;

import com.pirates.jpa.entities.Event;
import com.pirates.jpa.entities.EventType;
import com.pirates.jpa.entities.Port;
import com.pirates.jpa.entities.Ship;
import com.pirates.Application;
import com.pirates.dto.PortDTO;
import com.pirates.dto.EventDTO;
import com.pirates.dto.ShipDTO;
import com.pirates.services.PortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller that manages ports.
 */
@RestController
@CrossOrigin
public class RestPortController {

    private static final String BASE_URI = Application.BASE_URI + "/port/";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestPortController.class);

    private PortService portService;

    @Autowired
    public void setPortService(final PortService thePortService) {
        this.portService = thePortService;
    }

    /**
     * Retrieves the port by its id.
     * @param id the port identifier
     * @return the http status
     */
    @RequestMapping(value = BASE_URI + "{id}" , method = RequestMethod.GET)
    public ResponseEntity findPort(
            @PathVariable final long id,
            @RequestParam(value = "eventType", required=false) final EventType eventType) {

        ResponseEntity result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<Port> optional = portService.findPort(id);
        if(optional.isPresent()) {
            result = new ResponseEntity<>(toPortDTO(optional.get(), eventType), HttpStatus.OK);
        }
        return result;
    }

    private PortDTO toPortDTO(final Port port, final EventType eventType) {
        return PortDTO.builder().
                id(port.getId()).
                name(port.getName()).
                goods(toGoodDTO(port.getGoods())).
                events(toEventDTO(port.getEvents(), eventType)).
                build();
    }

    private List<EventDTO> toEventDTO(final List<Event> events, final EventType eventType) {
        return events.stream().
                filter(e -> (eventType== null || eventType.equals(e.getEventType()))).
                map(e -> toEventDTO(e)).collect(Collectors.toList());
    }

    private EventDTO toEventDTO(final Event event) {
        return EventDTO.builder().
                eventType(event.getEventType()).
                ship(toShipDTO(event.getShip())).
                goods(toGoodDTO(event.getGoods())).
                creationDate(from(event.getCreationDate())).build();
    }

    private ShipDTO toShipDTO(final Ship ship) {
        return ShipDTO.builder().
                id(ship.getId()).
                name(ship.getName()).build();
    }
}
