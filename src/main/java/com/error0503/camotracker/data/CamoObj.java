package com.error0503.camotracker.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CamoObj {
    private String name;
    private String displayName;
    private String requirement;
    private int progression;
    private int target;
    private boolean isUnlocked;
}
