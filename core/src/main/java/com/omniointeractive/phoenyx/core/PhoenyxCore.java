package com.omniointeractive.phoenyx.core;

import com.omniointeractive.phoenyx.api.addon.Addon;
import com.omniointeractive.phoenyx.api.addon.AddonInfo;
import com.omniointeractive.phoenyx.core.item.TestingItem;

@AddonInfo(name = "Phoenyx")
public class PhoenyxCore extends Addon {

    @Override
    public void onEnable() {
        this.registerItems(new TestingItem());
    }
}
