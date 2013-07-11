package com.brothercraft.chgp.util;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import org.bukkit.Location;

public class Converters {
    public static Location convertLocation(MCLocation l){
	return ((BukkitMCLocation)l).asLocation();
    }
    
    public static MCLocation convertLocation(Location l){
	BukkitMCLocation Bl = new BukkitMCLocation(l);
	return(Bl.clone());
    }
}
