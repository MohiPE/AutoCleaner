/**
	*		@author : MohiPE
	*		@since : 2016.3.17
	*		@E-Mail : dreamaker7770@gmail.com
	*/

package mohi.autocleaner;

import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.plugin.Plugin;

class AutoCleanerTask extends PluginTask<AutoCleaner> {
	public AutoCleanerTask(AutoCleaner owner) {
		super(owner);
		// TODO 자동 생성된 생성자 스텁
	}
	@Override
	public void onRun( int currentTick ) {
		owner.entityCleaner();
	}
}