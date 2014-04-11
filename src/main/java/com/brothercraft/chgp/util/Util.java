package com.brothercraft.chgp.util;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Target;
import java.util.ArrayList;
import org.bukkit.Location;

public class Util {
    public static Location Location(MCLocation l){
	return ((BukkitMCLocation)l).asLocation();
    }
    
    public static MCLocation Location(Location l){
	BukkitMCLocation Bl = new BukkitMCLocation(l);
	return(Bl.clone());
    }

    public static CArray formPermissions(ArrayList<String> builders, ArrayList<String> containers, ArrayList<String> accesors, ArrayList<String> managers, Target tar) {
	CArray permissions = new CArray(tar);
	CArray Cbuilders = new CArray(tar);
	CArray Ccontainers = new CArray(tar);
	CArray Caccesors = new CArray(tar);
	CArray Cmanagers = new CArray(tar);
	for (int i = 0; i < builders.size(); i++) {
	    Cbuilders.push(new CString(builders.get(i), tar));
	}
	permissions.set("builders", Cbuilders, tar);
	for (int i = 0; i < containers.size(); i++) {
	    Ccontainers.push(new CString(containers.get(i), tar));
	}
	permissions.set("containers", Ccontainers, tar);
	for (int i = 0; i < accesors.size(); i++) {
	    Caccesors.push(new CString(accesors.get(i), tar));
	}
	permissions.set("accesors", Caccesors, tar);
	for (int i = 0; i < managers.size(); i++) {
	    Cmanagers.push(new CString(managers.get(i), tar));
	}
	permissions.set("managers", Cmanagers, tar);
	return permissions;
    }
}
