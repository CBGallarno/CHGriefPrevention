package com.aczchef.chgp;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

/**
 *
 * @author cgallarno
 */
@MSExtension("CHGriefPrevention")
public class LifeCycle extends AbstractExtension {

    public Version getVersion() {
	return new SimpleVersion(1, 2, 0);
    }

    @Override
    public void onShutdown() {
	System.out.println("[CommandHelper] CHGriefPrevention: De-Initialized");
    }

    @Override
    public void onStartup() {
	try {
	    Static.checkPlugin("GriefPrevention", Target.UNKNOWN);
	} catch (Exception e) {
	    System.out.println("[CommandHelper] CHGriefPrevention Could not find GriefPrevention please make sure you have it installed.");
	}
	System.out.println("[CommandHelper] CHGriefPrevention: Initialized");
    }
}
