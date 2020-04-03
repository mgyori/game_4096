package hu.markgyori.game_4096.classes;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;

import hu.markgyori.game_4096.App;

/**
 * Class for EclipseLink log bridge. 
 * @author marko
 *
 */
public class LogBridge extends AbstractSessionLog {

	@Override
	public void log(SessionLogEntry entry) {
		StringBuilder message = new StringBuilder();
		message.append(getSupplementDetailString(entry));
		message.append(formatMessage(entry));
		switch (entry.getLevel()) {
		case FINE:
			App.getLogger().debug(message.toString());
			break;
		case INFO:
		case CONFIG:
			App.getLogger().info(message.toString());
			break;
		case WARNING:
			App.getLogger().warn(message.toString());
			break;
		case SEVERE:
			App.getLogger().error(message.toString());
			break;
		default:
			App.getLogger().trace(message.toString());
			break;
		}
	}
}
