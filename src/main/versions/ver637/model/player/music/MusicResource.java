package versions.ver637.model.player.music;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.framework.resource.RSResource;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MusicResource implements RSResource<MusicTrackRepo> {

	private static final HashMap<Integer, MusicTrack> Tracks = new HashMap<>();
	private static final String MusicPath = "./resources/637/music-tracks.json";

	@Override
	public MusicTrackRepo load() throws Exception {
		log.info("Loading music tracks...");
		Gson gson = new Gson();
		return gson.fromJson(Files.readString(Paths.get(MusicPath)), MusicTrackRepo.class);
	}

	@Override
	public void finish(MusicTrackRepo resource) {
		for (MusicTrack track : resource.getTracks()) {
			for (int regionID : track.regionIDs()) {
				Tracks.put(regionID, track);
			}
		}
		log.info("Loaded {} music tracks.", resource.getTracks().size());
	}

	public static MusicTrack getMusicTrack(int regionID) {
		if (regionID < 0 || regionID > 16384)
			throw new IndexOutOfBoundsException("Music track regionID must be between 0 and 16384");

		return Tracks.get(regionID);
	}

}
