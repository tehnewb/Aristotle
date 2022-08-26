package serialize;

import java.util.Arrays;

import com.framework.RSFramework;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestSerializeApplication {

	public static void main(String[] args) throws Exception {
		RSFramework.run(TestSerializeApplication.class);

		TestUser user = new TestUser();
		user.setName("Albert");
		user.setFriends(Arrays.asList("Joe", "Miranda", "Helen"));

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(user);

		System.out.println("Serialized JSON object from User: ");
		System.out.println();
		System.out.println("\t" + jsonString.replace("\n", "\n\t"));
		System.out.println();

		System.out.println("Deerialized JSON object to User: ");
		System.out.println();
		System.out.println("\t" + gson.fromJson(jsonString, TestUser.class));
	}

}
