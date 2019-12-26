//LAST MODIFIED: 2019.12.02

package jumpin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class takes care of creating levels.
 */
public class LevelRepository {
	public static final String EASY_LEVEL_NAME = "easy";
	public static final String MEDIUM_LEVEL_NAME = "medium";
	public static final String HARD_LEVEL_NAME = "hard";

	private static final String DEFAULT_LEVEL_ROOT = "levels/default";
	private static final String USER_LEVEL_ROOT = "levels/user";

	private static final LevelMarshaller LEVEL_MARSHALLER = new LevelMarshaller();
	private static final String JSON_EXTENSION = ".json";

	private final String saveFolderRoot;

	private LevelRepository(final String saveFolderRoot) {
		this.saveFolderRoot = saveFolderRoot;
	}

	public static LevelRepository getPremadeLevelRepository() {
		return new LevelRepository(DEFAULT_LEVEL_ROOT);
	}

	public static LevelRepository getUserLevelRepository() {
		return new LevelRepository(USER_LEVEL_ROOT);
	}

	public void saveLevel(final String name, final Level level) {
		final String levelJson = LEVEL_MARSHALLER.toJson(level);

		final String filePath = getFilePath(name);

		try (final BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
			fileWriter.write(levelJson);
		} catch (final IOException e) {
			throw new RuntimeException("Was not able to save the level: " + name, e);
		}
	}

	public Level loadLevel(final String name) {
		final String filePath = getFilePath(name);

		String levelJson;
		try (final BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
			levelJson = fileReader.readLine();
		} catch (final IOException e) {
			throw new RuntimeException("Was not able to load the level: " + name, e);
		}

		final Level level = LEVEL_MARSHALLER.fromJson(levelJson);

		return level;
	}

	public List<String> getLevelNames() {
		final List<String> levelNames = new ArrayList<>();
		final File levelFolder = new File(saveFolderRoot);

		if (levelFolder.exists() && levelFolder.isDirectory()) {
			final File[] levelFiles = levelFolder.listFiles((dir, name) -> name.endsWith(JSON_EXTENSION));
			Arrays.stream(levelFiles)
					.map(File::getName)
					.map(fileName -> fileName.replace(JSON_EXTENSION, ""))
					.sorted()
					.forEach(levelNames::add);
		} else {
			throw new IllegalStateException("The configured level folder is not a directory: " + levelFolder.getAbsolutePath());
		}

		return levelNames;
	}

	private String getFilePath(final String name) {
		return new StringBuilder(saveFolderRoot)
				.append('/')
				.append(name)
				.append(JSON_EXTENSION)
				.toString();
	}
}
