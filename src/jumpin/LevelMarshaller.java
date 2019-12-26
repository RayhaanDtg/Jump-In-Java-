
package jumpin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jumpin.common.InterfaceAdapter;
import jumpin.element.GameElement;

/*our parser*/
public class LevelMarshaller {
	private static final Gson MARSHALLER = new GsonBuilder().enableComplexMapKeySerialization()
			.registerTypeAdapter(GameElement.class, new InterfaceAdapter<GameElement>()).create();

	/*
	 * this method converts the level to a json object
	 * 
	 * @return string
	 */
	public String toJson(final Level level) {
		return MARSHALLER.toJson(level);
	}

	/*
	 * this method converts a json object to a level
	 * 
	 * @return string
	 */
	public Level fromJson(final String levelJson) {
		return MARSHALLER.fromJson(levelJson, Level.class);
	}
}
