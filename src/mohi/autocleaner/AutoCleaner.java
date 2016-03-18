/**
	*		@author : MohiPE
	*		@since : 2016.3.18
	*		@E-Mail : dreamaker7770@gmail.com
	*/


package mohi.autocleaner;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.Config;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.block.Block;
import cn.nukkit.math.Vector3;
import cn.nukkit.level.generator.object.tree.ObjectTree;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.entity.EntityCreature;

import java.util.*;

public class AutoCleaner extends PluginBase implements Listener {
	private Config config;
	private LinkedList<Entity> oldEntities = new LinkedList<Entity>();
	
	@Override
	public void onEnable() {
		this.config = (new Config( getDataFolder() + "/config.yml", Config.YAML, new LinkedHashMap<String, String>() {
			put( "Tree planting", "on" );
		})).getAll();	
		getServer().getPluginManager().registerEvents( this, this );
		getServer().getScheduler().scheduleRepeatingTask(new AutoCleanerTask(this), 20 * 60 * 2);
	}
	public void onDisable() {
		this.save();
	}
	public boolean isOldEntity( Entity entity ) {
		return oldEntities.contains( entity );
	}
	public void plantTree( Entity entity, Level level) {
		int x = (int) Math.round( entity.getX() );
		int y = ((int) Math.round( entity.getY() )) - 1;
		int z = (int) Math.round( entity.getZ() );
		Vector3 vector3 = new Vector3( x, y, z );
		int meta = (int) Random.nextInt( 6 );
		if(level.getBlock( vector3 ) instanceof Block.get( Block.DIRT ) ) {
			level.setBlock( vector3.add( 0, 1, 0 ) , Block.get( 6, meta ));
			Random.nextInt(2) == 0 ? ObjectTree.growTree( level, vector3.getX(), vector3.getY(), vector3.getZ(), meta ) : return;
			return;
		}
	}
	public void entityCleaner() {
		foreach( Level level : getServer().getLevels() ) {
			foreach( Entity entity : level.getEntities() ) {
				if( entity instanceof EntityCreature )
					continue;
				if( entity instanceof BlockEntity )
					continue;
				if( config.get( "Tree planting" ) == "on" ) {
					if( isOldEntity( entity ) ) {
						oldEntities.remove( entity );
						plantTree( entity, level );
						continue;
					}
					oldEntities.add( entity );
				}
				else entity.close();
			}
		}
		return;
	}	
	public void save() {
		Config config = new Config(getDataFolder() + "/config.yml", Config.YAML);
		config.setAll(this.config);
		config.save();
	}
}