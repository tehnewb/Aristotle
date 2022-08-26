package serialize;

import java.util.List;

import lombok.Data;

@Data
public class TestUser {
	private String name;
	private List<String> friends;
}