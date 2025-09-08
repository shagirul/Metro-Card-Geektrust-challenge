package com.example.geektrust.repository;


import com.example.geektrust.domain.Station;
import com.example.geektrust.domain.StationLedger;

public interface StationLedgerRepository {
    StationLedger get(Station station);
}
