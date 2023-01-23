package com.error0503.camotracker.data;

import java.util.List;

public record WeaponCategoryObj(
        String categoryName,
        List<WeaponObj> weapons) {
}