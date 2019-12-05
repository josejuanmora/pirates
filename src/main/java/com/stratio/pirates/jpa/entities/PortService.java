package com.stratio.pirates.jpa.entities;

import com.stratio.pirates.jpa.repositories.PortRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Contains all the services that are related to ports.
 */
@Service
public class PortService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortService.class);

    private PortRepository portRepository;

    @Autowired
    public void setPortRepository(final PortRepository thePortRepository) {
        this.portRepository = thePortRepository;
    }

    public PortService() {
    }

    /**
     * Retrieves the port if found.
     * @param id the identifier
     * @return the port
     */
    public Optional<Port> findPort(final long id) { return portRepository.findById(id); }

}
