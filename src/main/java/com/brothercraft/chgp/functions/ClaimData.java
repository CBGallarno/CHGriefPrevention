package com.brothercraft.chgp.functions;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.annotations.api;
import com.laytonsmith.annotations.startup;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.eclipse.swt.internal.win32.CHOOSECOLOR;

public class ClaimData {
    
    private static Location convertLocation(MCLocation l){
	return ((BukkitMCLocation)l).asLocation();
    }
    
    @startup
    public static void onEnable(){
	try {
	    Static.checkPlugin("GriefPrevention", Target.UNKNOWN);
	} catch (Exception e) {
	    System.out.println("[CommandHelper] CHGP Could not find GriefPrevention please make sure you have it installed.");
	}
	    System.out.println("[CommandHelper] CHGP Initialized - ACzChef");
    }
    
    @api
    public static class get_claim_id extends AbstractFunction {

	public ExceptionType[] thrown() {
	    return new ExceptionType[]{ExceptionType.InvalidPluginException, ExceptionType.CastException, ExceptionType.InvalidPluginException};
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {
	    Static.checkPlugin("GriefPrevention", t);
	    MCLocation l = null;
	    
	    if (args[0] instanceof CArray) {
		CArray ca = (CArray) args[0];
		l = ObjectGenerator.GetGenerator().location(ca, (l != null ? l.getWorld() : null), t);
	    } else {
		 throw new ConfigRuntimeException("Expected argument 1 to get_claim_id to be an array",
		 ExceptionType.CastException, t);
	    }
	    
	    Claim c = GriefPrevention.instance.dataStore.getClaimAt(convertLocation(l), true, null);
	    if(c == null) {
		return new CNull();
	    } else {
		return new CInt(c.getID().intValue(), t);
	    }  
	}

	public String getName() {
	    return "get_claim_id";
	}

	public Integer[] numArgs() {
	    return new Integer[]{1};
	}

	public String docs() {
	    throw new UnsupportedOperationException("Not supported yet.");
	}

	public CHVersion since() {
	    return CHVersion.V3_3_1;
	}
    }
    
    @api
    public static class get_claim_info extends AbstractFunction {

	public ExceptionType[] thrown() {
	    throw new UnsupportedOperationException("Not supported yet.");
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(Target tar, Environment environment, Construct... args) throws ConfigRuntimeException {
	    Static.checkPlugin("GriefPrevention", tar);
	    MCLocation l = null;
	    
	    if (args[0] instanceof CArray) {
		CArray ca = (CArray) args[0];
		l = ObjectGenerator.GetGenerator().location(ca, (l != null ? l.getWorld() : null), tar);
	    } else {
		 throw new ConfigRuntimeException("Expected argument 1 to get_claim_id to be an array",
		 ExceptionType.CastException, tar);
	    }
	    
	    Claim c = GriefPrevention.instance.dataStore.getClaimAt(convertLocation(l), true, null);
	    
	    if(c == null) {
		return new CNull();
	    } 
	    
	    CArray data = new CArray(tar);
	    
	    data.set("owner", new CString(c.getOwnerName(), tar), tar);
	    data.set("isadmin", new CBoolean(c.isAdminClaim(), tar), tar);
	    data.set("id", new CInt(c.getID().intValue(), tar), tar);
	    data.set("Height", new CInt(c.getHeight(), tar), tar);
	    CArray children = new CArray(tar);
	    for (Claim child : c.children) {
		CArray childData = new CArray(tar);
		
		childData.set("Owner", new CString(child.getOwnerName(), tar), tar);
		children.push(childData);
		
	    }
	    data.set("subclaims", children, tar);
	    return data;
	}

	public String getName() {
	    return "get_claim_info";
	}

	public Integer[] numArgs() {
	    return new Integer[] {1};
	}

	public String docs() {
	    throw new UnsupportedOperationException("Not supported yet.");
	}

	public CHVersion since() {
	    return CHVersion.V3_3_1;
	}
	
    }
}