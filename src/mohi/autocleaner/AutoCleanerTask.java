/**
	*		@author : MohiPE
	*		@since : 2016.3.17
	*		@E-Mail : dreamaker7770@gmail.com
	*/

package mohi.autocleaner;

import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.plugin.Plugin;

class AutoCleanerTask extends PluginTask {
	Plugin owner;
	public void AutoCleanerTask(Plugin plugin) {
		super(plugin);
	}
	@Override
	public void onRun(int currentTick) {
		this.owner = getOwner();
		owner.entityCleaner();
	}
}