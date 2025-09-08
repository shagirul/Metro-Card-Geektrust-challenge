package com.example.geektrust.repository;



import com.example.geektrust.domain.Station;
import com.example.geektrust.domain.StationLedger;

import java.util.EnumMap;
import java.util.Map;

public final class InMemoryStationLedgerRepository implements StationLedgerRepository {
    private final Map<Station, StationLedger> ledgers = new EnumMap<>(Station.class);


    public InMemoryStationLedgerRepository() {
        for (Station s : Station.values()) ledgers.put(s, new StationLedger(s));
    }


    @Override
    public StationLedger get(Station station) { return ledgers.get(station); }
}
