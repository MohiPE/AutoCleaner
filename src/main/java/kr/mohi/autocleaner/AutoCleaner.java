/**
 * @author : MohiPE
 * @since : 2016.3.20
 * @email : dreamaker7770@gmail.com
 */

package kr.mohi.autocleaner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockDirt;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.object.tree.ObjectTree;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class AutoCleaner extends PluginBase implements Listener {
	private LinkedHashMap<String, Object> config;
	private LinkedList<Entity> oldEntities = new LinkedList<Entity>();

	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		this.config = (LinkedHashMap<String, Object>) (new Config(getDataFolder() + "/config.yml", Config.YAML,
				new LinkedHashMap<String, Object>() {
					{
						put("Tree planting", true);
						put("sec", 60);
					}
				})).getAll();
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getScheduler().scheduleRepeatingTask(new AutoCleanerTask(this),
				20 * ((Integer) config.get("sec")).intValue());
	}

	public void onDisable() {
		this.save();
	}

	public boolean isOldEntity(Entity entity) {
		return oldEntities.contains(entity);
	}

	public void plantTree(Entity entity, Level level) {
		int x = (int) entity.getX();
		int y = ((int) entity.getY()) - 1;
		int z = (int) entity.getZ();
		Vector3 vector3 = new Vector3(x, y, z);
		NukkitRandom random = new NukkitRandom();
		int meta = new Random().nextInt(6);
		if (level.getBlock(vector3) instanceof BlockDirt) {
			if (Math.random() <= 0.5)
				ObjectTree.growTree(level, (int) vector3.getX(), (int) vector3.getY(), (int) vector3.getZ(), random,
						meta);
			else
				level.setBlock(vector3.add(0, 1, 0), Block.get(Block.SAPLING, meta));
		}
		entity.close();
	}

	public void entityCleaner() {
		for (Level level : new ArrayList<Level>(getServer().getLevels().values())) {
			for (Entity entity : level.getEntities()) {
				if (entity instanceof EntityCreature)
					continue;
				if ((Boolean) config.get("Tree planting")) {
					if (isOldEntity(entity)) {
						oldEntities.remove(entity);
						plantTree(entity, level);
						continue;
					} else
						oldEntities.add(entity);
				} else
					entity.close();
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