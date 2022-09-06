package versions.ver637.model.player;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

public class NotesVariables {

	private String[] notes = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
	private byte[] colors = new byte[30];

	@Getter
	@Setter
	private int highlightedIndex = -1;

	@Getter
	@Setter
	private int notesCount;

	public String getNotes(int index) {
		return notes[index];
	}

	public void setNotes(int index, String text) {
		this.notes[index] = text;
	}

	public int getColor(int index) {
		return colors[index];
	}

	public void setColor(int index, int color) {
		this.colors[index] = (byte) color;
	}

	public void shiftNotes() {
		String[] newNotes = new String[30];
		byte[] newColors = new byte[30];
		Arrays.fill(newNotes, "");

		int newIndex = 0;
		for (int i = 0; i < 30; i++) {
			if (notes[i].isBlank()) {
				colors[i] = 0;
				notes[i] = "";
			} else {
				newColors[newIndex] = colors[i];
				newNotes[newIndex++] = notes[i];
			}
		}
		this.notes = newNotes;
		this.colors = newColors;
	}

}
