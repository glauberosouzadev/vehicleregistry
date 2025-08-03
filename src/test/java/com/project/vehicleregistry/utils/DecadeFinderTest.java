package com.project.vehicleregistry.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DecadeFinderTest {

    @Test
    void shouldFindDecade() {
        var d1995 = 1995;
        var d2009 = 2009;
        var d2021 = 2021;

        Assertions.assertEquals(1990, DecadeFinder.find(d1995));
        Assertions.assertEquals(2000, DecadeFinder.find(d2009));
        Assertions.assertEquals(2020, DecadeFinder.find(d2021));
    }
}