/**
 * @author : MohiPE
 * @since : 2016.3.18
 * @email : dreamaker7770@gmail.com
 */

package kr.mohi.autocleaner;

import cn.nukkit.scheduler.PluginTask;

class AutoCleanerTask extends PluginTask<AutoCleaner> {
	public AutoCleanerTask(AutoCleaner owner) {
		super(owner);
	}
	@Override
	public void onRun( int currentTick ) {
		owner.entityCleaner();
	}
}