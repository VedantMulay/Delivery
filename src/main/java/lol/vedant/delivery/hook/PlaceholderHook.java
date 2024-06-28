/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class PlaceholderHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "delivery";
    }

    @Override
    public @NotNull String getAuthor() {
        return "COMPHACK (Vedant)";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }


}
