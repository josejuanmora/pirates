--liquibase formatted sql

--changeset jjuan:1

CREATE TABLE SHIPS
(
  ID                BIGINT              CONSTRAINT NN_SHIPS_ID NOT NULL,
  NAME              TEXT                CONSTRAINT NN_SHIPS_NAME NOT NULL,
  BARRELS_OF_RUM    INT                 CONSTRAINT NN_SHIPS_BOR NOT NULL,
  GOLD_COINS        INT                 CONSTRAINT NN_SHIPS_GC NOT NULL,
  CURRENT_PORT_ID   BIGINT              ,
  CREATION_DATE     TIMESTAMP(6)        DEFAULT CURRENT_TIMESTAMP CONSTRAINT NN_SHIPS_CREATION_DATE NOT NULL
);


CREATE TABLE PORTS
(
  ID                BIGINT              CONSTRAINT NN_PORTS_ID NOT NULL,
  NAME              TEXT                CONSTRAINT NN_PORTS_NAME NOT NULL,
  CREATION_DATE     TIMESTAMP(6)        DEFAULT CURRENT_TIMESTAMP CONSTRAINT NN_PORTS_CREATION_DATE NOT NULL
);

CREATE TABLE EVENTS
(
  ID                BIGINT              CONSTRAINT NN_EVENTS_ID NOT NULL,
  SHIP_ID           BIGINT              CONSTRAINT NN_EVENTS_SHIP_ID NOT NULL,
  PORT_ID           BIGINT              CONSTRAINT NN_EVENTS_PORT_ID NOT NULL,
  TYPE_ID           SMALLINT            CONSTRAINT NN_EVENTS_TYPE_ID NOT NULL,
  CREATION_DATE     TIMESTAMP(6)        DEFAULT CURRENT_TIMESTAMP CONSTRAINT NN_EVENTS_CREATION_DATE NOT NULL
);

CREATE TABLE EVENT_TYPES
(
  ID                BIGINT              CONSTRAINT NN_EVENT_TYPES_ID NOT NULL,
  NAME              TEXT                CONSTRAINT NN_EVENT_TYPES_NAME NOT NULL
);


COMMENT ON TABLE SHIPS is 'The pirate ships';
COMMENT ON COLUMN SHIPS.ID is 'The unique identifier, sequence SEQ_SHIPS';
COMMENT ON COLUMN SHIPS.NAME is 'The name of the ship';
COMMENT ON COLUMN SHIPS.BARRELS_OF_RUM is 'The number of barrels of rum that the ship is able to transport';
COMMENT ON COLUMN SHIPS.GOLD_COINS is 'The number of gold coins that the ship is able to transport';
COMMENT ON COLUMN SHIPS.CURRENT_PORT_ID is 'The port where the ship is located, null if on outer seas';
COMMENT ON COLUMN SHIPS.CREATION_DATE is 'The moment when the record was created';

COMMENT ON TABLE PORTS is 'The pirate ports';
COMMENT ON COLUMN PORTS.ID is 'The unique identifier, sequence SEQ_PORTS';
COMMENT ON COLUMN PORTS.NAME is 'The name of the port';
COMMENT ON COLUMN PORTS.CREATION_DATE is 'The moment when the record was created';

COMMENT ON TABLE EVENTS is 'The events performed on ships';
COMMENT ON COLUMN EVENTS.SHIP_ID is 'The ship owning the event';
COMMENT ON COLUMN EVENTS.PORT_ID is 'The port owning the event';
COMMENT ON COLUMN EVENTS.TYPE_ID is 'The type of event generated (arrival or departure)';
COMMENT ON COLUMN EVENTS.CREATION_DATE is 'The moment when the record was created';

COMMENT ON TABLE EVENT_TYPES is 'The supported event types';
