package com.brothercraft.chgp.functions;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitMCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class ClaimCheck {
    @api(environments = {CommandHelperEnvironment.class})
    public static class has_gp_buildperm extends AbstractFunction {
        
        public Exceptions.ExceptionType[] thrown() {
            return null;
        }
        
        public boolean isRestricted() {
            return true;
        }
        
        public Boolean runAsync() {
            return false;
        }
        
        public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
          Static.checkPlugin("GriefPrevention", t);
                Player player;
		CArray array;

          if (args.length == 0) {
          	throw new ConfigRuntimeException("Invalid arguments. Use [player,] location", Exceptions.ExceptionType.FormatException, t);
          } else if (args.length == 1) {
          	if (args[0] instanceof CArray) {
          		array = (CArray)args[0];
          		MCPlayer p = environment.getEnv(CommandHelperEnvironment.class).GetPlayer();
          		player = ((BukkitMCPlayer)p)._Player();
          	} else {
          		throw new ConfigRuntimeException("Invalid arguments. Use [player,] location", Exceptions.ExceptionType.FormatException, t);
          	}
          } else if (args.length == 2 && (args[0] instanceof CString) && (args[1] instanceof CArray)) {
          	player = Bukkit.getPlayer(args[0].val());
          	array = (CArray)args[1];
          } else {
          	throw new ConfigRuntimeException("Invalid arguments. Use [player,] location", Exceptions.ExceptionType.FormatException, t);
          }


          MCLocation loc = ObjectGenerator.GetGenerator().location(array, null, t);
          Location location = ((BukkitMCLocation)loc).asLocation();

          Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, false, null);
          if(claim == null){
              return new CBoolean(true, t);
          }
          String errorMessage = claim.allowBuild(player);
            
          if (errorMessage == null){
              return new CBoolean(true, t);
          } else {
              return new CBoolean(false, t);
          }
        }
        
        public String getName() {
            return getClass().getSimpleName();
        }
        
        public Integer[] numArgs() {
            return new Integer[]{1, 2};
        }
        
        public String docs() {
            return "boolean {[player,] location} See if a player can build at a given location.";
        }
        
        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
    }
}
