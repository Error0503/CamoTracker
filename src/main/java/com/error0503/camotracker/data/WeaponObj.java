package com.error0503.camotracker.data;

import java.util.List;

public record WeaponObj(
        String name,
        List<CamoObj> camos, List<CamoObj> masteries) {
}
