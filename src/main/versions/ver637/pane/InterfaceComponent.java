package versions.ver637.pane;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class InterfaceComponent {

	private final int ID;

	private InterfaceWindow parent;
	private ComponentSettings settings;
	private String text;
	private String[] options;
	private boolean hidden;
}
