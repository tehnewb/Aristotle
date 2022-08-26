package resourceworker;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.framework.resource.RSResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class TestResource implements RSResource<List<Integer>> {

	private final URL url;
	private long start;

	@Override
	public List<Integer> load() throws Exception {
		log.info("Loading test callable...");
		start = System.nanoTime();
		ArrayList<Integer> resource = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Path.of(url.toURI()))) {
			stream.forEach(string -> resource.add(Integer.parseInt(string.trim())));
		}
		return resource;
	}

	@Override
	public void finish(List<Integer> resource) {
		double elapsed = (System.nanoTime() - start) / 1000000D;
		log.info("Loaded {} integers from file taking {}ms", resource.size(), elapsed);
	}

}
